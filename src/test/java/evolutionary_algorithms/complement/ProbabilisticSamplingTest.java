package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Codification;
import problem.definition.Problem;
import problem.definition.State;

public class ProbabilisticSamplingTest {

    @BeforeEach
    public void setUp() {
        Strategy.destroyExecute();
        Problem p = new Problem();
        // default possibleValue and a simple codification
        p.setPossibleValue(5);
        p.setCodification(new SimpleCodification(10));
        Strategy.getStrategy().setProblem(p);
        Strategy.getStrategy().setCountCurrent(7);
    }

    @Test
    public void testListStateInitializesProperly() {
        ProbabilisticSampling ps = new ProbabilisticSampling();
        List<State> list = ps.listState(3);
        assertEquals(3, list.size());
        for (State s : list) {
            assertEquals(7, s.getNumber()); // from Strategy.getStrategy().getCountCurrent()
            // code must be an ArrayList (initialized empty)
            assertTrue(s.getCode() instanceof ArrayList);
            // typeGenerator should be set to DistributionEstimationAlgorithm
            // we only check that it's not null (enum present in State)
            assertTrue(s.getTypeGenerator() != null);
        }
    }

    @Test
    public void testSamplingReturnsValuesFromFathers() {
        ProbabilisticSampling ps = new ProbabilisticSampling();

        // Two fathers, codes length 3, small values so identity comparison in implementation works
        State f1 = new State();
        f1.setCode(new ArrayList<Object>(List.of(1, 2, 3)));
        State f2 = new State();
        f2.setCode(new ArrayList<Object>(List.of(1, 4, 3)));
        List<State> fathers = List.of(f1, f2);

        // request 5 sampled individuals
        List<State> sampled = ps.sampling(fathers, 5);
        assertEquals(5, sampled.size());
        int cantV = fathers.get(0).getCode().size();
        for (State s : sampled) {
            assertEquals(cantV, s.getCode().size());
            // each produced value at index i must be one of the fathers' values at that index
            for (int i = 0; i < cantV; i++) {
                Object val = s.getCode().get(i);
                Set<Object> allowed = new HashSet<>();
                allowed.add(f1.getCode().get(i));
                allowed.add(f2.getCode().get(i));
                assertTrue(allowed.contains(val), "sampled value must come from fathers at position " + i);
            }
        }
    }

    @Test
    public void testSamplingWhenAllFathersMinusOneProducesInRange() {
        ProbabilisticSampling ps = new ProbabilisticSampling();

        // Fathers carry -1 for every position to force the 'find==false' branch
        State f1 = new State();
        f1.setCode(new ArrayList<Object>(List.of(-1, -1, -1)));
        State f2 = new State();
        f2.setCode(new ArrayList<Object>(List.of(-1, -1, -1)));
        List<State> fathers = List.of(f1, f2);

        // codification variableCount is 10 (from SimpleCodification), so generated values range [0, variableCount*10)
        Strategy.getStrategy().getProblem().setCodification(new SimpleCodification(10));

        List<State> sampled = ps.sampling(fathers, 4);
        assertEquals(4, sampled.size());
        for (State s : sampled) {
            for (Object v : s.getCode()) {
                int vi = (Integer) v;
                assertTrue(vi >= 0 && vi < (10 * 10), "generated fallback value must be within expected range");
            }
        }
    }

    // Minimal Codification stub used by the tests
    static class SimpleCodification extends Codification {
        private final int variableCount;

        SimpleCodification(int variableCount) {
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
