package evolutionary_algorithms.complement;

import problem.definition.State;

/**
 * Clase abstracta que representa el operador de mutación en un algoritmo evolutivo.
 * Define el método que deben implementar las clases de mutación concretas.
 */
public abstract class Mutation {
	
	/**
	 * Realiza la mutación en un estado.
	 * @param state El estado a mutar.
	 * @param PM La probabilidad de mutación.
	 * @return El estado mutado.
	 */
	public abstract State mutation (State state, double PM);

}
