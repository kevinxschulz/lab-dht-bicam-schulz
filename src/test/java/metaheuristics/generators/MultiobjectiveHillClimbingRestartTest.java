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

class MultiobjectiveHillClimbingRestartTest {

    private MultiobjectiveHillClimbingRestart generator;
    private Problem mockProblem;
    private Operator mockOperator;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        mockProblem = mock(Problem.class);
        mockOperator = mock(Operator.class);
        when(mockProblem.getOperator()).thenReturn(mockOperator);
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        
        // Initialize listRefPoblacFinal
        Strategy.getStrategy().listRefPoblacFinal = new ArrayList<>();
        Strategy.getStrategy().setProblem(mockProblem);

        generator = new MultiobjectiveHillClimbingRestart();
        MultiobjectiveHillClimbingRestart.sizeNeighbors = 5;
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void constructorSetsCorrectDefaults() {
        assertEquals(GeneratorType.MultiobjectiveHillClimbingRestart, generator.getType());
    }

    @Test
    void generateReturnsStateFromNeighborhood() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State referenceState = createStateWithEvaluation(5.0);
        generator.setInitialReference(referenceState);
        
        List<State> neighborhood = new ArrayList<>();
        State neighborState = createStateWithEvaluation(7.0);
        neighborhood.add(neighborState);
        
        when(mockOperator.generatedNewState(any(), anyInt())).thenReturn(neighborhood);
        
        State result = generator.generate(1);
        
        assertNotNull(result);
    }

    @Test
    void setInitialReferenceUpdatesReference() {
        State state = createStateWithEvaluation(4.0);
        
        generator.setInitialReference(state);
        
        assertSame(state, generator.getReference());
    }

    @Test
    void getReferenceListClonesAndAddsReference() {
        State state = createStateWithEvaluation(6.0);
        generator.setInitialReference(state);
        
        List<State> firstCall = generator.getReferenceList();
        assertNotNull(firstCall);
        assertEquals(1, firstCall.size());
        
        List<State> secondCall = generator.getReferenceList();
        assertEquals(2, secondCall.size());
    }

    @Test
    void getListCountBetterGenderReturnsArray() {
        int[] counts = generator.getListCountBetterGender();
        
        assertNotNull(counts);
        assertEquals(0, counts[0]);
    }

    @Test
    void getListCountGenderReturnsArray() {
        int[] counts = generator.getListCountGender();
        
        assertNotNull(counts);
        assertEquals(0, counts[0]);
    }

    @Test
    void awardUpdateREFReturnsFalse() {
        State state = createStateWithEvaluation(1.0);
        
        assertEquals(false, generator.awardUpdateREF(state));
    }

    @Test
    void getSonListReturnsNull() {
        assertEquals(null, generator.getSonList());
    }

    @Test
    void getTraceReturnsArray() {
        float[] trace = generator.getTrace();
        
        assertNotNull(trace);
        assertEquals(50f, trace[0]);
    }

    private static State createStateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        return state;
    }

    private static List<State> createStateList(double value) {
        List<State> list = new ArrayList<>();
        list.add(createStateWithEvaluation(value));
        return list;
    }
}
