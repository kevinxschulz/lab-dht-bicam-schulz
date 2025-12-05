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

public class OnePointMutationTest {

    @BeforeEach
    public void setUp() {
        Strategy.destroyExecute();
        Problem problem = new Problem();
        // default codification will be overridden by tests if needed
        problem.setCodification(new DeterministicCodification(new int[] {2}, new Object[] {99}, 6));
        Strategy.getStrategy().setProblem(problem);
    }

    @AfterEach
    public void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    public void testNoMutationWhenPMNegative() {
        State state = new State();
        ArrayList<Object> code = new ArrayList<>();
        for (int i = 0; i < 5; i++) code.add(i);
        state.setCode(code);

        OnePointMutation mut = new OnePointMutation();
        // use PM negative to deterministically avoid mutation (PM >= probM will be false)
        State out = mut.mutation(state, -1.0);

        // ensure no change
        for (int i = 0; i < 5; i++) {
            assertEquals(i, out.getCode().get(i));
        }
    }

    @Test
    public void testMutationReplacesAtAleatoryKeyWhenPMOne() {
        // Prepare a state with known content
        State state = new State();
        ArrayList<Object> code = new ArrayList<>();
        for (int i = 0; i < 6; i++) code.add(i);
        state.setCode(code);

        // Use deterministics codification: key=2, value=999
        Strategy.getStrategy().getProblem().setCodification(new DeterministicCodification(new int[] {2}, new Object[] {999}, 6));

        OnePointMutation mut = new OnePointMutation();
        // PM == 1 forces the mutation branch (probM <= 1.0)
        State out = mut.mutation(state, 1.0);

        // index 2 should be replaced with 999
        assertEquals(999, out.getCode().get(2));
        // other indices unchanged
        assertEquals(0, out.getCode().get(0));
        assertEquals(1, out.getCode().get(1));
        assertEquals(3, out.getCode().get(3));
    }

    // Deterministic Codification used in tests
    static class DeterministicCodification extends Codification {
        private final int[] keys;
        private final Object[] values;
        private final AtomicInteger idx = new AtomicInteger(0);
        private final int variableCount;

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
            // return the first value for simplicity
            return values[0];
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
