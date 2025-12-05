package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import evolutionary_algorithms.complement.FatherSelection;
import evolutionary_algorithms.complement.SelectionType;

public class FactoryFatherSelectionTest {

    @Test
    public void createRouletteSelection() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        FactoryFatherSelection factory = new FactoryFatherSelection();
        FatherSelection fs = factory.createSelectFather(SelectionType.RouletteSelection);
        assertNotNull(fs, "Factory should not return null");
        assertTrue(fs instanceof FatherSelection, "Returned object should be a FatherSelection");
        assertEquals("RouletteSelection", fs.getClass().getSimpleName(), "Concrete class name should match enum name");
    }
}
