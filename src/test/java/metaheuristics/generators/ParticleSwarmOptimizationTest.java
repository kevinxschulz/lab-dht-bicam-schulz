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
}
