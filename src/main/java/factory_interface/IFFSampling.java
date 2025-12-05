package factory_interface;

import java.lang.reflect.InvocationTargetException;

import evolutionary_algorithms.complement.Sampling;
import evolutionary_algorithms.complement.SamplingType;



/**
 * An interface for a factory that creates objects that implement the Sampling interface.
 * These objects are responsible for sampling new solutions in an estimation of distribution algorithm.
 */
public interface IFFSampling {
	/**
	 * Creates an instance of a class that implements the Sampling interface.
	 *
	 * @param typesampling The type of sampling to use.
	 * @return An object that implements the Sampling interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	Sampling createSampling(SamplingType typesampling) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
