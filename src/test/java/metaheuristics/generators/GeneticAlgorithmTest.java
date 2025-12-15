package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;

class GeneticAlgorithmTest {

    private GeneticAlgorithm ga;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        // Initialize mapGenerators to avoid NPE
        Strategy.getStrategy().mapGenerators = new TreeMap<>();
        ga = new GeneticAlgorithm();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void getCountGenderReturnsZeroInitially() {
        assertEquals(0, ga.getCountGender());
    }

    @Test
    void setCountGenderUpdatesValue() {
        ga.setCountGender(22);
        assertEquals(22, ga.getCountGender());
        // Reset for other tests
        ga.setCountGender(0);
    }

    @Test
    void getCountBetterGenderReturnsZeroInitially() {
        assertEquals(0, ga.getCountBetterGender());
    }

    @Test
    void setCountBetterGenderUpdatesValue() {
        ga.setCountBetterGender(16);
        assertEquals(16, ga.getCountBetterGender());
        // Reset for other tests
        ga.setCountBetterGender(0);
    }
}
