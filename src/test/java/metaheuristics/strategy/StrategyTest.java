package metaheuristics.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import local_search.complement.StopExecute;
import local_search.complement.UpdateParameter;
import problem.definition.Problem;
import problem.definition.State;

/**
 * Test class for Strategy.
 * Tests the singleton strategy controller functionality.
 */
class StrategyTest {

    private Strategy strategy;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        strategy = Strategy.getStrategy();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void getStrategyReturnsSingletonInstance() {
        Strategy instance1 = Strategy.getStrategy();
        Strategy instance2 = Strategy.getStrategy();

        assertSame(instance1, instance2);
    }

    @Test
    void setBestStateUpdatesState() {
        State mockState = mock(State.class);

        strategy.setBestState(mockState);

        assertSame(mockState, strategy.getBestState());
    }

    @Test
    void setProblemUpdatesProblem() {
        Problem mockProblem = mock(Problem.class);

        strategy.setProblem(mockProblem);

        assertSame(mockProblem, strategy.getProblem());
    }

    @Test
    void setCountMaxUpdatesMaxCount() {
        strategy.setCountMax(1000);

        assertEquals(1000, strategy.getCountMax());
    }

    @Test
    void setCountCurrentUpdatesCurrentCount() {
        strategy.setCountCurrent(500);

        assertEquals(500, strategy.getCountCurrent());
    }

    @Test
    void setThresholdUpdatesThreshold() {
        strategy.setThreshold(0.75);

        assertEquals(0.75, strategy.getThreshold(), 0.001);
    }

    @Test
    void setStopExecuteUpdatesStopCriteria() {
        StopExecute mockStopExecute = mock(StopExecute.class);

        strategy.setStopexecute(mockStopExecute);

        assertSame(mockStopExecute, strategy.getStopexecute());
    }

    @Test
    void setUpdateParameterUpdatesParameter() {
        UpdateParameter mockUpdateParameter = mock(UpdateParameter.class);

        strategy.setUpdateparameter(mockUpdateParameter);

        assertSame(mockUpdateParameter, strategy.getUpdateparameter());
    }

    @Test
    void listOfflineErrorIsInitialized() {
        assertNotNull(strategy.listOfflineError);
        assertEquals(100, strategy.listOfflineError.length);
    }

    @Test
    void saveListStatesCanBeSet() {
        strategy.saveListStates = true;
        assertEquals(true, strategy.saveListStates);
    }

    @Test
    void saveListBestStatesCanBeSet() {
        strategy.saveListBestStates = true;
        assertEquals(true, strategy.saveListBestStates);
    }

    @Test
    void saveFreneParetoMonoObjetivoCanBeSet() {
        strategy.saveFreneParetoMonoObjetivo = true;
        assertEquals(true, strategy.saveFreneParetoMonoObjetivo);
    }

    @Test
    void calculateTimeCanBeSet() {
        strategy.calculateTime = true;
        assertEquals(true, strategy.calculateTime);
    }

    @Test
    void countPeriodChangeIsInitializedToZero() {
        assertEquals(0, strategy.countPeriodChange);
    }

    @Test
    void countChangeIsInitializedToZero() {
        assertEquals(0, strategy.countChange);
    }

    @Test
    void listStatesCanBeInitialized() {
        strategy.listStates = new java.util.ArrayList<>();
        assertNotNull(strategy.listStates);
    }

    @Test
    void listBestCanBeInitialized() {
        strategy.listBest = new java.util.ArrayList<>();
        assertNotNull(strategy.listBest);
    }

    @Test
    void listRefPoblacFinalCanBeInitialized() {
        strategy.listRefPoblacFinal = new java.util.ArrayList<>();
        assertNotNull(strategy.listRefPoblacFinal);
    }

    @Test
    void generatorFieldCanBeSet() {
        metaheuristics.generators.Generator mockGenerator = mock(metaheuristics.generators.Generator.class);
        strategy.generator = mockGenerator;
        assertSame(mockGenerator, strategy.generator);
    }

    @Test
    void thresholdFieldCanBeSet() {
        strategy.threshold = 0.5;
        assertEquals(0.5, strategy.threshold, 0.001);
    }

    @Test
    void mapGeneratorsCanBeInitialized() {
        strategy.mapGenerators = new java.util.TreeMap<>();
        assertNotNull(strategy.mapGenerators);
    }

    @Test
    void timeExecuteIsInitializedToZero() {
        assertEquals(0, Strategy.timeExecute);
    }

    @Test
    void getListKeyReturnsNonNullWhenMapIsInitialized() {
        strategy.mapGenerators = new java.util.TreeMap<>();
        java.util.List<String> keys = strategy.getListKey();
        assertNotNull(keys);
    }

    @Test
    void notDominatedCanBeSet() {
        local_search.acceptation_type.Dominance mockDominance = mock(local_search.acceptation_type.Dominance.class);
        strategy.notDominated = mockDominance;
        assertSame(mockDominance, strategy.notDominated);
    }
}
