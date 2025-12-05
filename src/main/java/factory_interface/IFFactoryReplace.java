package factory_interface;

import java.lang.reflect.InvocationTargetException;

import evolutionary_algorithms.complement.Replace;
import evolutionary_algorithms.complement.ReplaceType;



/**
 * An interface for a factory that creates objects that implement the Replace interface.
 * These objects are responsible for the replacement strategy in a genetic algorithm.
 */
public interface IFFactoryReplace {
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
	Replace createReplace(ReplaceType typereplace)throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException ;
}
