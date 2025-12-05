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

public class OnePointCrossoverTest {

    @BeforeEach
    public void setUp() {
        Strategy.destroyExecute();
        Problem p = new Problem();
        p.setCodification(new DummyCodification(5));
        Strategy.getStrategy().setProblem(p);
    }

    @Test
    public void testNoCrossoverWhenPCZero() {
        OnePointCrossover op = new OnePointCrossover();

        State f1 = new State();
        f1.setCode(new ArrayList<Object>(Arrays.asList(1, 2, 3, 4)));
        State f2 = new State();
        f2.setCode(new ArrayList<Object>(Arrays.asList(9, 9, 9, 9)));

        State out = op.crossover(f1, f2, 0.0); // PC == 0 => no crossover

        assertEquals(f1.getCode(), out.getCode(), "When PC==0 result should equal father1 code");
    }

    @Test
    public void testCrossoverWhenPCOneAndEqualFathers() {
        OnePointCrossover op = new OnePointCrossover();

        State f1 = new State();
        f1.setCode(new ArrayList<Object>(Arrays.asList(1, 2, 3, 4)));
        State f2 = new State();
        f2.setCode(new ArrayList<Object>(Arrays.asList(1, 2, 3, 4)));

        State out = op.crossover(f1, f2, 1.0); // PC == 1 forces crossover branch, but fathers identical

        assertEquals(f1.getCode(), out.getCode(), "If both fathers equal, child must equal them after crossover");
    }

    @Test
    public void testCrossoverWhenPCOneProducesOneOfExpectedChildren() {
        OnePointCrossover op = new OnePointCrossover();

        // Due to implementation detail pos ends up being 0; build expected children accordingly
        State f1 = new State();
        f1.setCode(new ArrayList<Object>(Arrays.asList(1, 2, 3, 4, 5)));
        State f2 = new State();
        f2.setCode(new ArrayList<Object>(Arrays.asList(10, 20, 30, 40, 50)));

        State out = op.crossover(f1, f2, 1.0);

        List<Object> expected1 = Arrays.asList(1, 20, 30, 40, 50); // ind1 when pos==0
        List<Object> expected2 = Arrays.asList(10, 2, 3, 4, 5);    // ind2 when pos==0

        List<Object> outCode = out.getCode();
        assertTrue(outCode.equals(expected1) || outCode.equals(expected2), "Result should be one of the two expected children");
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
