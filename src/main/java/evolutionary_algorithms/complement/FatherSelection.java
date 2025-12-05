package evolutionary_algorithms.complement;

import java.util.List;

import problem.definition.State;

/**
 * Clase abstracta que representa el operador de selección de padres en un algoritmo evolutivo.
 * Define el método que deben implementar las clases de selección de padres concretas.
 */
public abstract class FatherSelection {
	
	/**
	 * Selecciona un conjunto de padres de una lista de estados.
	 * @param listState La lista de estados de la población actual.
	 * @param truncation El número de padres a seleccionar.
	 * @return Una lista de estados que representan los padres seleccionados.
	 */
	public abstract List<State> selection(List<State> listState, int truncation);

}
