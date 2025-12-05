package evolutionary_algorithms.complement;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import problem.definition.State;

/**
 * Clase que implementa la estrategia de reemplazo generacional.
 * En esta estrategia, toda la población es reemplazada por la nueva generación de hijos.
 */
public class GenerationalReplace extends Replace {

	/**
	 * Realiza el reemplazo generacional.
	 * Reemplaza un estado de la lista con el estado candidato.
	 * @param stateCandidate El estado candidato a ser insertado en la población.
	 * @param listState La lista de estados de la población actual.
	 * @return La nueva lista de estados después del reemplazo.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Override
	public List<State> replace(State stateCandidate, List<State> listState) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		listState.remove(0);
		listState.add(stateCandidate);
		/*List<State> sonList = Strategy.getStrategy().generator.getSonList();
		for (int i = 0; i < listState.size(); i++) {
			listState.set(i, sonList.get(i));
		}*/
		return listState;
	}
}
