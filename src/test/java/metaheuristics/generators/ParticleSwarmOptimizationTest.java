package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;

class ParticleSwarmOptimizationTest {

    private ParticleSwarmOptimization pso;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        // Initialize mapGenerators to avoid NPE
        Strategy.getStrategy().mapGenerators = new TreeMap<>();
        // Initialize static listStateReference
        AbstractLocalSearchGenerator.listStateReference = new java.util.ArrayList<>();
        pso = new ParticleSwarmOptimization();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void getCountGenderReturnsZeroInitially() {
        assertEquals(0, pso.getCountGender());
    }

    @Test
    void setCountGenderUpdatesValue() {
        pso.setCountGender(25);
        assertEquals(25, pso.getCountGender());
        // Reset for other tests
        pso.setCountGender(0);
    }

    @Test
    void getCountBetterGenderReturnsZeroInitially() {
        assertEquals(0, pso.getCountBetterGender());
    }

    @Test
    void setCountBetterGenderUpdatesValue() {
        pso.setCountBetterGender(13);
        assertEquals(13, pso.getCountBetterGender());
        // Reset for other tests
        pso.setCountBetterGender(0);
    }
}
