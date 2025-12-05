package factory_interface;

import java.lang.reflect.InvocationTargetException;

import evolutionary_algorithms.complement.Crossover;
import evolutionary_algorithms.complement.CrossoverType;



/**
 * An interface for a factory that creates objects that implement the Crossover interface.
 * These objects are responsible for performing the crossover operation in a genetic algorithm.
 */
public interface IFFactoryCrossover {
	/**
	 * Creates an instance of a class that implements the Crossover interface.
	 *
	 * @param Crossovertype The type of crossover to use.
	 * @return An object that implements the Crossover interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	Crossover createCrossover(CrossoverType Crossovertype)throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException ;
}
