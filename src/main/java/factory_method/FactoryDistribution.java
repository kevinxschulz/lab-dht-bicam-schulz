package factory_method;

import java.lang.reflect.InvocationTargetException;


import evolutionary_algorithms.complement.Distribution;
import evolutionary_algorithms.complement.DistributionType;
import factory_interface.IFFactoryDistribution;



/**
 * A factory for creating objects that implement the Distribution interface.
 * This class uses the Factory Method design pattern.
 */
public class FactoryDistribution implements IFFactoryDistribution {
	private Distribution distribution;

	/**
	 * Creates an instance of a class that implements the Distribution interface.
	 *
	 * @param distributiontype The type of distribution to use.
	 * @return An object that implements the Distribution interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Distribution createDistribution(DistributionType distributiontype) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		String className = "evolutionary_algorithms.complement." + distributiontype.toString();
		distribution = (Distribution) FactoryLoader.getInstance(className);
		return distribution;
	}
}
