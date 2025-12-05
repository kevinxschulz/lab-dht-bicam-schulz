package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;

public class SteadyStateReplaceTest {

    @BeforeEach
    void beforeEach() {
        Strategy.destroyExecute();
    }

    @Test
    void whenMaximizing_replacesMinimumIfCandidateIsBetterOrEqual() {
        Problem p = new Problem();
        p.setTypeProblem(Problem.ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(p);

        State s0 = new State();
        ArrayList<Double> e0 = new ArrayList<>();
        e0.add(1.0);
        s0.setEvaluation(e0);

        State s1 = new State();
        ArrayList<Double> e1 = new ArrayList<>();
        e1.add(5.0);
        s1.setEvaluation(e1);

        State s2 = new State();
        ArrayList<Double> e2 = new ArrayList<>();
        e2.add(3.0);
        s2.setEvaluation(e2);

        List<State> population = new ArrayList<>();
        population.add(s0);
        population.add(s1);
        population.add(s2);

        SteadyStateReplace replacer = new SteadyStateReplace();

        // candidate is better than the minimum (1.0) -> should replace
        State candidate = new State();
        ArrayList<Double> ce = new ArrayList<>();
        ce.add(2.0);
        candidate.setEvaluation(ce);

        List<State> out = replacer.replace(candidate, population);
        assertEquals(3, out.size());
        assertSame(candidate, out.get(0), "Minimum element (index 0) should be replaced by candidate");

        // candidate worse than minimum -> no replacement
        State candidate2 = new State();
        ArrayList<Double> ce2 = new ArrayList<>();
        ce2.add(0.5);
        candidate2.setEvaluation(ce2);

        List<State> out2 = replacer.replace(candidate2, out);
        assertEquals(3, out2.size());
        // index 0 should still be candidate from previous step
        assertSame(candidate, out2.get(0), "Worse candidate must not replace the current element");
    }

    @Test
    void whenMinimizing_replacesMaximumIfCandidateIsBetterOrEqual() {
        Problem p = new Problem();
        p.setTypeProblem(Problem.ProblemType.Minimizar);
        Strategy.getStrategy().setProblem(p);

        State s0 = new State();
        ArrayList<Double> e0 = new ArrayList<>();
        e0.add(1.0);
        s0.setEvaluation(e0);

        State s1 = new State();
        ArrayList<Double> e1 = new ArrayList<>();
        e1.add(5.0);
        s1.setEvaluation(e1);

        State s2 = new State();
        ArrayList<Double> e2 = new ArrayList<>();
        e2.add(3.0);
        s2.setEvaluation(e2);

        List<State> population = new ArrayList<>();
        population.add(s0);
        population.add(s1);
        population.add(s2);

        SteadyStateReplace replacer = new SteadyStateReplace();

        // candidate is better (smaller) than maximum (5.0) -> should replace element at index 1
        State candidate = new State();
        ArrayList<Double> ce = new ArrayList<>();
        ce.add(4.0);
        candidate.setEvaluation(ce);

        List<State> out = replacer.replace(candidate, population);
        assertEquals(3, out.size());
        assertSame(candidate, out.get(1), "Maximum element (index 1) should be replaced by candidate when minimizing");

        // candidate worse than maximum -> no replacement
        State candidate2 = new State();
        ArrayList<Double> ce2 = new ArrayList<>();
        ce2.add(6.0);
        candidate2.setEvaluation(ce2);

        List<State> out2 = replacer.replace(candidate2, out);
        assertEquals(3, out2.size());
        assertSame(candidate, out2.get(1), "Worse candidate must not replace the current element when minimizing");
    }
}
