package evolutionary_algorithms.complement;

import java.util.List;

import problem.definition.State;

/**
 * Clase abstracta que representa el operador de muestreo en algoritmos de estimación de distribución (EDAs).
 * Define el método que deben implementar las clases de muestreo concretas.
 */
public abstract class Sampling {
	/**
	 * Realiza el muestreo para generar una lista de nuevos individuos.
	 * @param fathers La lista de estados padre a partir de la cual se realizará el muestreo.
	 * @param countInd El número de individuos a generar.
	 * @return Una lista de nuevos estados generados mediante muestreo.
	 */
	public abstract List<State> sampling (List<State> fathers, int countInd);
}
