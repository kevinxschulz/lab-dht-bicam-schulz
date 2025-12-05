package factory_method;

import java.lang.reflect.InvocationTargetException;


import evolutionary_algorithms.complement.Crossover;
import evolutionary_algorithms.complement.CrossoverType;
import factory_interface.IFFactoryCrossover;



/**
 * A factory for creating objects that implement the Crossover interface.
 * This class uses the Factory Method design pattern.
 */
public class FactoryCrossover implements IFFactoryCrossover {
	private Crossover crossing;

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
	public Crossover createCrossover(CrossoverType Crossovertype) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		String className = "evolutionary_algorithms.complement." + Crossovertype.toString();
		crossing = (Crossover) FactoryLoader.getInstance(className);
		return crossing;
	}
}
