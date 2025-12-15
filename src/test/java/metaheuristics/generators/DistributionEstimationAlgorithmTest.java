package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;

class DistributionEstimationAlgorithmTest {

    private DistributionEstimationAlgorithm algorithm;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        // Initialize mapGenerators to avoid NPE
        Strategy.getStrategy().mapGenerators = new TreeMap<>();
        algorithm = new DistributionEstimationAlgorithm();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void getCountGenderReturnsZeroInitially() {
        assertEquals(0, algorithm.getCountGender());
    }

    @Test
    void setCountGenderUpdatesValue() {
        algorithm.setCountGender(20);
        assertEquals(20, algorithm.getCountGender());
        // Reset for other tests
        algorithm.setCountGender(0);
    }

    @Test
    void getCountBetterGenderReturnsZeroInitially() {
        assertEquals(0, algorithm.getCountBetterGender());
    }

    @Test
    void setCountBetterGenderUpdatesValue() {
        algorithm.setCountBetterGender(11);
        assertEquals(11, algorithm.getCountBetterGender());
        // Reset for other tests
        algorithm.setCountBetterGender(0);
    }
}
