package local_search.acceptation_type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import problem.definition.State;
import metaheurictics.strategy.Strategy;

public class AcceptAnyoneTest {

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
    public void testAcceptsAlways() {
        AcceptAnyone a = new AcceptAnyone();
        State s1 = makeState(1.0);
        State s2 = makeState(2.0);
        assertTrue(a.acceptCandidate(s1, s2));
    }
}
