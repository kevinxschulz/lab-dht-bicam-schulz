package local_search.acceptation_type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import metaheuristics.generators.Generator;
import metaheuristics.generators.GeneratorType;
import problem.definition.Problem;
import problem.definition.State;

public class DominanceTest {

    @BeforeEach
    public void setup() {
        Strategy.destroyExecute();
        // provide a minimal stub generator to avoid null pointer when ListDominance checks generator type
        Strategy.getStrategy().generator = new Generator() {
            @Override
            public State generate(Integer operatornumber) { return null; }
            @Override
            public void updateReference(State stateCandidate, Integer countIterationsCurrent) {}
            @Override
            public State getReference() { return null; }
            @Override
            public void setInitialReference(State stateInitialRef) {}
            @Override
            public GeneratorType getType() { return GeneratorType.RandomSearch; }
            @Override
            public java.util.List<State> getReferenceList() { return new ArrayList<>(); }
            @Override
            public java.util.List<State> getSonList() { return new ArrayList<>(); }
            @Override
            public boolean awardUpdateREF(State stateCandidate) { return false; }
            @Override
            public void setWeight(float weight) {}
            @Override
            public float getWeight() { return 0; }
            @Override
            public float[] getTrace() { return new float[0]; }
            @Override
            public int[] getListCountBetterGender() { return new int[0]; }
            @Override
            public int[] getListCountGender() { return new int[0]; }
        };
    }

    private State makeState(double value) {
        State s = new State();
        ArrayList<Double> eval = new ArrayList<>();
        eval.add(value);
        s.setEvaluation(eval);
        return s;
    }

    @Test
    public void testDominanceMaximizar() {
        Problem p = new Problem();
        p.setTypeProblem(Problem.ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(p);

        State x = makeState(5.0);
        State y = makeState(3.0);

        Dominance d = new Dominance();
        assertTrue(d.dominance(x, y));
        assertFalse(d.dominance(y, x));
    }

    @Test
    public void testDominanceMinimizar() {
        Problem p = new Problem();
        p.setTypeProblem(Problem.ProblemType.Minimizar);
        Strategy.getStrategy().setProblem(p);

        State x = makeState(1.0);
        State y = makeState(2.0);

        Dominance d = new Dominance();
        assertTrue(d.dominance(x, y));
        assertFalse(d.dominance(y, x));
    }

    @Test
    public void testListDominanceAddsWhenEmpty() {
        State s = makeState(4.2);
        List<State> list = new ArrayList<>();
        Dominance d = new Dominance();
        boolean added = d.ListDominance(s, list);
        assertTrue(added);
        assertEquals(1, list.size());
        // the stored element should compare equal by code (codes are empty by default)
        assertTrue(list.get(0).Comparator(s));
    }
}
