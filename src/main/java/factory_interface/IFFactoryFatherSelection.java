package factory_interface;

import java.lang.reflect.InvocationTargetException;

import evolutionary_algorithms.complement.FatherSelection;
import evolutionary_algorithms.complement.SelectionType;



/**
 * An interface for a factory that creates objects that implement the FatherSelection interface.
 * These objects are responsible for selecting parent solutions for reproduction in a genetic algorithm.
 */
public interface IFFactoryFatherSelection {
	
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
	FatherSelection createSelectFather(SelectionType selectionType)throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException ;
}
