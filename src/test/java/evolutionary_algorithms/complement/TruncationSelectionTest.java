package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;

public class TruncationSelectionTest {

    @AfterEach
    public void tearDown() {
        // reset the Strategy singleton between tests
        Strategy.destroyExecute();
    }

    @Test
    public void testSelectionMaximizar() {
        // prepare problem and set type to Maximizar
        Problem problem = new Problem();
        problem.setTypeProblem(Problem.ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(problem);

        // create states with evaluations: 5.0, 1.0, 3.0, 4.0
        List<State> list = new ArrayList<>();
        list.add(makeStateWithEval(5.0));
        list.add(makeStateWithEval(1.0));
        list.add(makeStateWithEval(3.0));
        list.add(makeStateWithEval(4.0));

        TruncationSelection sel = new TruncationSelection();
        List<State> result = sel.selection(list, 2);

        assertEquals(2, result.size(), "Truncation should return requested number of elements");
        assertEquals(5.0, result.get(0).getEvaluation().get(0));
        assertEquals(4.0, result.get(1).getEvaluation().get(0));
    }

    @Test
    public void testSelectionMinimizar() {
        // prepare problem and set type to Minimizar
        Problem problem = new Problem();
        problem.setTypeProblem(Problem.ProblemType.Minimizar);
        Strategy.getStrategy().setProblem(problem);

        // create states with evaluations: 5.0, 1.0, 3.0, 4.0
        List<State> list = new ArrayList<>();
        list.add(makeStateWithEval(5.0));
        list.add(makeStateWithEval(1.0));
        list.add(makeStateWithEval(3.0));
        list.add(makeStateWithEval(4.0));

        TruncationSelection sel = new TruncationSelection();
        List<State> result = sel.selection(list, 2);

        assertEquals(2, result.size(), "Truncation should return requested number of elements");
        assertEquals(1.0, result.get(0).getEvaluation().get(0));
        assertEquals(3.0, result.get(1).getEvaluation().get(0));
    }

    private State makeStateWithEval(double value) {
        State s = new State();
        ArrayList<Double> eval = new ArrayList<>();
        eval.add(value);
        s.setEvaluation(eval);
        return s;
    }
}
