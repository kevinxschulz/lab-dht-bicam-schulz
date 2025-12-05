package factory_method;

import java.lang.reflect.InvocationTargetException;


import evolutionary_algorithms.complement.FatherSelection;
import evolutionary_algorithms.complement.SelectionType;
import factory_interface.IFFactoryFatherSelection;



/**
 * A factory for creating objects that implement the FatherSelection interface.
 * This class uses the Factory Method design pattern.
 */
public class FactoryFatherSelection implements IFFactoryFatherSelection{
    private FatherSelection selection;
	
    /**
     * Creates an instance of a class that implements the FatherSelection interface.
     *
     * @param selectionType The type of selection to use.
     * @return An object that implements the FatherSelection interface.
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public FatherSelection createSelectFather(SelectionType selectionType) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	String className = "evolutionary_algorithms.complement." + selectionType.toString();
		selection = (FatherSelection) FactoryLoader.getInstance(className);
		return selection;
	}
}
