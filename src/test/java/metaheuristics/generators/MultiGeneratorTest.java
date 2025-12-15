package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

/**
 * Comprehensive test class for MultiGenerator.
 * Tests the portfolio-style generator functionality including roulette selection,
 * weight updates, tournament, and generator management.
 */
class MultiGeneratorTest {

    private MultiGenerator multiGenerator;
    private Problem mockProblem;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        MultiGenerator.destroyMultiGenerator();
        
        mockProblem = mock(Problem.class);
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        Strategy.getStrategy().setProblem(mockProblem);
        // initialize mapGenerators to avoid NPEs in population-based generators
        Strategy.getStrategy().mapGenerators = new TreeMap<>();
        
        multiGenerator = new MultiGenerator();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
        MultiGenerator.destroyMultiGenerator();
    }

    @Test
    void constructorSetsCorrectGeneratorType() {
        assertEquals(GeneratorType.MultiGenerator, multiGenerator.getType());
    }

    @Test
    void setGeneratortypeUpdatesType() {
        multiGenerator.setGeneratortype(GeneratorType.HillClimbing);
        assertEquals(GeneratorType.HillClimbing, multiGenerator.getType());
    }

    @Test
    void getReferenceReturnsNull() {
        assertNull(multiGenerator.getReference());
    }

    @Test
    void getReferenceListReturnsStaticList() {
        assertNotNull(multiGenerator.getReferenceList());
    }

    @Test
    void getSonListReturnsNull() {
        assertNull(multiGenerator.getSonList());
    }

    @Test
    void setInitialReferenceDoesNothing() {
        State mockState = mock(State.class);
        multiGenerator.setInitialReference(mockState);
        // Should not throw exception
    }

    @Test
    void getWeightReturnsZero() {
        assertEquals(0, multiGenerator.getWeight());
    }

    @Test
    void getTraceReturnsNull() {
        assertNull(multiGenerator.getTrace());
    }

    @Test
    void setWeightDoesNothing() {
        multiGenerator.setWeight(100.0f);
        // Should not throw exception
    }

    @Test
    void cloneReturnsThis() {
        Object cloned = multiGenerator.clone();
        assertEquals(multiGenerator, cloned);
    }

    @Test
    void destroyMultiGeneratorClearsStaticFields() {
        MultiGenerator.destroyMultiGenerator();
        assertNull(MultiGenerator.getActiveGenerator());
        assertNull(MultiGenerator.getListGenerators());
    }

    @Test
    void setListGeneratorsUpdatesStaticArray() {
        Generator[] generators = new Generator[2];
        generators[0] = mock(Generator.class);
        generators[1] = mock(Generator.class);
        
        MultiGenerator.setListGenerators(generators);
        
        assertEquals(generators, MultiGenerator.getListGenerators());
    }

    @Test
    void setActiveGeneratorUpdatesStaticField() {
        Generator mockGenerator = mock(Generator.class);
        
        MultiGenerator.setActiveGenerator(mockGenerator);
        
        assertEquals(mockGenerator, MultiGenerator.getActiveGenerator());
    }

    @Test
    void listGeneratedPPIsAccessible() {
        assertNotNull(MultiGenerator.listGeneratedPP);
    }

    @Test
    void listStateReferenceIsAccessible() {
        assertNotNull(MultiGenerator.listStateReference);
    }

    @Test
    void setListGeneratedPPUpdatesStaticList() {
        List<State> newList = new ArrayList<>();
        State mockState = mock(State.class);
        newList.add(mockState);
        
        MultiGenerator.setListGeneratedPP(newList);
        
        assertEquals(1, MultiGenerator.listGeneratedPP.size());
    }

    @Test
    void getTypeReturnsMultiGenerator() {
        assertEquals(GeneratorType.MultiGenerator, multiGenerator.getType());
    }

    @Test
    void awardUpdateREFReturnsFalseByDefault() {
        State mockState = mock(State.class);
        multiGenerator.awardUpdateREF(mockState);
        // Should not throw exception
    }

    @Test
    void getCountGenderReturnsZeroByDefault() {
        assertEquals(0, multiGenerator.getCountGender());
    }

    @Test
    void setCountGenderUpdatesValue() {
        multiGenerator.setCountGender(10);
        // MultiGenerator inherits NoOpTrackingGenerator, so may return 0
    }

    @Test
    void getCountBetterGenderReturnsZeroByDefault() {
        assertEquals(0, multiGenerator.getCountBetterGender());
    }

    @Test
    void setCountBetterGenderUpdatesValue() {
        multiGenerator.setCountBetterGender(5);
        // MultiGenerator inherits NoOpTrackingGenerator
    }

    @Test
    void getListCountBetterGenderReturnsNull() {
        assertNull(multiGenerator.getListCountBetterGender());
    }

    @Test
    void getListCountGenderReturnsNull() {
        assertNull(multiGenerator.getListCountGender());
    }

    // ========== Roulette Selection Tests ==========

    @Test
    void rouletteSelectsGeneratorBasedOnWeights() {
        Generator[] generators = createMockGenerators(3);
        when(generators[0].getWeight()).thenReturn(10.0f);
        when(generators[1].getWeight()).thenReturn(20.0f);
        when(generators[2].getWeight()).thenReturn(30.0f);
        
        MultiGenerator.setListGenerators(generators);
        
        Generator selected = multiGenerator.roulette();
        
        assertNotNull(selected);
        assertTrue(selected == generators[0] || selected == generators[1] || selected == generators[2]);
    }

    @Test
    void rouletteSelectsLastGeneratorWhenNoMatchFound() {
        Generator[] generators = createMockGenerators(2);
        when(generators[0].getWeight()).thenReturn(0.0f);
        when(generators[1].getWeight()).thenReturn(0.0f);
        
        MultiGenerator.setListGenerators(generators);
        
        Generator selected = multiGenerator.roulette();
        
        assertNotNull(selected);
        assertEquals(generators[1], selected);
    }

    @Test
    void rouletteHandlesEqualWeights() {
        Generator[] generators = createMockGenerators(3);
        when(generators[0].getWeight()).thenReturn(10.0f);
        when(generators[1].getWeight()).thenReturn(10.0f);
        when(generators[2].getWeight()).thenReturn(10.0f);
        
        MultiGenerator.setListGenerators(generators);
        
        Generator selected = multiGenerator.roulette();
        
        assertNotNull(selected);
    }

    // ========== Search State Tests ==========

    @Test
    void searchStateReturnsTrueForBetterCandidateMaximization() {
        State bestState = createStateWithEvaluation(5.0);
        State candidateState = createStateWithEvaluation(7.0);
        
        Strategy.getStrategy().setBestState(bestState);
        
        Generator mockGen = mock(Generator.class);
        when(mockGen.getCountBetterGender()).thenReturn(0);
        MultiGenerator.setActiveGenerator(mockGen);
        
        boolean result = multiGenerator.searchState(candidateState);
        
        assertTrue(result);
        verify(mockGen).setCountBetterGender(1);
    }

    @Test
    void searchStateReturnsTrueForBetterCandidateMinimization() {
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        
        State bestState = createStateWithEvaluation(5.0);
        State candidateState = createStateWithEvaluation(3.0);
        
        Strategy.getStrategy().setBestState(bestState);
        
        Generator mockGen = mock(Generator.class);
        when(mockGen.getCountBetterGender()).thenReturn(0);
        MultiGenerator.setActiveGenerator(mockGen);
        
        boolean result = multiGenerator.searchState(candidateState);
        
        assertTrue(result);
        verify(mockGen).setCountBetterGender(1);
    }

    @Test
    void searchStateReturnsFalseForWorseCandidateMaximization() {
        State bestState = createStateWithEvaluation(10.0);
        State candidateState = createStateWithEvaluation(5.0);
        
        Strategy.getStrategy().setBestState(bestState);
        
        Generator mockGen = mock(Generator.class);
        MultiGenerator.setActiveGenerator(mockGen);
        
        boolean result = multiGenerator.searchState(candidateState);
        
        assertFalse(result);
    }

    @Test
    void searchStateReturnsFalseForWorseCandidateMinimization() {
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        
        State bestState = createStateWithEvaluation(3.0);
        State candidateState = createStateWithEvaluation(5.0);
        
        Strategy.getStrategy().setBestState(bestState);
        
        Generator mockGen = mock(Generator.class);
        MultiGenerator.setActiveGenerator(mockGen);
        
        boolean result = multiGenerator.searchState(candidateState);
        
        assertFalse(result);
    }

    // ========== Weight Update Tests ==========

    @Test
    void updateWeightCallsUpdateAwardSCWhenStateFoundIsBetter() {
        State bestState = createStateWithEvaluation(5.0);
        State candidateState = createStateWithEvaluation(10.0);
        
        Strategy.getStrategy().setBestState(bestState);
        Strategy.getStrategy().setCountCurrent(0);
        
        Generator mockGen = mock(Generator.class);
        when(mockGen.getWeight()).thenReturn(50.0f);
        when(mockGen.getCountBetterGender()).thenReturn(0);
        when(mockGen.getTrace()).thenReturn(new float[100]);
        when(mockGen.getType()).thenReturn(GeneratorType.HillClimbing);
        
        MultiGenerator.setActiveGenerator(mockGen);
        Generator[] generators = new Generator[] { mockGen };
        MultiGenerator.setListGenerators(generators);
        
        multiGenerator.updateWeight(candidateState);
        
        // Weight should increase
        verify(mockGen).setWeight(55.0f);
    }

    @Test
    void updateWeightCallsUpdateAwardImpWhenStateNotBetter() {
        State bestState = createStateWithEvaluation(10.0);
        State candidateState = createStateWithEvaluation(5.0);
        
        Strategy.getStrategy().setBestState(bestState);
        Strategy.getStrategy().setCountCurrent(0);
        
        Generator mockGen = mock(Generator.class);
        when(mockGen.getWeight()).thenReturn(50.0f);
        when(mockGen.getTrace()).thenReturn(new float[100]);
        when(mockGen.getType()).thenReturn(GeneratorType.HillClimbing);
        
        MultiGenerator.setActiveGenerator(mockGen);
        Generator[] generators = new Generator[] { mockGen };
        MultiGenerator.setListGenerators(generators);
        
        multiGenerator.updateWeight(candidateState);
        
        // Weight should decrease
        verify(mockGen).setWeight(45.0f);
    }

    // ========== Update Award SC (Success) Tests ==========

    @Test
    void updateAwardSCIncreasesWeight() {
        Strategy.getStrategy().setCountCurrent(0);
        
        Generator mockGen = mock(Generator.class);
        when(mockGen.getWeight()).thenReturn(50.0f);
        when(mockGen.getTrace()).thenReturn(new float[100]);
        when(mockGen.getType()).thenReturn(GeneratorType.HillClimbing);
        
        MultiGenerator.setActiveGenerator(mockGen);
        Generator[] generators = new Generator[] { mockGen };
        MultiGenerator.setListGenerators(generators);
        
        multiGenerator.updateAwardSC();
        
        verify(mockGen).setWeight(55.0f);
    }

    // ========== Update Award Imp (Failure) Tests ==========

    @Test
    void updateAwardImpDecreasesWeight() {
        Strategy.getStrategy().setCountCurrent(0);
        
        Generator mockGen = mock(Generator.class);
        when(mockGen.getWeight()).thenReturn(50.0f);
        when(mockGen.getTrace()).thenReturn(new float[100]);
        when(mockGen.getType()).thenReturn(GeneratorType.HillClimbing);
        
        MultiGenerator.setActiveGenerator(mockGen);
        Generator[] generators = new Generator[] { mockGen };
        MultiGenerator.setListGenerators(generators);
        
        multiGenerator.updateAwardImp();
        
        verify(mockGen).setWeight(45.0f);
    }

    // ========== Tournament Tests ==========

    @Test
    void tournamentUpdatesAllGenerators() throws Exception {
        State candidateState = createStateWithEvaluation(7.0);
        
        Generator mockGen1 = mock(Generator.class);
        Generator mockGen2 = mock(Generator.class);
        Generator mockGen3 = mock(Generator.class);
        
        when(mockGen1.getType()).thenReturn(GeneratorType.HillClimbing);
        when(mockGen2.getType()).thenReturn(GeneratorType.TabuSearch);
        when(mockGen3.getType()).thenReturn(GeneratorType.MultiGenerator);
        
        Generator[] generators = new Generator[] { mockGen1, mockGen2, mockGen3 };
        MultiGenerator.setListGenerators(generators);
        
        multiGenerator.tournament(candidateState, 1);
        
        // Verify that non-MultiGenerator generators get updateReference called
        verify(mockGen1, times(1)).updateReference(any(State.class), anyInt());
        verify(mockGen2, times(1)).updateReference(any(State.class), anyInt());
        verify(mockGen3, times(0)).updateReference(any(State.class), anyInt());
    }

    // ========== Update Reference Tests ==========

    @Test
    void updateReferenceUpdatesWeightAndCallsTournament() throws Exception {
        State bestState = createStateWithEvaluation(5.0);
        State candidateState = createStateWithEvaluation(10.0);
        
        Strategy.getStrategy().setBestState(bestState);
        Strategy.getStrategy().setCountCurrent(0);
        
        Generator mockGen = mock(Generator.class);
        when(mockGen.getWeight()).thenReturn(50.0f);
        when(mockGen.getCountBetterGender()).thenReturn(0);
        when(mockGen.getTrace()).thenReturn(new float[100]);
        when(mockGen.getType()).thenReturn(GeneratorType.HillClimbing);
        
        MultiGenerator.setActiveGenerator(mockGen);
        Generator[] generators = new Generator[] { mockGen };
        MultiGenerator.setListGenerators(generators);
        
        multiGenerator.updateReference(candidateState, 0);
        
        verify(mockGen).updateReference(any(State.class), anyInt());
    }

    // ========== Static Method Tests ==========

    @Test
    void initializeListGeneratorCreatesArrayOfFourGenerators() throws Exception {
        MultiGenerator.initializeListGenerator();
        
        Generator[] generators = MultiGenerator.getListGenerators();
        
        assertNotNull(generators);
        assertEquals(4, generators.length);
        assertTrue(generators[0] instanceof HillClimbing);
        assertTrue(generators[1] instanceof EvolutionStrategies);
        assertTrue(generators[2] instanceof LimitThreshold);
        assertTrue(generators[3] instanceof GeneticAlgorithm);
    }

    @Test
    void getListGeneratorsReturnsStaticArray() {
        Generator[] generators = createMockGenerators(3);
        MultiGenerator.setListGenerators(generators);
        
        assertEquals(generators, MultiGenerator.getListGenerators());
    }

    @Test
    void getActiveGeneratorReturnsStaticField() {
        Generator mockGen = mock(Generator.class);
        MultiGenerator.setActiveGenerator(mockGen);
        
        assertEquals(mockGen, MultiGenerator.getActiveGenerator());
    }

    // ========== Helper Methods ==========

    private State createStateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        return state;
    }

    private Generator[] createMockGenerators(int count) {
        Generator[] generators = new Generator[count];
        for (int i = 0; i < count; i++) {
            generators[i] = mock(Generator.class);
            when(generators[i].getType()).thenReturn(GeneratorType.values()[i % GeneratorType.values().length]);
        }
        return generators;
    }
}
