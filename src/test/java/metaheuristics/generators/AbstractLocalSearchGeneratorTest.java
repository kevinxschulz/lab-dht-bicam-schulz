package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

class AbstractLocalSearchGeneratorTest {

    @BeforeEach
    void refreshStrategy() {
        Strategy.destroyExecute();
        Problem problem = new Problem();
        problem.setTypeProblem(ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(problem);
    }

    @AfterEach
    void resetStrategy() {
        Strategy.destroyExecute();
    }

    @Test
    void updateReferenceReplacesReferenceWhenCandidateAccepted() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        TestLocalSearchGenerator generator = new TestLocalSearchGenerator();
        State initial = stateWithEvaluation(5.0);
        generator.setInitialReference(initial);
        State candidate = stateWithEvaluation(10.0);

        generator.updateReference(candidate, 0);

        assertSame(candidate, generator.getReference(), "Accepted candidate should become the new reference state");
    }

    @Test
    void getReferenceListAccumulatesReferences() {
        TestLocalSearchGenerator generator = new TestLocalSearchGenerator();
        State reference = stateWithEvaluation(7.0);
        generator.setInitialReference(reference);

        List<State> firstSnapshot = generator.getReferenceList();
        assertEquals(1, firstSnapshot.size());
        assertSame(reference, firstSnapshot.get(0));

        List<State> secondSnapshot = generator.getReferenceList();
        assertEquals(2, secondSnapshot.size());
        assertSame(reference, secondSnapshot.get(1));
    }

    @Test
    void weightStartsAtDefaultAndCanBeChanged() {
        TestLocalSearchGenerator generator = new TestLocalSearchGenerator();
        assertEquals(50f, generator.getWeight());

        generator.setWeight(75f);

        assertEquals(75f, generator.getWeight());
    }

    private static State stateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        return state;
    }

    private static final class TestLocalSearchGenerator extends AbstractLocalSearchGenerator {

        TestLocalSearchGenerator() {
            super(GeneratorType.HillClimbing);
        }

        @Override
        public State generate(Integer operatornumber) {
            return referenceState;
        }
    }
}
