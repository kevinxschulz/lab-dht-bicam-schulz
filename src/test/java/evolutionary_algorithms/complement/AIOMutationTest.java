package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import config.tspDynamic.TSPState;
import metaheuristics.strategy.Strategy;
import problem.definition.Codification;
import problem.definition.Problem;
import problem.definition.State;

public class AIOMutationTest {

    @BeforeEach
    public void setUp() {
        // Reset singleton and set a default problem; each test will override codification if needed
        Strategy.destroyExecute();
        Problem problem = new Problem();
        problem.setCodification(new SequencedCodification(new int[] {1, 4}, 6));
        Strategy.getStrategy().setProblem(problem);
    }

    @AfterEach
    public void tearDown() {
        Strategy.destroyExecute();
        AIOMutation.path.clear();
    }

    @Test
    public void testMutationReversesSubpathOnSortedValues() {
        State state = new State();
        ArrayList<Object> code = new ArrayList<>();
        // values ascending: 10,20,30,40,50,60 and idCity 1..6
        for (int i = 1; i <= 6; i++) {
            TSPState ts = new TSPState();
            ts.setValue(i * 10);
            ts.setIdCity(i);
            code.add(ts);
        }
        state.setCode(code);

        AIOMutation mut = new AIOMutation();
        State result = mut.mutation(state, 0.0);

        ArrayList<Object> resCode = result.getCode();
        int[] expected = {1, 5, 4, 3, 2, 6};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], ((TSPState) resCode.get(i)).getIdCity(), "idCity at index " + i);
        }

        assertTrue(AIOMutation.path.isEmpty(), "static path should be cleared after mutation");
    }

    @Test
    public void testMutationAfterSortingUnsortedValues() {
        State state = new State();
        ArrayList<Object> code = new ArrayList<>();
        // unsorted values so sortedPathValue will reorder by value before reversal
        int[] values = {30, 10, 20, 60, 40, 50};
        for (int i = 0; i < values.length; i++) {
            TSPState ts = new TSPState();
            ts.setValue(values[i]);
            ts.setIdCity(i + 1);
            code.add(ts);
        }
        state.setCode(code);

        // Use same codification sequence (1,4)
        Strategy.getStrategy().getProblem().setCodification(new SequencedCodification(new int[] {1, 4}, 6));

        AIOMutation mut = new AIOMutation();
        State result = mut.mutation(state, 0.0);

        ArrayList<Object> resCode = result.getCode();

        // compute expected result manually: sorted by value gives idCities in order [2,3,1,5,6,4]
        // reversing indices 1..4 (positions 1..4 inclusive) yields [2,6,5,1,3,4]
        int[] expected = {2, 6, 5, 1, 3, 4};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], ((TSPState) resCode.get(i)).getIdCity(), "idCity at index " + i);
        }
    }

    // Helper codification returning a deterministic sequence of keys
    static class SequencedCodification extends Codification {
        private final int[] seq;
        private final AtomicInteger idx = new AtomicInteger(0);
        private final int variableCount;

        SequencedCodification(int[] seq, int variableCount) {
            this.seq = seq.clone();
            this.variableCount = variableCount;
        }

        @Override
        public boolean validState(State state) {
            return true;
        }

        @Override
        public Object getVariableAleatoryValue(int key) {
            return null;
        }

        @Override
        public int getAleatoryKey() {
            int i = idx.getAndIncrement();
            return seq[i % seq.length];
        }

        @Override
        public int getVariableCount() {
            return variableCount;
        }
    }
}
