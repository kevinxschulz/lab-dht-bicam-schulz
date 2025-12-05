package local_search.acceptation_type;


import local_search.acceptation_type.Dominance;
import metaheuristics.strategy.Strategy;

import problem.definition.State;

/**
 * An acceptance criterion that accepts a candidate solution if it is not dominated by the current solution.
 * For multi-objective problems.
 */
public class AcceptNotDominated extends AcceptableCandidate {
	
	/**
	 * Accepts the candidate solution if it is not dominated by the current solution.
	 *
	 * @param stateCurrent The current state.
	 * @param stateCandidate The candidate state.
	 * @return `true` if the candidate is accepted, `false` otherwise.
	 */
	@Override
	public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
		Boolean accept = false;
		Dominance dominace = new Dominance();
		
		if(Strategy.getStrategy().listRefPoblacFinal.size() == 0){
			Strategy.getStrategy().listRefPoblacFinal.add(stateCurrent.clone());
		}
		if(dominace.dominance(stateCurrent, stateCandidate)== false)
		{
			//Verificando si la solucin candidata domina a alguna de las soluciones de la lista de soluciones no dominadas
			//De ser as se eliminan de la lista y se adiciona la nueva solucin en la lista
			//De lo contrario no se adiciona la solucin candidata a la lista
			//Si fue insertada en la lista entonces la solucion candidata se convierte en solucion actual
			if(dominace.ListDominance(stateCandidate, Strategy.getStrategy().listRefPoblacFinal) == true){
				//Se pone la solucin candidata como solucin actual
				accept = true;
			}
			else{
				accept = false;
			}
		}
		return accept;
	}

}