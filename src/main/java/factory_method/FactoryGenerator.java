package factory_method;


import java.lang.reflect.InvocationTargetException;

import factory_interface.IFFactoryGenerator;

import metaheuristics.generators.Generator;
import metaheuristics.generators.GeneratorType;


/**
 * A factory for creating objects that implement the Generator interface.
 * This class uses the Factory Method design pattern.
 */
public class FactoryGenerator implements IFFactoryGenerator {

	private Generator generator;
	
	/**
	 * Creates an instance of a class that implements the Generator interface.
	 *
	 * @param generatorType The type of generator to use.
	 * @return An object that implements the Generator interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Generator createGenerator(GeneratorType generatorType) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String className = "metaheuristics.generators." + generatorType.toString();
		generator = (Generator) FactoryLoader.getInstance(className);
		return generator;
	}
}
