package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import problem.extension.SolutionMethod;
import problem.extension.TypeSolutionMethod;

public class FactorySolutionMethodTest {

    @Test
    public void testCreateSolutionMethod() throws Exception {
        FactorySolutionMethod factory = new FactorySolutionMethod();
        SolutionMethod sm = factory.createdSolutionMethod(TypeSolutionMethod.FactoresPonderados);
        assertNotNull(sm);
        assertEquals("problem.extension.FactoresPonderados", sm.getClass().getName());
    }
}
