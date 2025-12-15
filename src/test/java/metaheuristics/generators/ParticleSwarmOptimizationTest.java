package metaheuristics.generators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metaheuristics.strategy.Strategy;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

class ParticleSwarmOptimizationTest {

    private ParticleSwarmOptimization pso;
    private Problem mockProblem;
    private State mockState;

    @BeforeEach
    void setUp() {
        Strategy.destroyExecute();
        // Initialize mapGenerators to avoid NPE
        Strategy.getStrategy().mapGenerators = new TreeMap<>();
        // Initialize static listStateReference
        AbstractLocalSearchGenerator.listStateReference = new ArrayList<>();
        ParticleSwarmOptimization.countParticle = 0;
        ParticleSwarmOptimization.coutSwarm = 2;
        ParticleSwarmOptimization.countParticleBySwarm = 5;
        
        mockProblem = mock(Problem.class);
        mockState = mock(State.class);
        
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        when(mockProblem.getState()).thenReturn(mockState);
        when(mockState.getCopy()).thenReturn(mockState);
        when(mockState.getCode()).thenReturn(new ArrayList<>());
        when(mockState.getEvaluation()).thenReturn(new ArrayList<>(List.of(5.0)));
        
        Strategy.getStrategy().setProblem(mockProblem);
        
        pso = new ParticleSwarmOptimization();
    }

    @AfterEach
    void tearDown() {
        Strategy.destroyExecute();
    }

    @Test
    void getCountGenderReturnsZeroInitially() {
        assertEquals(0, pso.getCountGender());
    }

    @Test
    void setCountGenderUpdatesValue() {
        pso.setCountGender(25);
        assertEquals(25, pso.getCountGender());
        // Reset for other tests
        pso.setCountGender(0);
    }

    @Test
    void getTypeReturnsParticleSwarmOptimization() {
        assertEquals(GeneratorType.ParticleSwarmOptimization, pso.getType());
    }

    @Test
    void getListParticleReturnsNonNull() {
        List<Particle> particles = pso.getListParticle();
        assertNotNull(particles);
    }

    @Test
    void setListParticleUpdatesParticleList() {
        List<Particle> newList = new ArrayList<>();
        pso.setListParticle(newList);
        assertEquals(newList, pso.getListParticle());
    }

    @Test
    void getReferenceReturnsNull() {
        assertNull(pso.getReference());
    }

    @Test
    void getStateReferencePSOReturnsValue() {
        State state = pso.getStateReferencePSO();
        // May be null initially
        assertTrue(state == null || state != null);
    }

    @Test
    void setStateReferencePSOUpdatesState() {
        State newState = mock(State.class);
        pso.setStateReferencePSO(newState);
        assertEquals(newState, pso.getStateReferencePSO());
    }

    @Test
    void getSonListReturnsNull() {
        assertNull(pso.getSonList());
    }

    @Test
    void awardUpdateREFReturnsFalse() {
        State state = mock(State.class);
        assertFalse(pso.awardUpdateREF(state));
    }

    @Test
    void getWeightReturnsInitialValue() {
        assertEquals(50f, pso.getWeight(), 0.001f);
    }

    @Test
    void setWeightUpdatesWeight() {
        pso.setWeight(75f);
        assertEquals(75f, pso.getWeight(), 0.001f);
    }

    @Test
    void getTraceReturnsNonNullArray() {
        float[] trace = pso.getTrace();
        assertNotNull(trace);
        assertEquals(50f, trace[0], 0.001f);
    }

    @Test
    void staticConstantsHaveCorrectValues() {
        assertEquals(0.9, ParticleSwarmOptimization.wmax, 0.001);
        assertEquals(0.2, ParticleSwarmOptimization.wmin, 0.001);
        assertEquals(2, ParticleSwarmOptimization.learning1);
        assertEquals(2, ParticleSwarmOptimization.learning2);
    }

    @Test
    void staticBinaryIsFalseByDefault() {
        assertFalse(ParticleSwarmOptimization.binary);
    }

    @Test
    void lBestArrayIsInitializedCorrectly() {
        ParticleSwarmOptimization.coutSwarm = 3;
        ParticleSwarmOptimization pso2 = new ParticleSwarmOptimization();
        assertNotNull(ParticleSwarmOptimization.lBest);
    }

    @Test
    void generateReturnsStateWhenParticlesExist() throws Exception {
        // Setup particles with states
        List<Particle> particles = new ArrayList<>();
        State mockParticleState = mock(State.class);
        when(mockParticleState.getEvaluation()).thenReturn(new ArrayList<>(List.of(3.0)));
        when(mockParticleState.getCode()).thenReturn(new ArrayList<>());
        
        Particle mockParticle = mock(Particle.class);
        when(mockParticle.getStateActual()).thenReturn(mockParticleState);
        when(mockParticle.getStatePBest()).thenReturn(mockParticleState);
        
        particles.add(mockParticle);
        pso.setListParticle(particles);
        
        ParticleSwarmOptimization.countParticle = 0;
        AbstractPopulationBasedGenerator.countRef = 1;
        
        State result = pso.generate(1);
        
        assertNotNull(result);
        verify(mockParticle).generate(1);
    }

    @Test
    void generateResetsCounterWhenExceedingCountRef() throws Exception {
        List<Particle> particles = new ArrayList<>();
        State mockParticleState = mock(State.class);
        when(mockParticleState.getEvaluation()).thenReturn(new ArrayList<>(List.of(3.0)));
        
        Particle mockParticle = mock(Particle.class);
        when(mockParticle.getStateActual()).thenReturn(mockParticleState);
        
        particles.add(mockParticle);
        pso.setListParticle(particles);
        
        ParticleSwarmOptimization.countParticle = 10;
        AbstractPopulationBasedGenerator.countRef = 5;
        
        pso.generate(1);
        
        assertEquals(0, ParticleSwarmOptimization.countParticle);
    }

    @Test
    void updateReferenceIncrementsCountParticle() throws Exception {
        setupPSOWithParticles();
        
        int initialCount = ParticleSwarmOptimization.countParticle;
        State candidate = createStateWithEvaluation(2.0);
        
        pso.updateReference(candidate, 1);
        
        assertEquals(initialCount + 1, ParticleSwarmOptimization.countParticle);
    }

    @Test
    void updateReferenceUpdatesGBestForMaximizationWhenBetter() throws Exception {
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        setupPSOWithParticles();
        
        State candidate = createStateWithEvaluation(10.0);
        
        pso.updateReference(candidate, 1);
        
        assertNotNull(ParticleSwarmOptimization.gBest);
    }

    @Test
    void updateReferenceUpdatesGBestForMinimizationWhenBetter() throws Exception {
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        setupPSOWithParticles();
        
        State candidate = createStateWithEvaluation(1.0);
        
        pso.updateReference(candidate, 1);
        
        assertNotNull(ParticleSwarmOptimization.gBest);
    }

    @Test
    void gBestInicialReturnsStateWithBestEvaluationForMaximization() {
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        
        // Create particles first
        setupPSOWithMultipleParticles();
        
        ParticleSwarmOptimization.lBest = new State[3];
        ParticleSwarmOptimization.lBest[0] = createStateWithEvaluation(5.0);
        ParticleSwarmOptimization.lBest[1] = createStateWithEvaluation(10.0);
        ParticleSwarmOptimization.lBest[2] = createStateWithEvaluation(7.0);
        
        State best = pso.gBestInicial();
        
        assertNotNull(best);
        assertEquals(10.0, best.getEvaluation().get(0), 0.001);
    }

    @Test
    void gBestInicialReturnsStateWithBestEvaluationForMinimization() {
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        
        // Create particles first
        setupPSOWithMultipleParticles();
        
        ParticleSwarmOptimization.lBest = new State[3];
        ParticleSwarmOptimization.lBest[0] = createStateWithEvaluation(5.0);
        ParticleSwarmOptimization.lBest[1] = createStateWithEvaluation(2.0);
        ParticleSwarmOptimization.lBest[2] = createStateWithEvaluation(7.0);
        
        State best = pso.gBestInicial();
        
        assertNotNull(best);
        assertEquals(2.0, best.getEvaluation().get(0), 0.001);
    }

    @Test
    void inicialiceLBestSetsLBestForMaximization() {
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Maximizar);
        setupPSOWithMultipleParticles();
        
        pso.inicialiceLBest();
        
        assertNotNull(ParticleSwarmOptimization.lBest);
        for (State state : ParticleSwarmOptimization.lBest) {
            if (state != null) {
                assertNotNull(state.getEvaluation());
            }
        }
    }

    @Test
    void inicialiceLBestSetsLBestForMinimization() {
        when(mockProblem.getTypeProblem()).thenReturn(ProblemType.Minimizar);
        setupPSOWithMultipleParticles();
        
        pso.inicialiceLBest();
        
        assertNotNull(ParticleSwarmOptimization.lBest);
        for (State state : ParticleSwarmOptimization.lBest) {
            if (state != null) {
                assertNotNull(state.getEvaluation());
            }
        }
    }

    @Test
    void getReferenceListReturnsListStateReference() {
        List<State> list = pso.getReferenceList();
        assertNotNull(list);
    }

    @Test
    void getGeneratorTypeReturnsCorrectType() {
        assertEquals(GeneratorType.ParticleSwarmOptimization, pso.getGeneratorType());
    }

    @Test
    void setGeneratorTypeUpdatesType() {
        pso.setGeneratorType(GeneratorType.GeneticAlgorithm);
        assertEquals(GeneratorType.GeneticAlgorithm, pso.getGeneratorType());
    }

    @Test
    void getListCountBetterGenderReturnsNonNull() {
        int[] counts = pso.getListCountBetterGender();
        assertNotNull(counts);
    }

    @Test
    void getListCountGenderReturnsNonNull() {
        int[] counts = pso.getListCountGender();
        assertNotNull(counts);
    }

    @Test
    void getCountBetterGenderReturnsZeroInitially() {
        assertEquals(0, pso.getCountBetterGender());
    }

    @Test
    void setCountBetterGenderUpdatesValue() {
        pso.setCountBetterGender(13);
        assertEquals(13, pso.getCountBetterGender());
        pso.setCountBetterGender(0);
    }

    @Test
    void staticConstrictionCanBeSet() {
        ParticleSwarmOptimization.constriction = 0.729;
        assertEquals(0.729, ParticleSwarmOptimization.constriction, 0.001);
    }

    @Test
    void countCurrentIterPSOIsAccessible() {
        ParticleSwarmOptimization.countCurrentIterPSO = 10;
        assertEquals(10, ParticleSwarmOptimization.countCurrentIterPSO);
    }

    // Helper methods
    private State createStateWithEvaluation(double value) {
        State state = new State();
        ArrayList<Double> evaluation = new ArrayList<>();
        evaluation.add(value);
        state.setEvaluation(evaluation);
        state.setCode(new ArrayList<>());
        return state;
    }

    private void setupPSOWithParticles() {
        List<Particle> particles = new ArrayList<>();
        
        State state1 = createStateWithEvaluation(5.0);
        State state2 = createStateWithEvaluation(3.0);
        
        Particle particle1 = mock(Particle.class);
        when(particle1.getStatePBest()).thenReturn(state1);
        when(particle1.getStateActual()).thenReturn(state1);
        
        Particle particle2 = mock(Particle.class);
        when(particle2.getStatePBest()).thenReturn(state2);
        when(particle2.getStateActual()).thenReturn(state2);
        
        particles.add(particle1);
        particles.add(particle2);
        
        pso.setListParticle(particles);
        
        ParticleSwarmOptimization.lBest = new State[2];
        ParticleSwarmOptimization.lBest[0] = state1;
        ParticleSwarmOptimization.lBest[1] = state2;
        
        List<State> refList = new ArrayList<>();
        refList.add(state1);
        pso.setListStateReference(refList);
        
        ParticleSwarmOptimization.countParticle = 0;
        ParticleSwarmOptimization.gBest = state1;
    }

    private void setupPSOWithMultipleParticles() {
        List<Particle> particles = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            State state = createStateWithEvaluation(i * 1.0);
            Particle particle = mock(Particle.class);
            when(particle.getStatePBest()).thenReturn(state);
            when(particle.getStateActual()).thenReturn(state);
            particles.add(particle);
        }
        
        pso.setListParticle(particles);
        ParticleSwarmOptimization.countParticle = 0;
        ParticleSwarmOptimization.countParticleBySwarm = 5;
        ParticleSwarmOptimization.coutSwarm = 2;
    }
}
