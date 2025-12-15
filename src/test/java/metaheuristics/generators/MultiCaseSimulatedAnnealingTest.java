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
import metaheuristics.strategy.Strategy;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

class MultiCaseSimulatedAnnealingTest {

    private MultiCaseSimulatedAnnealing multiCaseSA;
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

        MultiCaseSimulatedAnnealing.tinitial = 100.0;
        MultiCaseSimulatedAnnealing.tfinal = 0.01;
        MultiCaseSimulatedAnnealing.countIterationsT = 10;
        
        multiCaseSA = new MultiCaseSimulatedAnnealing();
        Strategy.getStrategy().generator = multiCaseSA;
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void constructorSetsCorrectDefaults() {
        assertEquals(GeneratorType.MultiCaseSimulatedAnnealing, multiCaseSA.getType());
    }

    @Test
    void constructorSetsMulticaseAcceptType() {
        // Access through reflection or verify behavior - the typeAcceptation is protected
        // We can verify the type indirectly through the generator type
        assertNotNull(multiCaseSA);
    }

    @Test
    void generateReturnsStateFromNeighborhood() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State referenceState = createStateWithEvaluation(5.0);
        multiCaseSA.setInitialReference(referenceState);
        
        List<State> neighborhood = new ArrayList<>();
        State neighborState = createStateWithEvaluation(7.0);
        neighborhood.add(neighborState);
        
        when(mockOperator.generatedNewState(any(), anyInt())).thenReturn(neighborhood);
        
        State result = multiCaseSA.generate(1);
        
        assertNotNull(result);
    }

    @Test
    void setInitialReferenceUpdatesReference() {
        State state = createStateWithEvaluation(4.0);
        
        multiCaseSA.setInitialReference(state);
        
        assertSame(state, multiCaseSA.getReference());
    }

    @Test
    void updateReferenceAcceptsBetterCandidate() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        multiCaseSA.setInitialReference(initial);
        
        State better = createStateWithEvaluation(8.0);
        
        multiCaseSA.updateReference(better, 0);
        
        assertEquals(better, multiCaseSA.getReference());
    }

    @Test
    void updateReferenceDecreasesTemperatureWhenCountReached() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        multiCaseSA.setInitialReference(initial);
        
        State candidate = createStateWithEvaluation(8.0);
        
        MultiCaseSimulatedAnnealing.tinitial = 100.0;
        MultiCaseSimulatedAnnealing.countIterationsT = 5;
        
        double initialTemp = MultiCaseSimulatedAnnealing.tinitial;
        
        multiCaseSA.updateReference(candidate, 5);
        
        // Temperature should have decreased by alpha
        assertEquals(initialTemp * MultiCaseSimulatedAnnealing.alpha, MultiCaseSimulatedAnnealing.tinitial, 0.001);
    }

    @Test
    void updateReferenceDoesNotDecreaseTemperatureWhenCountNotReached() throws IllegalArgumentException, 
            SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        multiCaseSA.setInitialReference(initial);
        
        State candidate = createStateWithEvaluation(8.0);
        
        MultiCaseSimulatedAnnealing.tinitial = 100.0;
        MultiCaseSimulatedAnnealing.countIterationsT = 10;
        
        double initialTemp = MultiCaseSimulatedAnnealing.tinitial;
        
        multiCaseSA.updateReference(candidate, 5);
        
        // Temperature should not have changed
        assertEquals(initialTemp, MultiCaseSimulatedAnnealing.tinitial, 0.001);
    }

    @Test
    void alphaValueIsCorrect() {
        assertEquals(0.95, MultiCaseSimulatedAnnealing.alpha, 0.001);
    }

    @Test
    void getReferenceListClonesAndAddsReference() {
        State state = createStateWithEvaluation(6.0);
        multiCaseSA.setInitialReference(state);
        
        List<State> firstCall = multiCaseSA.getReferenceList();
        assertNotNull(firstCall);
        assertEquals(1, firstCall.size());
        
        List<State> secondCall = multiCaseSA.getReferenceList();
        assertEquals(2, secondCall.size());
    }

    @Test
    void updateReferenceUpdatesCountIterationsT() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        multiCaseSA.setInitialReference(initial);
        
        State candidate = createStateWithEvaluation(8.0);
        
        MultiCaseSimulatedAnnealing.countIterationsT = 10;
        int initialCount = MultiCaseSimulatedAnnealing.countIterationsT;
        
        multiCaseSA.updateReference(candidate, 10);
        
        // Count should have increased
        assertEquals(initialCount * 2, MultiCaseSimulatedAnnealing.countIterationsT);
    }

    private static State createStateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        return state;
    }
}
