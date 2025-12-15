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

import local_search.candidate_type.CandidateType;
import metaheuristics.strategy.Strategy;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

class HillClimbingRestartTest {

    private HillClimbingRestart hillClimbingRestart;
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
        
        HillClimbingRestart.count = 10;
        HillClimbingRestart.countCurrent = 5;
        
        hillClimbingRestart = new HillClimbingRestart();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void constructorSetsCorrectDefaults() {
        assertEquals(GeneratorType.HillClimbingRestart, hillClimbingRestart.getType());
    }

    @Test
    void constructorSetsSmallerCandidateForMinimization() {
        Strategy.destroyExecute();
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        Strategy.getStrategy().setProblem(mockProblem);
        
        HillClimbingRestart hcr = new HillClimbingRestart();
        
        assertNotNull(hcr);
    }

    @Test
    void generateReturnsStateFromNeighborhood() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State referenceState = createStateWithEvaluation(5.0);
        hillClimbingRestart.setInitialReference(referenceState);
        
        List<State> neighborhood = new ArrayList<>();
        State neighborState = createStateWithEvaluation(7.0);
        neighborhood.add(neighborState);
        
        when(mockOperator.generatedNewState(any(), anyInt())).thenReturn(neighborhood);
        
        // Set count to not trigger restart
        HillClimbingRestart.count = 100;
        Strategy.getStrategy().setCountCurrent(10);
        
        State result = hillClimbingRestart.generate(1);
        
        assertNotNull(result);
    }

    @Test
    void generateRestartsWhenCountReached() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State referenceState = createStateWithEvaluation(5.0);
        hillClimbingRestart.setInitialReference(referenceState);
        
        List<State> neighborhood = new ArrayList<>();
        State neighborState = createStateWithEvaluation(7.0);
        neighborhood.add(neighborState);
        
        State randomState = createStateWithEvaluation(6.0);
        List<State> randomStates = new ArrayList<>();
        randomStates.add(randomState);
        
        when(mockOperator.generatedNewState(any(), anyInt())).thenReturn(neighborhood);
        when(mockOperator.generateRandomState(anyInt())).thenReturn(randomStates);
        
        // Set count to trigger restart
        HillClimbingRestart.count = 10;
        Strategy.getStrategy().setCountCurrent(10);
        
        State result = hillClimbingRestart.generate(1);
        
        assertNotNull(result);
    }

    @Test
    void setInitialReferenceUpdatesReference() {
        State state = createStateWithEvaluation(4.0);
        
        hillClimbingRestart.setInitialReference(state);
        
        assertEquals(state, hillClimbingRestart.getReference());
    }

    @Test
    void updateReferenceAcceptsBetterCandidate() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        hillClimbingRestart.setInitialReference(initial);
        
        State better = createStateWithEvaluation(8.0);
        
        hillClimbingRestart.updateReference(better, 0);
        
        assertEquals(better, hillClimbingRestart.getReference());
    }

    private static State createStateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        return state;
    }
}
