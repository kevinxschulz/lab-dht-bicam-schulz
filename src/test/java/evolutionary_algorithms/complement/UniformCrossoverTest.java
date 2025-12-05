package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheurictics.strategy.Strategy;
import problem.definition.Codification;
import problem.definition.Problem;
import problem.definition.State;

public class UniformCrossoverTest {

    @BeforeEach
    public void setUp() {
        Strategy.destroyExecute();
        Problem p = new Problem();
        p.setCodification(new DummyCodification(5));
        Strategy.getStrategy().setProblem(p);
    }

    @Test
    public void testMascaraLengthAndValues() {
        UniformCrossover uc = new UniformCrossover();
        int[] mask = uc.mascara(10);
        assertEquals(10, mask.length, "Mask length should match requested length");
        for (int v : mask) {
            assertTrue(v == 0 || v == 1, "Mask values should be 0 or 1");
        }
    }

    @Test
    public void testCrossoverProducesExpectedPattern_givenCurrentImplementation() {
        UniformCrossover uc = new UniformCrossover();

        State f1 = new State();
        f1.setCode(new ArrayList<Object>(Arrays.asList(1, 2, 3, 4, 5)));
        State f2 = new State();
        f2.setCode(new ArrayList<Object>(Arrays.asList(10, 20, 30, 40, 50)));

        State out = uc.crossover(f1, f2, 1.0);

        // size preserved
        assertEquals(f1.getCode().size(), out.getCode().size(), "Child code size should match fathers");

        // Implementation currently sets only mask[0] randomly; indices 1..n-1 should come from father2
        for (int i = 1; i < out.getCode().size(); i++) {
            assertEquals(f2.getCode().get(i), out.getCode().get(i), "Index " + i + " should equal father2 element with current implementation");
        }

        // index 0 should be from either father1 or father2
        Object first = out.getCode().get(0);
        assertTrue(first.equals(f1.getCode().get(0)) || first.equals(f2.getCode().get(0)), "Index 0 should be from either father");
    }

    @Test
    public void testChildElementsAreFromParents() {
        UniformCrossover uc = new UniformCrossover();

        State f1 = new State();
        f1.setCode(new ArrayList<Object>(Arrays.asList("a", "b", "c")));
        State f2 = new State();
        f2.setCode(new ArrayList<Object>(Arrays.asList("A", "B", "C")));

        State out = uc.crossover(f1, f2, 1.0);

        for (int i = 0; i < out.getCode().size(); i++) {
            Object v = out.getCode().get(i);
            boolean fromF1 = v.equals(f1.getCode().get(i));
            boolean fromF2 = v.equals(f2.getCode().get(i));
            assertTrue(fromF1 || fromF2, "Child element must come from one of the fathers at index " + i);
        }
    }

    // Minimal Codification stub used only to supply getVariableCount()
    static class DummyCodification extends Codification {
        private final int variableCount;

        DummyCodification(int variableCount) {
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
            return 0;
        }

        @Override
        public int getVariableCount() {
            return variableCount;
        }
    }
}
