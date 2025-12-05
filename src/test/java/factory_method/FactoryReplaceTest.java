package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import evolutionary_algorithms.complement.Replace;
import evolutionary_algorithms.complement.ReplaceType;

public class FactoryReplaceTest {

    @Test
    public void testCreateReplace() throws Exception {
        FactoryReplace factory = new FactoryReplace();
        Replace r = factory.createReplace(ReplaceType.SteadyStateReplace);
        assertNotNull(r);
        assertEquals("evolutionary_algorithms.complement.SteadyStateReplace", r.getClass().getName());
    }
}
