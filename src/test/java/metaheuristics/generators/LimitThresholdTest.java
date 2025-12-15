package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

class LimitThresholdTest {

    private LimitThreshold lt;
    private Problem mockProblem;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        mockProblem = mock(Problem.class);
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(mockProblem);
        lt = new LimitThreshold();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void getCountGenderReturnsZeroInitially() {
        assertEquals(0, lt.getCountGender());
    }

    @Test
    void setCountGenderUpdatesValue() {
        lt.setCountGender(30);
        assertEquals(30, lt.getCountGender());
        // Reset for other tests
        lt.setCountGender(0);
    }

    @Test
    void getCountBetterGenderReturnsZeroInitially() {
        assertEquals(0, lt.getCountBetterGender());
    }

    @Test
    void setCountBetterGenderUpdatesValue() {
        lt.setCountBetterGender(17);
        assertEquals(17, lt.getCountBetterGender());
        // Reset for other tests
        lt.setCountBetterGender(0);
    }

    @Test
    void getTypeReturnsLimitThreshold() {
        assertEquals(GeneratorType.LimitThreshold, lt.getType());
    }

    @Test
    void getTypeGeneratorReturnsLimitThreshold() {
        assertEquals(GeneratorType.LimitThreshold, lt.getTypeGenerator());
    }

    @Test
    void setTypeGeneratorUpdatesType() {
        lt.setTypeGenerator(GeneratorType.HillClimbing);
        assertEquals(GeneratorType.HillClimbing, lt.getTypeGenerator());
        // Reset
        lt.setTypeGenerator(GeneratorType.LimitThreshold);
    }

    @Test
    void setWeightUpdatesWeight() {
        lt.setWeight(75.5f);
        assertEquals(75.5f, lt.getWeight(), 0.001f);
        // Reset
        lt.setWeight(50f);
    }

    @Test
    void getWeightReturnsInitialWeight() {
        assertEquals(50f, lt.getWeight(), 0.001f);
    }

    @Test
    void getTraceReturnsNonNullArray() {
        float[] trace = lt.getTrace();
        assertNotNull(trace);
        assertEquals(50f, trace[0], 0.001f);
    }

    @Test
    void getListCountBetterGenderReturnsNonNull() {
        int[] counts = lt.getListCountBetterGender();
        assertNotNull(counts);
    }

    @Test
    void getListCountGenderReturnsNonNull() {
        int[] counts = lt.getListCountGender();
        assertNotNull(counts);
    }

    @Test
    void getSonListReturnsNull() {
        assertNull(lt.getSonList());
    }

    @Test
    void awardUpdateREFReturnsFalse() {
        State mockState = mock(State.class);
        assertFalse(lt.awardUpdateREF(mockState));
    }
}
