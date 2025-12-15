package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;

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
}
