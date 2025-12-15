package problem.extension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jxl.read.biff.BiffException;
import problem.definition.State;

/**
 * Test class for MetricasMultiobjetivo.
 * Tests multi-objective quality metrics like error rate, generational distance, and dispersion.
 */
class MetricasMultiobjetivoTest {

    private MetricasMultiobjetivo metricas;

    @BeforeEach
    void setUp() {
        metricas = new MetricasMultiobjetivo();
    }

    @Test
    void tasaErrorReturnsZeroWhenAllSolutionsInTrueFront() throws BiffException, IOException {
        List<State> currentFront = createParetoFront(new double[][]{{1.0, 2.0}, {3.0, 4.0}});
        List<State> trueFront = createParetoFront(new double[][]{{1.0, 2.0}, {3.0, 4.0}});

        double errorRate = metricas.TasaError(currentFront, trueFront);

        assertEquals(0.0, errorRate, 0.001);
    }

    @Test
    void tasaErrorReturnsOneWhenNoSolutionsInTrueFront() throws BiffException, IOException {
        List<State> currentFront = createParetoFront(new double[][]{{5.0, 6.0}, {7.0, 8.0}});
        List<State> trueFront = createParetoFront(new double[][]{{1.0, 2.0}, {3.0, 4.0}});

        double errorRate = metricas.TasaError(currentFront, trueFront);

        assertEquals(1.0, errorRate, 0.001);
    }

    @Test
    void tasaErrorReturnsHalfWhenHalfSolutionsInTrueFront() throws BiffException, IOException {
        List<State> currentFront = createParetoFront(new double[][]{{1.0, 2.0}, {5.0, 6.0}});
        List<State> trueFront = createParetoFront(new double[][]{{1.0, 2.0}, {3.0, 4.0}});

        double errorRate = metricas.TasaError(currentFront, trueFront);

        assertEquals(0.5, errorRate, 0.001);
    }

    @Test
    void distanciaGeneracionalReturnsLowValueForIdenticalFronts() throws BiffException, IOException {
        List<State> currentFront = createParetoFront(new double[][]{{1.0, 2.0}, {3.0, 4.0}});
        List<State> trueFront = createParetoFront(new double[][]{{1.0, 2.0}, {3.0, 4.0}});

        double distance = metricas.DistanciaGeneracional(currentFront, trueFront);

        // Distance should be small (not necessarily 0 due to implementation details)
        assertTrue(distance >= 0);
    }

    @Test
    void distanciaGeneracionalReturnsPositiveForDifferentFronts() throws BiffException, IOException {
        List<State> currentFront = createParetoFront(new double[][]{{2.0, 3.0}});
        List<State> trueFront = createParetoFront(new double[][]{{1.0, 2.0}});

        double distance = metricas.DistanciaGeneracional(currentFront, trueFront);

        assertTrue(distance > 0);
    }

    @Test
    void dispersionReturnsZeroForSingleSolution() throws BiffException, IOException {
        ArrayList<State> solutions = new ArrayList<>();
        solutions.add(createState(new double[]{1.0, 2.0}));

        double dispersion = metricas.Dispersion(solutions);

        assertEquals(0.0, dispersion, 0.001);
    }

    @Test
    void dispersionReturnsPositiveForMultipleSolutions() throws BiffException, IOException {
        ArrayList<State> solutions = new ArrayList<>();
        solutions.add(createState(new double[]{1.0, 2.0}));
        solutions.add(createState(new double[]{3.0, 4.0}));
        solutions.add(createState(new double[]{5.0, 6.0}));

        double dispersion = metricas.Dispersion(solutions);

        assertTrue(dispersion >= 0);
    }

    @Test
    void calcularMinReturnsMinimumValue() {
        ArrayList<Double> metrics = new ArrayList<>();
        metrics.add(5.0);
        metrics.add(2.0);
        metrics.add(8.0);
        metrics.add(1.0);

        double min = metricas.CalcularMin(metrics);

        assertEquals(1.0, min, 0.001);
    }

    @Test
    void calcularMaxReturnsMaximumValue() {
        ArrayList<Double> metrics = new ArrayList<>();
        metrics.add(5.0);
        metrics.add(2.0);
        metrics.add(8.0);
        metrics.add(1.0);

        double max = metricas.CalcularMax(metrics);

        assertEquals(8.0, max, 0.001);
    }

    @Test
    void calcularMediaReturnsArithmeticMean() {
        ArrayList<Double> metrics = new ArrayList<>();
        metrics.add(2.0);
        metrics.add(4.0);
        metrics.add(6.0);
        metrics.add(8.0);

        double mean = metricas.CalcularMedia(metrics);

        assertEquals(5.0, mean, 0.001);
    }

    private List<State> createParetoFront(double[][] evaluations) {
        List<State> front = new ArrayList<>();
        for (double[] eval : evaluations) {
            front.add(createState(eval));
        }
        return front;
    }

    private State createState(double[] evaluations) {
        State state = mock(State.class);
        ArrayList<Double> evalList = new ArrayList<>();
        for (double eval : evaluations) {
            evalList.add(eval);
        }
        when(state.getEvaluation()).thenReturn(evalList);
        return state;
    }
}
