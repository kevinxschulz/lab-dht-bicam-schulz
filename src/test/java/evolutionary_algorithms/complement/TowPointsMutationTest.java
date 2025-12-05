package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheurictics.strategy.Strategy;
import problem.definition.Codification;
import problem.definition.Problem;
import problem.definition.State;

public class TowPointsMutationTest {

    @BeforeEach
    public void setUp() {
        Strategy.destroyExecute();
        Problem problem = new Problem();
        // placeholder codification; specific tests will override
        problem.setCodification(new DeterministicCodification(new int[] {0}, new Object[] {0}, 1));
        Strategy.getStrategy().setProblem(problem);
    }

    @AfterEach
    public void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    public void testMutationSwapsTwoPositions() {
        State state = new State();
        ArrayList<Object> code = new ArrayList<>();
        code.add(10);
        code.add(20);
        code.add(30);
        code.add(40);
        state.setCode(code);

        // codification will return keys 1 and 3 in sequence and values matching the initial code
        Strategy.getStrategy().getProblem().setCodification(
                new DeterministicCodification(new int[] {1, 3}, new Object[] {10, 20, 30, 40}, 4) {
                    @Override
                    public Object getVariableAleatoryValue(int key) {
                        return values[key];
                    }
                }
        );

        TowPointsMutation mut = new TowPointsMutation();
        State out = mut.mutation(state, 1.0);

        // positions 1 and 3 should be swapped
        assertEquals(10, out.getCode().get(0));
        assertEquals(40, out.getCode().get(1));
        assertEquals(30, out.getCode().get(2));
        assertEquals(20, out.getCode().get(3));
    }

    @Test
    public void testMutationSameIndexTwiceLeavesUnchanged() {
        State state = new State();
        ArrayList<Object> code = new ArrayList<>();
        code.add(7);
        code.add(8);
        code.add(9);
        state.setCode(code);

        // codification returns the same key twice
        Strategy.getStrategy().getProblem().setCodification(
                new DeterministicCodification(new int[] {2, 2}, new Object[] {7, 8, 9}, 3) {
                    @Override
                    public Object getVariableAleatoryValue(int key) {
                        return values[key];
                    }
                }
        );

        TowPointsMutation mut = new TowPointsMutation();
        State out = mut.mutation(state, 1.0);

        // No change expected when both keys are the same
        assertEquals(7, out.getCode().get(0));
        assertEquals(8, out.getCode().get(1));
        assertEquals(9, out.getCode().get(2));
    }

    // Deterministic Codification used in tests
    static class DeterministicCodification extends Codification {
        final int[] keys;
        final Object[] values;
        final AtomicInteger idx = new AtomicInteger(0);
        final int variableCount;

        DeterministicCodification(int[] keys, Object[] values, int variableCount) {
            this.keys = keys.clone();
            this.values = values.clone();
            this.variableCount = variableCount;
        }

        @Override
        public boolean validState(State state) {
            return true;
        }

        @Override
        public Object getVariableAleatoryValue(int key) {
            // default to first value if out of range
            if (key < 0 || key >= values.length) return values[0];
            return values[key];
        }

        @Override
        public int getAleatoryKey() {
            int i = idx.getAndIncrement();
            return keys[i % keys.length];
        }

        @Override
        public int getVariableCount() {
            return variableCount;
        }
    }
}
