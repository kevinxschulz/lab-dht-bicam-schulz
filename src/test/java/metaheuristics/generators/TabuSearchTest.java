package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
