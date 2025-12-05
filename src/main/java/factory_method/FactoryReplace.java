package factory_method;

import java.lang.reflect.InvocationTargetException;


import evolutionary_algorithms.complement.Replace;
import evolutionary_algorithms.complement.ReplaceType;
import factory_interface.IFFactoryReplace;



/**
 * A factory for creating objects that implement the Replace interface.
 * This class uses the Factory Method design pattern.
 */
public class FactoryReplace implements IFFactoryReplace {

private Replace replace;
	
	/**
	 * Creates an instance of a class that implements the Replace interface.
	 *
	 * @param typereplace The type of replacement strategy to use.
	 * @return An object that implements the Replace interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Replace createReplace( ReplaceType typereplace ) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		String className = "evolutionary_algorithms.complement." + typereplace.toString();
		replace = (Replace) FactoryLoader.getInstance(className);
		return replace;
	}
}
