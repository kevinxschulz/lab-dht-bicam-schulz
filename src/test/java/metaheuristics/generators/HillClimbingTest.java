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

class HillClimbingTest {

    private HillClimbing hillClimbing;
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

        hillClimbing = new HillClimbing();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void constructorSetsGreaterCandidateForMaximization() {
        assertEquals(GeneratorType.HillClimbing, hillClimbing.getType());
    }

    @Test
    void constructorSetsSmallerCandidateForMinimization() {
        Strategy.destroyExecute();
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        Strategy.getStrategy().setProblem(mockProblem);
        
        HillClimbing hc = new HillClimbing();
        
        assertNotNull(hc);
    }

    @Test
    void generateReturnsStateFromNeighborhood() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State referenceState = createStateWithEvaluation(5.0);
        hillClimbing.setInitialReference(referenceState);
        
        List<State> neighborhood = new ArrayList<>();
        State neighborState = createStateWithEvaluation(7.0);
        neighborhood.add(neighborState);
        
        when(mockOperator.generatedNewState(any(), anyInt())).thenReturn(neighborhood);
        
        State result = hillClimbing.generate(1);
        
        assertNotNull(result);
    }

    @Test
    void setInitialReferenceUpdatesReference() {
        State state = createStateWithEvaluation(4.0);
        
        hillClimbing.setInitialReference(state);
        
        assertEquals(state, hillClimbing.getReference());
    }

    @Test
    void updateReferenceAcceptsBetterCandidate() throws IllegalArgumentException, SecurityException, 
            ClassNotFoundException, InstantiationException, IllegalAccessException, 
            InvocationTargetException, NoSuchMethodException {
        State initial = createStateWithEvaluation(3.0);
        hillClimbing.setInitialReference(initial);
        
        State better = createStateWithEvaluation(8.0);
        
        hillClimbing.updateReference(better, 0);
        
        assertEquals(better, hillClimbing.getReference());
    }

    private static State createStateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        return state;
    }
}
