package evolutionary_algorithms.complement;

import java.util.List;

import problem.definition.State;

/**
 * Clase abstracta que representa la distribución de probabilidad en un algoritmo de estimación de distribución (EDA).
 * Define el método que deben implementar las clases de distribución concretas.
 */
public abstract class Distribution {
	/**
	 * Calcula la distribución de probabilidad a partir de una lista de padres.
	 * @param fathers La lista de estados padre.
	 * @return Una lista de objetos de probabilidad que representan la distribución.
	 */
	public abstract List<Probability> distribution(List<State> fathers);

}
