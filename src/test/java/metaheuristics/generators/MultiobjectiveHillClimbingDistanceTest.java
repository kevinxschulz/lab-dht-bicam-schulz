package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

class MultiobjectiveHillClimbingDistanceTest {

    private MultiobjectiveHillClimbingDistance generator;
    private Problem mockProblem;
    private Operator mockOperator;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        Strategy.getStrategy().listRefPoblacFinal = new ArrayList<>();
        
        mockProblem = mock(Problem.class);
        mockOperator = mock(Operator.class);
        when(mockProblem.getOperator()).thenReturn(mockOperator);
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(mockProblem);

        MultiobjectiveHillClimbingDistance.sizeNeighbors = 5;
        MultiobjectiveHillClimbingDistance.distanceSolution = new ArrayList<>();
        
        generator = new MultiobjectiveHillClimbingDistance();
        Strategy.getStrategy().generator = generator;
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
        MultiobjectiveHillClimbingDistance.distanceSolution = new ArrayList<>();
    }

    @Test
    void constructorSetsCorrectDefaults() {
        assertEquals(GeneratorType.MultiobjectiveHillClimbingDistance, generator.getType());
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
    void updateReferenceAddsFirstSolutionToParetoFront() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        generator.setInitialReference(initial);
        
        State candidate = createStateWithEvaluation(8.0);
        
        List<State> neighborhood = new ArrayList<>();
        neighborhood.add(candidate);
        when(mockOperator.generatedNewState(any(), anyInt())).thenReturn(neighborhood);
        
        assertTrue(Strategy.getStrategy().listRefPoblacFinal.isEmpty());
        
        generator.updateReference(candidate, 0);
        
        assertEquals(1, Strategy.getStrategy().listRefPoblacFinal.size());
    }

    @Test
    void distanceCalculateAddHandlesSingleSolution() {
        List<State> solutions = new ArrayList<>();
        State state1 = createStateWithEvaluation(5.0);
        solutions.add(state1);
        
        MultiobjectiveHillClimbingDistance.distanceSolution = new ArrayList<>();
        
        List<Double> distances = MultiobjectiveHillClimbingDistance.DistanceCalculateAdd(solutions);
        
        assertNotNull(distances);
    }

    @Test
    void distanceCalculateAddHandlesMultipleSolutions() {
        State state1 = createStateWithEvaluation(5.0);
        State state2 = createStateWithEvaluation(10.0);
        
        List<State> solutions = new ArrayList<>();
        solutions.add(state1);
        solutions.add(state2);
        
        MultiobjectiveHillClimbingDistance.distanceSolution = new ArrayList<>();
        MultiobjectiveHillClimbingDistance.distanceSolution.add(0.0);
        
        List<Double> distances = MultiobjectiveHillClimbingDistance.DistanceCalculateAdd(solutions);
        
        assertNotNull(distances);
        assertEquals(2, distances.size());
    }

    @Test
    void sizeNeighborsCanBeSet() {
        MultiobjectiveHillClimbingDistance.sizeNeighbors = 10;
        assertEquals(10, MultiobjectiveHillClimbingDistance.sizeNeighbors);
        
        // Reset
        MultiobjectiveHillClimbingDistance.sizeNeighbors = 5;
    }

    @Test
    void distanceSolutionListIsAccessible() {
        MultiobjectiveHillClimbingDistance.distanceSolution = new ArrayList<>();
        MultiobjectiveHillClimbingDistance.distanceSolution.add(5.0);
        MultiobjectiveHillClimbingDistance.distanceSolution.add(10.0);
        
        assertEquals(2, MultiobjectiveHillClimbingDistance.distanceSolution.size());
        assertEquals(5.0, MultiobjectiveHillClimbingDistance.distanceSolution.get(0));
        assertEquals(10.0, MultiobjectiveHillClimbingDistance.distanceSolution.get(1));
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
}
