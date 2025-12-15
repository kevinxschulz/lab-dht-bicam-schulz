package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
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
import local_search.complement.StrategyType;
import metaheuristics.strategy.Strategy;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

/**
 * Test class for MultiobjectiveTabuSearch.
 * Tests the multi-objective tabu search generator functionality.
 */
class MultiobjectiveTabuSearchTest {

    private MultiobjectiveTabuSearch tabuSearch;
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

        tabuSearch = new MultiobjectiveTabuSearch();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void constructorSetsCorrectDefaults() {
        assertEquals(GeneratorType.MultiobjectiveTabuSearch, tabuSearch.getType());
        assertEquals(AcceptType.AcceptNotDominatedTabu, tabuSearch.typeAcceptation);
        assertEquals(StrategyType.TABU, tabuSearch.strategy);
        assertEquals(CandidateType.RandomCandidate, tabuSearch.typeCandidate);
        assertEquals(50f, tabuSearch.getWeight());
    }

    @Test
    void getTypeReturnsCorrectGeneratorType() {
        assertEquals(GeneratorType.MultiobjectiveTabuSearch, tabuSearch.getType());
    }

    @Test
    void setTypeGeneratorUpdatesGeneratorType() {
        tabuSearch.setTypeGenerator(GeneratorType.TabuSearch);
        assertEquals(GeneratorType.TabuSearch, tabuSearch.getTypeGenerator());
    }

    @Test
    void setInitialReferenceUpdatesReferenceState() {
        State mockState = createStateWithEvaluation(5.0);
        tabuSearch.setInitialReference(mockState);
        assertSame(mockState, tabuSearch.getReference());
    }

    @Test
    void getReferenceListReturnsNonNullList() {
        State initialState = createStateWithEvaluation(5.0);
        tabuSearch.setInitialReference(initialState);
        
        List<State> referenceList = tabuSearch.getReferenceList();
        
        assertNotNull(referenceList);
    }

    @Test
    void getStateReferenceTSReturnsReferenceState() {
        State mockState = createStateWithEvaluation(5.0);
        tabuSearch.setInitialReference(mockState);
        
        assertSame(mockState, tabuSearch.getStateReferenceTS());
    }

    @Test
    void setStateReferenceTSUpdatesReferenceState() {
        State mockState = createStateWithEvaluation(7.0);
        
        tabuSearch.setStateReferenceTS(mockState);
        
        assertSame(mockState, tabuSearch.getStateReferenceTS());
    }

    @Test
    void getSonListReturnsNull() {
        assertEquals(null, tabuSearch.getSonList());
    }

    private State createStateWithEvaluation(double value) {
        State state = mock(State.class);
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        when(state.getEvaluation()).thenReturn(evaluation);
        return state;
    }
}
