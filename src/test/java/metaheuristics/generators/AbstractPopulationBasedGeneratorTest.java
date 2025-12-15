package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

/**
 * Test class for AbstractPopulationBasedGenerator.
 */
class AbstractPopulationBasedGeneratorTest {

    private AbstractPopulationBasedGenerator generator;
    private Problem mockProblem;

    // Concrete implementation for testing
    private static class TestPopulationGenerator extends AbstractPopulationBasedGenerator {
        public TestPopulationGenerator() {
            super(GeneratorType.GeneticAlgorithm);
        }

        @Override
        public State generate(Integer operatornumber) {
            return null;
        }

        @Override
        public void updateReference(State stateCandidate, Integer countIterationsCurrent) {
            // No-op for testing
        }
    }

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        Strategy.getStrategy().mapGenerators = new java.util.TreeMap<>();
        AbstractLocalSearchGenerator.listStateReference = new ArrayList<>();
        
        mockProblem = mock(Problem.class);
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        Strategy.getStrategy().setProblem(mockProblem);
        
        generator = new TestPopulationGenerator();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void constructorInitializesWeightTo50() {
        assertEquals(50f, generator.getWeight(), 0.001f);
    }

    @Test
    void constructorInitializesTraceArray() {
        float[] trace = generator.getTrace();
        assertNotNull(trace);
        assertEquals(50f, trace[0], 0.001f);
    }

    @Test
    void constructorInitializesCountBetterGenderToZero() {
        assertEquals(0, generator.getCountBetterGender());
    }

    @Test
    void constructorInitializesCountGenderToZero() {
        assertEquals(0, generator.getCountGender());
    }

    @Test
    void getReferenceReturnsFirstStateWhenListNotEmpty() {
        State state1 = createStateWithEvaluation(5.0);
        State state2 = createStateWithEvaluation(3.0);
        State state3 = createStateWithEvaluation(7.0);
        
        generator.referenceList.add(state1);
        generator.referenceList.add(state2);
        generator.referenceList.add(state3);
        
        State reference = generator.getReference();
        assertNotNull(reference);
    }

    @Test
    void getReferenceReturnsLowestEvaluationForMinimization() {
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        
        State state1 = createStateWithEvaluation(5.0);
        State state2 = createStateWithEvaluation(3.0);
        State state3 = createStateWithEvaluation(7.0);
        
        generator.referenceList.add(state1);
        generator.referenceList.add(state2);
        generator.referenceList.add(state3);
        
        State reference = generator.getReference();
        assertEquals(3.0, reference.getEvaluation().get(0), 0.001);
    }

    @Test
    void getReferenceReturnsHighestEvaluationForMaximization() {
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        
        State state1 = createStateWithEvaluation(5.0);
        State state2 = createStateWithEvaluation(3.0);
        State state3 = createStateWithEvaluation(7.0);
        
        generator.referenceList.add(state1);
        generator.referenceList.add(state2);
        generator.referenceList.add(state3);
        
        State reference = generator.getReference();
        assertEquals(7.0, reference.getEvaluation().get(0), 0.001);
    }

    @Test
    void getReferenceReturnsBestReferenceWhenListIsEmpty() {
        State bestState = createStateWithEvaluation(10.0);
        generator.bestReference = bestState;
        
        State reference = generator.getReference();
        assertEquals(bestState, reference);
    }

    @Test
    void getReferenceListReturnsCopyOfList() {
        State state1 = createStateWithEvaluation(5.0);
        State state2 = createStateWithEvaluation(3.0);
        
        generator.referenceList.add(state1);
        generator.referenceList.add(state2);
        
        List<State> copy = generator.getReferenceList();
        
        assertEquals(2, copy.size());
        assertEquals(state1, copy.get(0));
        assertEquals(state2, copy.get(1));
    }

    @Test
    void setInitialReferenceSetsReference() {
        State initialState = createStateWithEvaluation(4.0);
        
        generator.setInitialReference(initialState);
        
        assertEquals(initialState, generator.bestReference);
    }

    @Test
    void getTypeReturnsCorrectGeneratorType() {
        assertEquals(GeneratorType.GeneticAlgorithm, generator.getType());
    }

    @Test
    void getSonListReturnsNull() {
        assertNull(generator.getSonList());
    }

    @Test
    void awardUpdateREFReturnsFalse() {
        State state = createStateWithEvaluation(1.0);
        assertFalse(generator.awardUpdateREF(state));
    }

    @Test
    void setWeightUpdatesWeight() {
        generator.setWeight(100f);
        assertEquals(100f, generator.getWeight(), 0.001f);
    }

    @Test
    void setCountGenderUpdatesValue() {
        generator.setCountGender(20);
        assertEquals(20, generator.getCountGender());
    }

    @Test
    void setCountBetterGenderUpdatesValue() {
        generator.setCountBetterGender(15);
        assertEquals(15, generator.getCountBetterGender());
    }

    @Test
    void getListCountBetterGenderReturnsArray() {
        int[] counts = generator.getListCountBetterGender();
        assertNotNull(counts);
        assertEquals(10, counts.length);
        assertEquals(0, counts[0]);
    }

    @Test
    void getListCountGenderReturnsArray() {
        int[] counts = generator.getListCountGender();
        assertNotNull(counts);
        assertEquals(10, counts.length);
        assertEquals(0, counts[0]);
    }

    @Test
    void getInternalReferenceListReturnsReferenceList() {
        State state = createStateWithEvaluation(5.0);
        generator.referenceList.add(state);
        
        List<State> list = generator.getInternalReferenceList();
        assertEquals(1, list.size());
        assertEquals(state, list.get(0));
    }

    @Test
    void setInternalReferenceListUpdatesReferenceList() {
        List<State> newList = new ArrayList<>();
        State state = createStateWithEvaluation(3.0);
        newList.add(state);
        
        generator.setInternalReferenceList(newList);
        
        assertEquals(newList, generator.referenceList);
    }

    @Test
    void getGeneratorTypeReturnsCorrectType() {
        assertEquals(GeneratorType.GeneticAlgorithm, generator.getGeneratorType());
    }

    @Test
    void setGeneratorTypeUpdatesType() {
        generator.setGeneratorType(GeneratorType.EvolutionStrategies);
        assertEquals(GeneratorType.EvolutionStrategies, generator.getGeneratorType());
    }

    @Test
    void staticCountRefCanBeSet() {
        AbstractPopulationBasedGenerator.countRef = 20;
        assertEquals(20, AbstractPopulationBasedGenerator.countRef);
    }

    @Test
    void staticTruncationCanBeSet() {
        AbstractPopulationBasedGenerator.truncation = 10;
        assertEquals(10, AbstractPopulationBasedGenerator.truncation);
    }

    private static State createStateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        return state;
    }
}
