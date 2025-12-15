package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

class SimulatedAnnealingTest {

    private SimulatedAnnealing simulatedAnnealing;
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

        SimulatedAnnealing.tinitial = 100.0;
        SimulatedAnnealing.tfinal = 0.01;
        SimulatedAnnealing.countIterationsT = 10;
        
        simulatedAnnealing = new SimulatedAnnealing();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void constructorSetsCorrectDefaults() {
        assertEquals(GeneratorType.SimulatedAnnealing, simulatedAnnealing.getType());
        assertEquals(GeneratorType.SimulatedAnnealing, simulatedAnnealing.getTypeGenerator());
    }

    @Test
    void setTypeGeneratorUpdatesType() {
        simulatedAnnealing.setTypeGenerator(GeneratorType.HillClimbing);
        
        assertEquals(GeneratorType.HillClimbing, simulatedAnnealing.getTypeGenerator());
    }

    @Test
    void generateReturnsStateFromNeighborhood() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State referenceState = createStateWithEvaluation(5.0);
        simulatedAnnealing.setInitialReference(referenceState);
        
        List<State> neighborhood = new ArrayList<>();
        State neighborState = createStateWithEvaluation(7.0);
        neighborhood.add(neighborState);
        
        when(mockOperator.generatedNewState(any(), anyInt())).thenReturn(neighborhood);
        
        State result = simulatedAnnealing.generate(1);
        
        assertNotNull(result);
    }

    @Test
    void setInitialReferenceUpdatesReference() {
        State state = createStateWithEvaluation(4.0);
        
        simulatedAnnealing.setInitialReference(state);
        
        assertEquals(state, simulatedAnnealing.getReference());
    }

    @Test
    void updateReferenceAcceptsBetterCandidate() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        simulatedAnnealing.setInitialReference(initial);
        
        State better = createStateWithEvaluation(8.0);
        
        simulatedAnnealing.updateReference(better, 0);
        
        assertEquals(better, simulatedAnnealing.getReference());
    }

    @Test
    void updateReferenceDecreasesTemperatureWhenCountReached() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        simulatedAnnealing.setInitialReference(initial);
        
        State candidate = createStateWithEvaluation(8.0);
        
        SimulatedAnnealing.tinitial = 100.0;
        SimulatedAnnealing.countIterationsT = 5;
        
        double initialTemp = SimulatedAnnealing.tinitial;
        
        simulatedAnnealing.updateReference(candidate, 5);
        
        // Temperature should have decreased by alpha
        assertEquals(initialTemp * SimulatedAnnealing.alpha, SimulatedAnnealing.tinitial, 0.001);
    }

    @Test
    void updateReferenceDoesNotDecreaseTemperatureWhenCountNotReached() throws IllegalArgumentException, 
            SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        simulatedAnnealing.setInitialReference(initial);
        
        State candidate = createStateWithEvaluation(8.0);
        
        SimulatedAnnealing.tinitial = 100.0;
        SimulatedAnnealing.countIterationsT = 10;
        
        double initialTemp = SimulatedAnnealing.tinitial;
        
        simulatedAnnealing.updateReference(candidate, 5);
        
        // Temperature should not have changed
        assertEquals(initialTemp, SimulatedAnnealing.tinitial, 0.001);
    }

    @Test
    void alphaValueIsCorrect() {
        assertEquals(0.93, SimulatedAnnealing.alpha, 0.001);
    }

    private static State createStateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        return state;
    }
}
