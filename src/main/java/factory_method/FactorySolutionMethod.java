package factory_method;

import java.lang.reflect.InvocationTargetException;

import problem.extension.SolutionMethod;
import problem.extension.TypeSolutionMethod;
import factory_interface.IFFactorySolutionMethod;

/**
 * A factory for creating objects that implement the SolutionMethod interface.
 * This class uses the Factory Method design pattern.
 */
public class FactorySolutionMethod implements IFFactorySolutionMethod {

	private SolutionMethod solutionMethod;
	
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
	@Override
	public SolutionMethod createdSolutionMethod(TypeSolutionMethod method) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String className = "problem.extension." + method.toString();
		solutionMethod = (SolutionMethod) FactoryLoader.getInstance(className);
		return solutionMethod;
	}

}
