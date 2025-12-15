package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;

class EvolutionStrategiesTest {

    private EvolutionStrategies es;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        // Initialize mapGenerators to avoid NPE
        Strategy.getStrategy().mapGenerators = new TreeMap<>();
        es = new EvolutionStrategies();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void getCountGenderReturnsZeroInitially() {
        assertEquals(0, es.getCountGender());
    }

    @Test
    void setCountGenderUpdatesValue() {
        es.setCountGender(18);
        assertEquals(18, es.getCountGender());
        // Reset for other tests
        es.setCountGender(0);
    }

    @Test
    void getCountBetterGenderReturnsZeroInitially() {
        assertEquals(0, es.getCountBetterGender());
    }

    @Test
    void setCountBetterGenderUpdatesValue() {
        es.setCountBetterGender(14);
        assertEquals(14, es.getCountBetterGender());
        // Reset for other tests
        es.setCountBetterGender(0);
    }
}
