package local_search.complement;

import factory_interface.IFFactoryGenerator;
import factory_method.FactoryGenerator;

import java.lang.reflect.InvocationTargetException;

import metaheurictics.strategy.Strategy;
import metaheuristics.generators.DistributionEstimationAlgorithm;
import metaheuristics.generators.EvolutionStrategies;
import metaheuristics.generators.GeneratorType;
import metaheuristics.generators.GeneticAlgorithm;
import metaheuristics.generators.ParticleSwarmOptimization;

/**
 * A class that provides a method to update the parameters of a metaheuristic and change the generator if necessary.
 */
public class UpdateParameter {
	
	private static IFFactoryGenerator ifFactoryGenerator;
	
	/**
	 * Updates the current iteration count and changes the generator if certain conditions are met.
	 *
	 * @param countIterationsCurrent The current number of iterations.
	 * @return The updated number of iterations.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Integer updateParameter(Integer countIterationsCurrent) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {//HashMap<String, Object> map, 
		countIterationsCurrent = countIterationsCurrent + 1;
		//		Here update parameter for update and change generator.
		if(countIterationsCurrent.equals(GeneticAlgorithm.countRef - 1)){
			ifFactoryGenerator = new FactoryGenerator();
			Strategy.getStrategy().generator = ifFactoryGenerator.createGenerator(GeneratorType.GeneticAlgorithm);
		}
		else{
			if(countIterationsCurrent.equals(EvolutionStrategies.countRef - 1)){
				ifFactoryGenerator = new FactoryGenerator();
				Strategy.getStrategy().generator = ifFactoryGenerator.createGenerator(GeneratorType.EvolutionStrategies);
			}			
			if(countIterationsCurrent.equals(DistributionEstimationAlgorithm.countRef - 1)){
				ifFactoryGenerator = new FactoryGenerator();
				Strategy.getStrategy().generator = ifFactoryGenerator.createGenerator(GeneratorType.DistributionEstimationAlgorithm);
			}
			if(countIterationsCurrent.equals(ParticleSwarmOptimization.countRef - 1)){
				ifFactoryGenerator = new FactoryGenerator();
				Strategy.getStrategy().generator = ifFactoryGenerator.createGenerator(GeneratorType.ParticleSwarmOptimization);
			}
		}
		return countIterationsCurrent;
	}
}
	


