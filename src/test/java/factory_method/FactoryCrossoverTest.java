package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import evolutionary_algorithms.complement.Crossover;
import evolutionary_algorithms.complement.CrossoverType;

public class FactoryCrossoverTest {

    @Test
    public void createOnePointCrossover() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        FactoryCrossover factory = new FactoryCrossover();
        Crossover c = factory.createCrossover(CrossoverType.OnePointCrossover);
        assertNotNull(c, "Factory should not return null");
        assertTrue(c instanceof Crossover, "Returned object should be a Crossover");
        assertEquals("OnePointCrossover", c.getClass().getSimpleName(), "Concrete class name should match enum name");
    }
}
