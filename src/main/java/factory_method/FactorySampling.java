package factory_method;

import java.lang.reflect.InvocationTargetException;


import evolutionary_algorithms.complement.Sampling;
import evolutionary_algorithms.complement.SamplingType;
import factory_interface.IFFSampling;



/**
 * A factory for creating objects that implement the Sampling interface.
 * This class uses the Factory Method design pattern.
 */
public class FactorySampling implements IFFSampling {
    private Sampling sampling;
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
	public Sampling createSampling(SamplingType typesampling) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		String className = "evolutionary_algorithms.complement." + typesampling.toString();
		sampling = (Sampling) FactoryLoader.getInstance(className);
		return sampling;
	}
}
