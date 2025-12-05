package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import evolutionary_algorithms.complement.Distribution;
import evolutionary_algorithms.complement.DistributionType;

public class FactoryDistributionTest {

    @Test
    public void createUnivariate() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        FactoryDistribution factory = new FactoryDistribution();
        Distribution d = factory.createDistribution(DistributionType.Univariate);
        assertNotNull(d, "Factory should not return null");
        assertTrue(d instanceof Distribution, "Returned object should be a Distribution");
        assertEquals("Univariate", d.getClass().getSimpleName(), "Concrete class name should match enum name");
    }
}
