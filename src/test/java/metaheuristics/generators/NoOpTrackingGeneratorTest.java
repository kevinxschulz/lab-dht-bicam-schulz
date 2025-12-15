package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import problem.definition.State;

/**
 * Test class for NoOpTrackingGenerator.
 * Tests the default no-op implementations for tracking methods.
 */
class NoOpTrackingGeneratorTest {

    private NoOpTrackingGenerator generator;

    @BeforeEach
    void setUp() {
        // Create a concrete implementation for testing the abstract class
        generator = new NoOpTrackingGenerator() {
            @Override
            public State generate(Integer operatornumber) {
                return null;
            }

            @Override
            public void updateReference(State stateCandidate, Integer countIterationsCurrent) {
            }

            @Override
            public State getReference() {
                return null;
            }

            @Override
            public void setInitialReference(State stateInitialRef) {
            }

            @Override
            public GeneratorType getType() {
                return GeneratorType.RandomSearch;
            }

            @Override
            public List<State> getReferenceList() {
                return null;
            }

            @Override
            public List<State> getSonList() {
                return null;
            }
        };
    }

    @Test
    void getWeightReturnsZero() {
        assertEquals(0, generator.getWeight());
    }

    @Test
    void setWeightDoesNothing() {
        generator.setWeight(100.0f);
        // Should still return 0 since setWeight is a no-op
        assertEquals(0, generator.getWeight());
    }

    @Test
    void getTraceReturnsNull() {
        assertNull(generator.getTrace());
    }

    @Test
    void getListCountBetterGenderReturnsNull() {
        assertNull(generator.getListCountBetterGender());
    }

    @Test
    void getListCountGenderReturnsNull() {
        assertNull(generator.getListCountGender());
    }

    @Test
    void getCountGenderReturnsZero() {
        assertEquals(0, generator.getCountGender());
    }

    @Test
    void setCountGenderDoesNothing() {
        generator.setCountGender(5);
        // Should still return 0 since setCountGender is a no-op
        assertEquals(0, generator.getCountGender());
    }

    @Test
    void getCountBetterGenderReturnsZero() {
        assertEquals(0, generator.getCountBetterGender());
    }

    @Test
    void setCountBetterGenderDoesNothing() {
        generator.setCountBetterGender(10);
        // Should still return 0 since setCountBetterGender is a no-op
        assertEquals(0, generator.getCountBetterGender());
    }

    @Test
    void awardUpdateREFReturnsFalse() {
        State mockState = mock(State.class);
        assertFalse(generator.awardUpdateREF(mockState));
    }
}
