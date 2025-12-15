package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import local_search.candidate_type.CandidateType;
import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

class AbstractHillClimbingGeneratorTest {

    @BeforeEach
    void prepareStrategy() {
        configureStrategy(ProblemType.Maximizar);
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void constructorSetsGreaterCandidateForMaxProblems() {
        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();

        assertEquals(CandidateType.GreaterCandidate, generator.exposedCandidateType());
    }

    @Test
    void constructorSetsSmallerCandidateForMinProblems() {
        configureStrategy(ProblemType.Minimizar);

        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();

        assertEquals(CandidateType.SmallerCandidate, generator.exposedCandidateType());
    }

    @Test
    void weightDefaultsToFiftyAndCanChange() {
        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();
        assertEquals(50f, generator.getWeight());

        generator.setWeight(25f);

        assertEquals(25f, generator.getWeight());
    }

    @Test
    void setStateRefOverridesReference() {
        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();
        State original = stateWithEvaluation(2.0);
        State replacement = stateWithEvaluation(8.0);
        generator.setInitialReference(original);

        generator.setStateRef(replacement);

        assertSame(replacement, generator.getReference());
    }

    @Test
    void generatorTypeAccessorsStayInSync() {
        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();
        assertEquals(GeneratorType.HillClimbing, generator.getGeneratorType());
        assertEquals(GeneratorType.HillClimbing, generator.getType());

        generator.setGeneratorType(GeneratorType.SimulatedAnnealing);

        assertEquals(GeneratorType.SimulatedAnnealing, generator.getGeneratorType());
        assertEquals(GeneratorType.SimulatedAnnealing, generator.getType());
    }

    @Test
    void updateReferenceUsesAcceptBestLogic() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();
        State initial = stateWithEvaluation(3.0);
        generator.setInitialReference(initial);
        State better = stateWithEvaluation(9.0);

        generator.updateReference(better, 0);

        assertSame(better, generator.getReference());
    }

    @Test
    void awardUpdateRefReturnsFalseAndKeepsReference() {
        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();
        State reference = stateWithEvaluation(1.0);
        generator.setInitialReference(reference);
        State candidate = stateWithEvaluation(5.0);

        assertFalse(generator.awardUpdateREF(candidate));
        assertSame(reference, generator.getReference());
    }

    @Test
    void getReferenceListReturnsLatestReference() {
        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();
        State reference = stateWithEvaluation(4.0);
        generator.setInitialReference(reference);

        List<State> firstCall = generator.getReferenceList();
        assertEquals(1, firstCall.size());
        assertSame(reference, firstCall.get(0));

        List<State> secondCall = generator.getReferenceList();
        assertEquals(2, secondCall.size());
        assertSame(reference, secondCall.get(1));
    }

    @Test
    void typeCandidateCanBeOverriddenManually() {
        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();

        generator.setTypeCandidate(CandidateType.RandomCandidate);

        assertEquals(CandidateType.RandomCandidate, generator.exposedCandidateType());
    }

    @Test
    void getSonListReturnsNull() {
        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();

        assertNull(generator.getSonList());
    }

    @Test
    void traceAndCountersExposeDefaults() {
        TestHillClimbingGenerator generator = new TestHillClimbingGenerator();

        assertEquals(50f, generator.getTrace()[0]);
        assertEquals(0, generator.getListCountBetterGender()[0]);
        assertEquals(0, generator.getListCountGender()[0]);
    }

    private static void configureStrategy(ProblemType type) {
        Strategy.destroyExecute();
        Problem problem = new Problem();
        problem.setTypeProblem(type);
        Strategy.getStrategy().setProblem(problem);
    }

    private static State stateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        return state;
    }

    private static final class TestHillClimbingGenerator extends AbstractHillClimbingGenerator {

        TestHillClimbingGenerator() {
            super(GeneratorType.HillClimbing);
        }

        CandidateType exposedCandidateType() {
            return this.typeCandidate;
        }

        @Override
        public State generate(Integer operatornumber) {
            return referenceState;
        }
    }
}
