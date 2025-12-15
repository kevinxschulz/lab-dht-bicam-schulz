package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import local_search.acceptation_type.AcceptType;
import local_search.candidate_type.CandidateType;
import metaheuristics.strategy.Strategy;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

class RandomSearchTest {

    private RandomSearch randomSearch;
    private Problem mockProblem;
    private Operator mockOperator;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        mockProblem = mock(Problem.class);
        mockOperator = mock(Operator.class);
        when(mockProblem.getOperator()).thenReturn(mockOperator);
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(mockProblem);

        randomSearch = new RandomSearch();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void constructorSetsCorrectDefaults() {
        assertEquals(GeneratorType.RandomSearch, randomSearch.getType());
        assertEquals(50f, randomSearch.getWeight());
        assertNotNull(randomSearch.getTrace());
        assertEquals(50f, randomSearch.getTrace()[0]);
    }

    @Test
    void generateReturnsRandomStateFromNeighborhood() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State referenceState = createStateWithEvaluation(5.0);
        randomSearch.setInitialReference(referenceState);
        
        List<State> randomStates = new ArrayList<>();
        State randomState = createStateWithEvaluation(7.0);
        randomStates.add(randomState);
        
        when(mockOperator.generateRandomState(anyInt())).thenReturn(randomStates);
        
        State result = randomSearch.generate(1);
        
        assertNotNull(result);
    }

    @Test
    void updateReferenceAcceptsBetterCandidate() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        randomSearch.setInitialReference(initial);
        
        State better = createStateWithEvaluation(8.0);
        
        randomSearch.updateReference(better, 0);
        
        assertSame(better, randomSearch.getReference());
    }

    @Test
    void updateReferenceRejectsWorseCandidate() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(10.0);
        randomSearch.setInitialReference(initial);
        
        State worse = createStateWithEvaluation(3.0);
        
        randomSearch.updateReference(worse, 0);
        
        assertSame(initial, randomSearch.getReference());
    }

    @Test
    void setInitialReferenceUpdatesReference() {
        State state = createStateWithEvaluation(4.0);
        
        randomSearch.setInitialReference(state);
        
        assertSame(state, randomSearch.getReference());
    }

    @Test
    void getReferenceListAccumulatesReferences() {
        State state = createStateWithEvaluation(6.0);
        randomSearch.setInitialReference(state);
        
        List<State> firstCall = randomSearch.getReferenceList();
        assertEquals(1, firstCall.size());
        
        List<State> secondCall = randomSearch.getReferenceList();
        assertEquals(2, secondCall.size());
    }

    @Test
    void setWeightUpdatesWeight() {
        randomSearch.setWeight(100f);
        
        assertEquals(100f, randomSearch.getWeight());
    }

    @Test
    void getListCountBetterGenderReturnsArray() {
        int[] counts = randomSearch.getListCountBetterGender();
        
        assertNotNull(counts);
        assertEquals(0, counts[0]);
    }

    @Test
    void getListCountGenderReturnsArray() {
        int[] counts = randomSearch.getListCountGender();
        
        assertNotNull(counts);
        assertEquals(0, counts[0]);
    }

    @Test
    void awardUpdateREFReturnsFalse() {
        State state = createStateWithEvaluation(1.0);
        
        assertEquals(false, randomSearch.awardUpdateREF(state));
    }

    @Test
    void getSonListReturnsNull() {
        assertEquals(null, randomSearch.getSonList());
    }

    private static State createStateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        return state;
    }
}
