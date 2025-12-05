package factory_interface;

import java.lang.reflect.InvocationTargetException;

import metaheuristics.generators.Generator;
import metaheuristics.generators.GeneratorType;

/**
 * An interface for a factory that creates objects that implement the Generator interface.
 * These objects are responsible for generating solutions in a metaheuristic algorithm.
 */
public interface IFFactoryGenerator {
	
	/**
	 * Creates an instance of a class that implements the Generator interface.
	 *
	 * @param Generatortype The type of generator to use.
	 * @return An object that implements the Generator interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	Generator createGenerator(GeneratorType Generatortype)throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException ;
}
