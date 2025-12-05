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

public class AcceptNotDominatedTests {

    @BeforeEach
    public void setup() {
        Strategy.destroyExecute();
        // ensure a minimal generator exists to avoid NPEs in Dominance.ListDominance
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

    private State makeState(double v) {
        State s = new State();
        ArrayList<Double> eval = new ArrayList<>();
        eval.add(v);
        s.setEvaluation(eval);
        return s;
    }

    @Test
    public void testAcceptNotDominatedAddsToList() {
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

        // stub generator to avoid NPEs inside Dominance.ListDominance
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

        AcceptNotDominated an = new AcceptNotDominated();
        State current = makeState(5.0);
        State candidate = makeState(6.0); // candidate that dominates current

        // ensure list is empty
        Strategy.getStrategy().listRefPoblacFinal.clear();

        boolean accepted = an.acceptCandidate(current, candidate);
        assertTrue(accepted);
        assertFalse(Strategy.getStrategy().listRefPoblacFinal.isEmpty());
    }

    @Test
    public void testAcceptNotDominatedTabuAlwaysTrue() {
        Problem p = new Problem();
        p.setFunction(new ArrayList<>());
        p.setTypeProblem(Problem.ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(p);

        AcceptNotDominatedTabu at = new AcceptNotDominatedTabu();
        State current = makeState(2.0);
        State candidate = makeState(3.0);

        Strategy.getStrategy().listRefPoblacFinal.clear();
        boolean returned = at.acceptCandidate(current, candidate);
        assertTrue(returned);
        // list should have been initialized with current
        assertFalse(Strategy.getStrategy().listRefPoblacFinal.isEmpty());
    }
}
