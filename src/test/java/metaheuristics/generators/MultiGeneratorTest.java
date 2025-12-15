package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;

/**
 * Test class for MultiGenerator.
 * Tests the portfolio-style generator functionality.
 */
class MultiGeneratorTest {

    private MultiGenerator multiGenerator;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        MultiGenerator.destroyMultiGenerator();
        multiGenerator = new MultiGenerator();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
        MultiGenerator.destroyMultiGenerator();
    }

    @Test
    void constructorSetsCorrectGeneratorType() {
        assertEquals(GeneratorType.MultiGenerator, multiGenerator.getType());
    }

    @Test
    void setGeneratortypeUpdatesType() {
        multiGenerator.setGeneratortype(GeneratorType.HillClimbing);
        assertEquals(GeneratorType.HillClimbing, multiGenerator.getType());
    }

    @Test
    void getReferenceReturnsNull() {
        assertNull(multiGenerator.getReference());
    }

    @Test
    void getReferenceListReturnsStaticList() {
        assertNotNull(multiGenerator.getReferenceList());
    }

    @Test
    void getSonListReturnsNull() {
        assertNull(multiGenerator.getSonList());
    }

    @Test
    void setInitialReferenceDoesNothing() {
        State mockState = mock(State.class);
        // Should not throw exception
        multiGenerator.setInitialReference(mockState);
    }

    @Test
    void getWeightReturnsZero() {
        assertEquals(0, multiGenerator.getWeight());
    }

    @Test
    void getTraceReturnsNull() {
        assertNull(multiGenerator.getTrace());
    }

    @Test
    void setWeightDoesNothing() {
        // Should not throw exception
        multiGenerator.setWeight(100.0f);
    }

    @Test
    void cloneReturnsThis() {
        Object cloned = multiGenerator.clone();
        assertEquals(multiGenerator, cloned);
    }

    @Test
    void destroyMultiGeneratorClearsStaticFields() {
        MultiGenerator.destroyMultiGenerator();
        // After destroy, lists should be cleared/null
        // This verifies the cleanup behavior
        assertNull(MultiGenerator.getActiveGenerator());
        assertNull(MultiGenerator.getListGenerators());
    }

    @Test
    void setListGeneratorsUpdatesStaticArray() {
        Generator[] generators = new Generator[2];
        generators[0] = mock(Generator.class);
        generators[1] = mock(Generator.class);
        
        MultiGenerator.setListGenerators(generators);
        
        assertEquals(generators, MultiGenerator.getListGenerators());
    }

    @Test
    void setActiveGeneratorUpdatesStaticField() {
        Generator mockGenerator = mock(Generator.class);
        
        MultiGenerator.setActiveGenerator(mockGenerator);
        
        assertEquals(mockGenerator, MultiGenerator.getActiveGenerator());
    }
}
