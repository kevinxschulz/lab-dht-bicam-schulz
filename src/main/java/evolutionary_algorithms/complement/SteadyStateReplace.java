package evolutionary_algorithms.complement;


import java.util.List;
import metaheuristics.strategy.Strategy;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

/**
 * Clase que implementa la estrategia de reemplazo de estado estacionario.
 * En esta estrategia, solo los individuos de peor aptitud son reemplazados por nuevos individuos,
 * manteniendo la mayor parte de la población original.
 */
public class SteadyStateReplace extends Replace {

	/**
	 * Realiza el reemplazo de estado estacionario.
	 * Si el problema es de maximización, reemplaza el individuo con el valor mínimo si el candidato es mejor.
	 * Si el problema es de minimización, reemplaza el individuo con el valor máximo si el candidato es mejor.
	 * @param stateCandidate El estado candidato a ser insertado en la población.
	 * @param listState La lista de estados de la población actual.
	 * @return La nueva lista de estados después del reemplazo.
	 */
	@Override
	public List<State> replace(State stateCandidate, List<State> listState) {
		State stateREP = null;
		if (Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar)) {
			stateREP = MinValue(listState);
			if(stateCandidate.getEvaluation().get(0) >= stateREP.getEvaluation().get(0)){
				Boolean find = false;
		        int count = 0;
		        while ((find.equals(false)) && (listState.size() > count)){
		        	if(listState.get(count).equals(stateREP)){
		        		listState.remove(count);
						listState.add(count, stateCandidate);
						find = true;
					}
		        	else count ++;
				}
			}
		}
		else {
			if(Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Minimizar)){
				stateREP = MaxValue(listState);
				if(stateCandidate.getEvaluation().get(0) <= stateREP.getEvaluation().get(0)){
					Boolean find = false;
			        int count = 0;
			        while ((find.equals(false)) && (listState.size() > count)){
			        	if(listState.get(count).equals(stateREP)){
			        		listState.remove(count);
							listState.add(count, stateCandidate);
							find = true;
						}
			        	else count ++;
					}
				}
			}
		}
		return listState;
	}
	
	/**
	 * Encuentra el estado con el valor de evaluación mínimo en una lista de estados.
	 * @param listState La lista de estados a evaluar.
	 * @return El estado con el valor mínimo.
	 */
	public State MinValue (List<State> listState){
		State value = listState.get(0);
		double min = listState.get(0).getEvaluation().get(0);
		for (int i = 1; i < listState.size(); i++) {
			if(listState.get(i).getEvaluation().get(0) < min){
				min = listState.get(i).getEvaluation().get(0);
				value = listState.get(i);
			}
		}
		return value;
	}
	
	/**
	 * Encuentra el estado con el valor de evaluación máximo en una lista de estados.
	 * @param listState La lista de estados a evaluar.
	 * @return El estado con el valor máximo.
	 */
	public State MaxValue (List<State> listState){
		State value = listState.get(0);
		double max = listState.get(0).getEvaluation().get(0);
		for (int i = 1; i < listState.size(); i++) {
			if(listState.get(i).getEvaluation().get(0) > max){
				max = listState.get(i).getEvaluation().get(0);
				value = listState.get(i);
			}
		}
		return value;
	}
}
