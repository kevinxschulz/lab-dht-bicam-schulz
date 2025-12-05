package factory_method;

import java.lang.reflect.InvocationTargetException;


import evolutionary_algorithms.complement.Mutation;
import evolutionary_algorithms.complement.MutationType;
import factory_interface.IFFactoryMutation;



/**
 * A factory for creating objects that implement the Mutation interface.
 * This class uses the Factory Method design pattern.
 */
public class FactoryMutation implements IFFactoryMutation {
	private Mutation mutation;

	/**
	 * Creates an instance of a class that implements the Mutation interface.
	 *
	 * @param typeMutation The type of mutation to use.
	 * @return An object that implements the Mutation interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Mutation createMutation(MutationType typeMutation) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		String className = "evolutionary_algorithms.complement." + typeMutation.toString();
		mutation = (Mutation) FactoryLoader.getInstance(className);
		return mutation;
	}
}
