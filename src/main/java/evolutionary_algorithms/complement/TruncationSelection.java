package evolutionary_algorithms.complement;


import java.util.ArrayList;
import java.util.List;

import metaheuristics.strategy.Strategy;

import problem.definition.State;
import problem.definition.Problem.ProblemType;

/**
 * Clase que implementa la selección por truncamiento.
 * En este método, los individuos con las mejores aptitudes son seleccionados directamente como padres.
 */
public class TruncationSelection extends FatherSelection {
	
	/**
	 * Ordena la lista de estados de forma descendente según su valor de evaluación.
	 * Se utiliza para problemas de maximización.
	 * @param listState La lista de estados a ordenar.
	 * @return La lista de estados ordenada.
	 */
	public List<State> OrderBetter (List<State> listState){
		State var = null;
		for (int i = 0; i < listState.size()- 1; i++) {
			for (int j = i+1; j < listState.size(); j++) {
				if(listState.get(i).getEvaluation().get(0) < listState.get(j).getEvaluation().get(0)){
					var = listState.get(i);
					listState.set(i, listState.get(j));
					listState.set(j,var);
				}
			}
		}
		return listState;
	}
	
	/**
	 * Ordena la lista de estados de forma ascendente según su valor de evaluación.
	 * Se utiliza para problemas de minimización.
	 * @param listState La lista de estados a ordenar.
	 * @return La lista de estados ordenada.
	 */
	public List<State> ascOrderBetter (List<State> listState){
		State var = null;
		for (int i = 0; i < listState.size()- 1; i++) {
			for (int j = i+1; j < listState.size(); j++) {
				if(listState.get(i).getEvaluation().get(0) > listState.get(j).getEvaluation().get(0)){
					var = listState.get(i);
					listState.set(i, listState.get(j));
					listState.set(j,var);
				}
			}
		}
		return listState;
	}
    
	/**
	 * Realiza la selección por truncamiento.
	 * Selecciona los mejores individuos de la población según el tipo de problema (maximización o minimización).
	 * @param listState La lista de estados de la población actual.
	 * @param truncation El número de individuos a seleccionar como padres.
	 * @return Una lista de estados que representan los padres seleccionados.
	 */
	@Override
	public List<State> selection(List<State> listState, int truncation) {
		List<State> AuxList = new ArrayList<State>();
		if (Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar)) {
			listState = OrderBetter(listState);
		} else {
			if(Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Minimizar))
				listState = ascOrderBetter(listState);
		}
		int i = 0;
		while(AuxList.size()< truncation){
			AuxList.add(listState.get(i));
			i++;
		}
		return AuxList;
	}
}
