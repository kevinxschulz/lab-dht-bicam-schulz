package metaheuristics.generators;

import factory_method.FactoryGenerator;


/**
 * Helper that creates and injects a GeneticAlgorithm instance into the
 * generator array. Designed to run in a separate thread during initialization.
 */
public class InstanceGA implements Runnable {

	private boolean terminate = false;
	
	public void run() {
		FactoryGenerator ifFactoryGenerator = new FactoryGenerator();
		Generator generatorGA = null;
		try {
			generatorGA = ifFactoryGenerator.createGenerator(GeneratorType.GeneticAlgorithm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean find = false;
		int i = 0;
		while (find == false) {
			if(MultiGenerator.getListGenerators()[i].getType().equals(GeneratorType.GeneticAlgorithm)){
				MultiGenerator.getListGenerators()[i] = generatorGA;
				find = true;
			}
			else i++;
		}
		terminate = true;
	}

	public boolean isTerminate() {
		return terminate;
	}

	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}

}
