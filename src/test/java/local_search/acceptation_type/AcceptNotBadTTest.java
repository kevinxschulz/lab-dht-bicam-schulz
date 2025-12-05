package local_search.acceptation_type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheurictics.strategy.Strategy;
import metaheuristics.generators.SimulatedAnnealing;
import problem.definition.Problem;
import problem.definition.State;

public class AcceptNotBadTTest {

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
    public void testAcceptNotBadTDeterministicBranch() throws Exception {
        Problem p = new Problem();
        p.setTypeProblem(Problem.ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(p);

        // ensure tinitial is non-null to avoid NPE
        SimulatedAnnealing.tinitial = 10.0;

        AcceptNotBadT at = new AcceptNotBadT();
        State current = makeState(3.0);
        State candidateBetter = makeState(5.0);

        // candidate better should be accepted deterministically
        assertTrue(at.acceptCandidate(current, candidateBetter));
    }
}
