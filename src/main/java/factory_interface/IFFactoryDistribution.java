package factory_interface;

import java.lang.reflect.InvocationTargetException;

import evolutionary_algorithms.complement.Distribution;
import evolutionary_algorithms.complement.DistributionType;



/**
 * An interface for a factory that creates objects that implement the Distribution interface.
 * These objects are used in estimation of distribution algorithms.
 */
public interface IFFactoryDistribution {
	/**
	 * Creates an instance of a class that implements the Distribution interface.
	 *
	 * @param typedistribution The type of distribution to use.
	 * @return An object that implements the Distribution interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	Distribution createDistribution(DistributionType typedistribution) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
