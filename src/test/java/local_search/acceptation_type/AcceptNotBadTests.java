package local_search.acceptation_type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import metaheuristics.generators.Generator;
import metaheuristics.generators.GeneratorType;
import problem.definition.ObjetiveFunction;
import problem.definition.Problem;
import problem.definition.State;

public class AcceptNotBadTests {

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
    public void testAcceptNotBadBehavior() {
        Problem p = new Problem();
        // ensure function list has one objective so the loop runs
        ArrayList<ObjetiveFunction> funcs = new ArrayList<>();
        funcs.add(new ObjetiveFunction() {
            @Override
            public Double Evaluation(problem.definition.State state) {
                return state.getEvaluation().get(0);
            }
        });
        p.setFunction(funcs);
        p.setTypeProblem(Problem.ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(p);

        // provide a minimal stub generator to avoid null checks inside Dominance/ListDominance
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
            @Override
            public int getCountGender() { return 0; }
            @Override
            public void setCountGender(int countGender) {}
            @Override
            public int getCountBetterGender() { return 0; }
            @Override
            public void setCountBetterGender(int countBetterGender) {}
        };

        AcceptNotBad a = new AcceptNotBad();
        State current = makeState(4.0);
        State candidateBetter = makeState(5.0);
        State candidateWorse = makeState(3.0);

        assertTrue(a.acceptCandidate(current, candidateBetter));
        assertFalse(a.acceptCandidate(current, candidateWorse));
    }

    @Test
    public void testAcceptNotBadUThreshold() throws Exception {
        Problem p = new Problem();
        ArrayList<ObjetiveFunction> funcs = new ArrayList<>();
        funcs.add(new ObjetiveFunction() {
            @Override
            public Double Evaluation(problem.definition.State state) {
                return state.getEvaluation().get(0);
            }
        });
        p.setFunction(funcs);
        p.setTypeProblem(Problem.ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(p);
        Strategy.getStrategy().setThreshold(0.5);

        AcceptNotBadU au = new AcceptNotBadU();
        State current = makeState(5.0);
        State candidateClose = makeState(4.6);
        State candidateFar = makeState(4.0);

        assertTrue(au.acceptCandidate(current, candidateClose));
        assertFalse(au.acceptCandidate(current, candidateFar));
    }
}
