package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import factory_interface.IFFactoryAcceptCandidate;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.candidate_type.CandidateValue;
import local_search.complement.TabuSolutions;
import problem.definition.Problem;
import metaheuristics.strategy.Strategy;
import problem.definition.Operator;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

public class TabuSearchTest {

    private TabuSearch tabuSearch;
    private CandidateValue mockCandidateValue;
    private IFFactoryAcceptCandidate mockFactoryAcceptCandidate;
    private AcceptableCandidate mockAcceptableCandidate;
    private Problem mockProblem;
    private Operator mockOperator;

    @BeforeEach
    public void setUp() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        mockProblem = mock(Problem.class);
        mockOperator = mock(Operator.class);
        when(mockProblem.getOperator()).thenReturn(mockOperator);
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(mockProblem);

        tabuSearch = new TabuSearch();
        TabuSolutions.listTabu.clear();
        TabuSolutions.maxelements = 5;

        mockCandidateValue = mock(CandidateValue.class);
        mockFactoryAcceptCandidate = mock(IFFactoryAcceptCandidate.class);
        mockAcceptableCandidate = mock(AcceptableCandidate.class);
        
        tabuSearch.setCandidateValue(mockCandidateValue);
        tabuSearch.setAcceptCandidateFactory(mockFactoryAcceptCandidate);
        
        when(mockFactoryAcceptCandidate.createAcceptCandidate(any())).thenReturn(mockAcceptableCandidate);
    }

    @Test
    public void testGenerate() throws Exception {
        State referenceState = mock(State.class);
        State mockState = mock(State.class);
        List<State> neighborhood = new ArrayList<>();
        neighborhood.add(mock(State.class));
        tabuSearch.setInitialReference(referenceState);
        
        when(mockOperator.generatedNewState(any(State.class), anyInt())).thenReturn(neighborhood);
        when(mockCandidateValue.stateCandidate(any(), any(), any(), any(), any())).thenReturn(mockState);
        
        State result = tabuSearch.generate(0);
        
        assertEquals(mockState, result);
    }

    @Test
    public void testUpdateReference_Accept() throws Exception {
        State initialState = mock(State.class);
        State candidateState = mock(State.class);
        when(candidateState.Comparator(any(State.class))).thenReturn(false);
        tabuSearch.setInitialReference(initialState);
        
        when(mockAcceptableCandidate.acceptCandidate(initialState, candidateState)).thenReturn(true);
        
        tabuSearch.updateReference(candidateState, 0);
        
        assertEquals(candidateState, tabuSearch.getReference());
        assertTrue(TabuSolutions.listTabu.contains(candidateState));
    }

    @Test
    public void testUpdateReference_Reject() throws Exception {
        State initialState = mock(State.class);
        State candidateState = mock(State.class);
        tabuSearch.setInitialReference(initialState);
        
        when(mockAcceptableCandidate.acceptCandidate(initialState, candidateState)).thenReturn(false);
        
        tabuSearch.updateReference(candidateState, 0);
        
        assertEquals(initialState, tabuSearch.getReference());
        assertTrue(TabuSolutions.listTabu.isEmpty());
    }

    @Test
    public void testTabuListDoesNotAddDuplicates() throws Exception {
        State initialState = mock(State.class);
        State candidateState = mock(State.class);
        when(candidateState.Comparator(any(State.class))).thenReturn(true);
        tabuSearch.setInitialReference(initialState);
        
        when(mockAcceptableCandidate.acceptCandidate(any(), any())).thenReturn(true);
        
        TabuSolutions.listTabu.add(candidateState);
        int sizeBefore = TabuSolutions.listTabu.size();
        
        tabuSearch.updateReference(candidateState, 0);
        
        assertEquals(sizeBefore, TabuSolutions.listTabu.size());
    }

    @Test
    public void testTabuListRemovesOldestWhenFull() throws Exception {
        State initialState = mock(State.class);
        tabuSearch.setInitialReference(initialState);
        
        when(mockAcceptableCandidate.acceptCandidate(any(), any())).thenReturn(true);
        
        // Fill tabu list
        for (int i = 0; i < TabuSolutions.maxelements; i++) {
            State state = mock(State.class);
            when(state.Comparator(any(State.class))).thenReturn(false);
            TabuSolutions.listTabu.add(state);
        }
        
        State newCandidate = mock(State.class);
        when(newCandidate.Comparator(any(State.class))).thenReturn(false);
        
        tabuSearch.updateReference(newCandidate, 0);
        
        assertEquals(TabuSolutions.maxelements, TabuSolutions.listTabu.size());
        assertTrue(TabuSolutions.listTabu.contains(newCandidate));
    }

    @Test
    public void testGetReferenceList() {
        State state = mock(State.class);
        tabuSearch.setInitialReference(state);
        
        List<State> list = tabuSearch.getReferenceList();
        
        assertEquals(1, list.size());
        assertEquals(state, list.get(0));
    }

    @Test
    public void testSetAndGetWeight() {
        tabuSearch.setWeight(75f);
        
        assertEquals(75f, tabuSearch.getWeight());
    }

    @Test
    public void testGetTypeReturnsTabuSearch() {
        assertEquals(GeneratorType.TabuSearch, tabuSearch.getType());
    }

    @Test
    public void testGetSonListReturnsNull() {
        assertEquals(null, tabuSearch.getSonList());
    }

    @Test
    public void testAwardUpdateREFReturnsFalse() {
        State state = mock(State.class);
        
        assertEquals(false, tabuSearch.awardUpdateREF(state));
    }

    @Test
    public void testGetTraceReturnsArray() {
        float[] trace = tabuSearch.getTrace();
        
        assertNotNull(trace);
        assertEquals(50f, trace[0]);
    }

    @Test
    public void testGetListCountBetterGenderReturnsArray() {
        int[] counts = tabuSearch.getListCountBetterGender();
        
        assertNotNull(counts);
        assertEquals(0, counts[0]);
    }

    @Test
    public void testGetListCountGenderReturnsArray() {
        int[] counts = tabuSearch.getListCountGender();
        
        assertNotNull(counts);
        assertEquals(0, counts[0]);
    }

    @Test
    public void testGetCountGenderReturnsZeroInitially() {
        assertEquals(0, tabuSearch.getCountGender());
    }

    @Test
    public void testSetCountGenderUpdatesValue() {
        tabuSearch.setCountGender(8);
        assertEquals(8, tabuSearch.getCountGender());
        // Reset for other tests
        tabuSearch.setCountGender(0);
    }

    @Test
    public void testGetCountBetterGenderReturnsZeroInitially() {
        assertEquals(0, tabuSearch.getCountBetterGender());
    }

    @Test
    public void testSetCountBetterGenderUpdatesValue() {
        tabuSearch.setCountBetterGender(4);
        assertEquals(4, tabuSearch.getCountBetterGender());
        // Reset for other tests
        tabuSearch.setCountBetterGender(0);
    }
}
