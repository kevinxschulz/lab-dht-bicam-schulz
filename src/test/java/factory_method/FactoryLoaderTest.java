package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class FactoryLoaderTest {

    @Test
    public void testGetInstanceExistingClass() throws Exception {
        Object o = FactoryLoader.getInstance("java.util.ArrayList");
        assertNotNull(o);
        assertTrue(o instanceof ArrayList);
    }

    @Test
    public void testGetInstanceNonExistingClass() throws Exception {
        Object o = FactoryLoader.getInstance("no.such.ClassNameForTest");
        assertNull(o);
    }

}
