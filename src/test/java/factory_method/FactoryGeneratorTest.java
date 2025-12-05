package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import metaheuristics.generators.Generator;
import metaheuristics.generators.GeneratorType;

public class FactoryGeneratorTest {

    @Test
    public void testCreateGenerator() throws Exception {
        FactoryGenerator factory = new FactoryGenerator();
        Generator g = factory.createGenerator(GeneratorType.RandomSearch);
        assertNotNull(g);
        assertEquals("metaheuristics.generators.RandomSearch", g.getClass().getName());
    }
}
