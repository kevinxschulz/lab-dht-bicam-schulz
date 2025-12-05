package local_search.acceptation_type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;

public class AcceptBestTest {

    @BeforeEach
    public void setup() {
        Strategy.destroyExecute();
    }

    private State makeState(double v) {
        State s = new State();
        ArrayList<Double> eval = new ArrayList<>();
        eval.add(v);
        s.setEvaluation(eval);
        return s;
    }

    @Test
    public void testAcceptBestMaximizar() throws Exception {
        Problem p = new Problem();
        p.setTypeProblem(Problem.ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(p);

        AcceptBest a = new AcceptBest();
        State current = makeState(3.0);
        State candidateBetter = makeState(5.0);
        State candidateWorse = makeState(2.0);

        assertTrue(a.acceptCandidate(current, candidateBetter));
        assertFalse(a.acceptCandidate(current, candidateWorse));
    }

    @Test
    public void testAcceptBestMinimizar() throws Exception {
        Problem p = new Problem();
        p.setTypeProblem(Problem.ProblemType.Minimizar);
        Strategy.getStrategy().setProblem(p);

        AcceptBest a = new AcceptBest();
        State current = makeState(5.0);
        State candidateBetter = makeState(2.0);
        State candidateWorse = makeState(7.0);

        assertTrue(a.acceptCandidate(current, candidateBetter));
        assertFalse(a.acceptCandidate(current, candidateWorse));
    }
}
