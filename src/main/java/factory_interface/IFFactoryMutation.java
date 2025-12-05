package factory_interface;

import java.lang.reflect.InvocationTargetException;

import evolutionary_algorithms.complement.Mutation;
import evolutionary_algorithms.complement.MutationType;



/**
 * An interface for a factory that creates objects that implement the Mutation interface.
 * These objects are responsible for performing the mutation operation in a genetic algorithm.
 */
public interface IFFactoryMutation {
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
	Mutation createMutation(MutationType typeMutation)throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException ;
}
