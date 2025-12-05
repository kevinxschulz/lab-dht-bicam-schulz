package local_search.acceptation_type;

import metaheuristics.generators.*;
import metaheuristics.strategy.*;

import java.util.List;
import java.util.Random;

import problem.definition.State;

/**
 * An acceptance criterion for multi-objective simulated annealing.
 * It uses a dominance-based approach to decide whether to accept a candidate solution.
 */
public class AcceptMulticase extends AcceptableCandidate {

	/**
	 * Decides whether to accept a candidate solution based on dominance and a probability function.
	 *
	 * @param stateCurrent The current state.
	 * @param stateCandidate The candidate state.
	 * @return `true` if the candidate is accepted, `false` otherwise.
	 */
	@Override
	public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
		// TODO Auto-generated method stub
		Boolean accept = false;
		List<State> list = Strategy.getStrategy().listRefPoblacFinal;
		
		if(list.size() == 0){
			list.add(stateCurrent.clone());
		}
		Double T = MultiCaseSimulatedAnnealing.tinitial;
		double pAccept = 0;
		Random rdm = new Random();
		Dominance dominance= new Dominance();
		//Verificando si la solucin candidata domina a la solucin actual
		//Si la solucin candidata domina a la solucin actual
		if(dominance.dominance(stateCandidate, stateCurrent) == true){
			//Se asigna como solucin actual la solucin candidata con probabilidad 1
			pAccept = 1; 
		}
		else if(dominance.dominance(stateCandidate, stateCurrent)== false){	
			if(DominanceCounter(stateCandidate, list) > 0){
				pAccept = 1;
			}
			else if(DominanceRank(stateCandidate, list) == 0){
				pAccept = 1;
			}
			else if(DominanceRank(stateCandidate, list) < DominanceRank(stateCurrent, list)){
				pAccept = 1;
			}
			else if(DominanceRank(stateCandidate, list) == DominanceRank(stateCurrent, list)){
				//Calculando la probabilidad de aceptacin
				List<Double> evaluations = stateCurrent.getEvaluation();
				double total = 0;
				for (int i = 0; i < evaluations.size()-1; i++) {
					Double evalA = evaluations.get(i);
					Double evalB = stateCandidate.getEvaluation().get(i);
					if (evalA != 0 && evalB != 0) {
						total += (evalA - evalB)/((evalA + evalB)/2);
					}
				}	
				pAccept = Math.exp(-(1-total)/T);
			}
			else if (DominanceRank(stateCandidate, list) > DominanceRank(stateCurrent, list) && DominanceRank(stateCurrent, list)!= 0){
				float value = DominanceRank(stateCandidate, list)/DominanceRank(stateCurrent, list);
				pAccept = Math.exp(-(value+1)/T);
			}
			else{
				//Calculando la probabilidad de aceptacin
				List<Double> evaluations = stateCurrent.getEvaluation();
				double total = 0;
				for (int i = 0; i < evaluations.size()-1; i++) {
					Double evalA = evaluations.get(i);
					Double evalB = stateCandidate.getEvaluation().get(i);
					if (evalA != 0 && evalB != 0) {
						total += (evalA - evalB)/((evalA + evalB)/2);
					}
				}
				pAccept = Math.exp(-(1-total)/T);
			}
		}
		//Generar un nmero aleatorio
		if((rdm.nextFloat()) < pAccept){
			stateCurrent = stateCandidate.clone();
			//Verificando que la solucin candidata domina a alguna de las soluciones
			accept = dominance.ListDominance(stateCandidate, list);
		}
		return accept;
	}


	/**
	 * Counts how many solutions in the list are dominated by the candidate state.
	 *
	 * @param stateCandidate The candidate state.
	 * @param list The list of states to compare against.
	 * @return The number of dominated solutions.
	 */
	private int DominanceCounter(State stateCandidate, List<State> list) { //chequea el nmero de soluciones de Pareto que son dominados por la nueva solucin
		int counter = 0;
		for (int i = 0; i < list.size(); i++) {
			State solution = list.get(i);
			Dominance dominance = new Dominance();
			if(dominance.dominance(stateCandidate, solution) == true)
				counter++;
		}
		return counter;
	}

	/**
	 * Calculates the dominance rank of the candidate state with respect to the list of states.
	 * The rank is the number of solutions in the list that dominate the candidate.
	 *
	 * @param stateCandidate The candidate state.
	 * @param list The list of states to compare against.
	 * @return The dominance rank of the candidate state.
	 */
	private int DominanceRank(State stateCandidate, List<State> list) { //calculando el nmero de soluciones en el conjunto de Pareto que dominan a la solucin
		int rank = 0;
		for (int i = 0; i < list.size(); i++) {
			State solution = list.get(i);
			Dominance dominance = new Dominance();
			if(dominance.dominance(solution, stateCandidate) == true){
				rank++;
			}
		}
		
		return rank;
	}

}