package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import evolutionary_algorithms.complement.Mutation;
import evolutionary_algorithms.complement.MutationType;

public class FactoryMutationTest {

    @Test
    public void testCreateMutation() throws Exception {
        FactoryMutation factory = new FactoryMutation();
        Mutation m = factory.createMutation(MutationType.TowPointsMutation);
        assertNotNull(m);
        assertEquals("evolutionary_algorithms.complement.TowPointsMutation", m.getClass().getName());
    }
}
