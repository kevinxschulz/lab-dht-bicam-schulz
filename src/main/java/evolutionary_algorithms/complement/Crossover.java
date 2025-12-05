package evolutionary_algorithms.complement;


import problem.definition.State;

/**
 * Clase abstracta que representa el operador de cruce en un algoritmo evolutivo.
 * Define el método que deben implementar las clases de cruce concretas.
 */
public abstract class Crossover {
	
	/**
	 * Realiza el cruce entre dos padres para generar un nuevo estado (hijo).
	 * @param father1 El primer estado padre.
	 * @param father2 El segundo estado padre.
	 * @param PC La probabilidad de cruce.
	 * @return El estado hijo resultante del cruce.
	 */
	public abstract State crossover(State father1, State father2, double PC);
}
