package factory_interface;

import java.lang.reflect.InvocationTargetException;

import problem.extension.SolutionMethod;
import problem.extension.TypeSolutionMethod;

/**
 * An interface for a factory that creates objects that implement the SolutionMethod interface.
 * These objects represent a method for solving a multi-objective optimization problem.
 */
public interface IFFactorySolutionMethod {
	
	/**
	 * Creates an instance of a class that implements the SolutionMethod interface.
	 *
	 * @param method The type of solution method to use.
	 * @return An object that implements the SolutionMethod interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	SolutionMethod createdSolutionMethod(TypeSolutionMethod  method) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException ;

}
