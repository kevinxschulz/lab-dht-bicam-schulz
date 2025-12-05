package evolutionary_algorithms.complement;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import problem.definition.State;

/**
 * Clase abstracta que representa el operador de reemplazo de individuos en un algoritmo evolutivo.
 * Define el método que deben implementar las clases de reemplazo concretas.
 */
public abstract class Replace {

	/**
	 * Realiza el reemplazo de individuos en la población.
	 * @param stateCandidate El estado candidato a ser considerado para el reemplazo.
	 * @param listState La lista de estados de la población actual.
	 * @return La nueva lista de estados después de aplicar el reemplazo.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public abstract List<State> replace(State stateCandidate, List<State>listState) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
