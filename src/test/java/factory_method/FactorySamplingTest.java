package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import evolutionary_algorithms.complement.Sampling;
import evolutionary_algorithms.complement.SamplingType;

public class FactorySamplingTest {

    @Test
    public void testCreateSampling() throws Exception {
        FactorySampling factory = new FactorySampling();
        Sampling s = factory.createSampling(SamplingType.ProbabilisticSampling);
        assertNotNull(s);
        assertEquals("evolutionary_algorithms.complement.ProbabilisticSampling", s.getClass().getName());
    }
}
