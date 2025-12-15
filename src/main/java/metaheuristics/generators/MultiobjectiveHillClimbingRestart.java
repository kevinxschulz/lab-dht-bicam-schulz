package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import factory_interface.IFFactoryAcceptCandidate;
import factory_method.FactoryAcceptCandidate;

import problem.definition.State;

import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StrategyType;
import metaheuristics.strategy.Strategy;




/**
 * Multi-objective hill climbing with restarts.
 *
 * <p>Performs hill climbing suitable for multi-objective optimization and
 * restarts the search from random positions when needed to escape plateaus
 * or stagnation.
 */
public class MultiobjectiveHillClimbingRestart extends AbstractLocalSearchGenerator {

	private List<State> visitedState = new ArrayList<State>();
	public static int sizeNeighbors;


	public MultiobjectiveHillClimbingRestart() {
		super(GeneratorType.MultiobjectiveHillClimbingRestart);
		this.typeAcceptation = AcceptType.AcceptNotDominated;
		this.strategy = StrategyType.NORMAL;
		//Problem problem = Strategy.getStrategy().getProblem();
		this.typeCandidate = CandidateType.NotDominatedCandidate;
		this.candidatevalue = new CandidateValue();
	}

	@Override
	public State generate(Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<State> neighborhood = new ArrayList<State>();
		neighborhood = Strategy.getStrategy().getProblem().getOperator().generatedNewState(referenceState, operatornumber);
		State statecandidate = candidatevalue.stateCandidate(referenceState, typeCandidate, strategy, operatornumber, neighborhood);
		return statecandidate;
	}

	@Override
	public void updateReference(State stateCandidate, Integer countIterationsCurrent) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//Agregando la primera soluci�n a la lista de soluciones no dominadas

		if(Strategy.getStrategy().listRefPoblacFinal.size() == 0){
			Strategy.getStrategy().listRefPoblacFinal.add(referenceState.clone());
		}

		ifacceptCandidate = new FactoryAcceptCandidate();
		AcceptableCandidate candidate = ifacceptCandidate.createAcceptCandidate(typeAcceptation);
		State lastState = Strategy.getStrategy().listRefPoblacFinal.get(Strategy.getStrategy().listRefPoblacFinal.size()-1);
		List<State> neighborhood = new ArrayList<State>();
		neighborhood = Strategy.getStrategy().getProblem().getOperator().generatedNewState(referenceState, sizeNeighbors);
		int i= 0;

		Boolean accept = candidate.acceptCandidate(lastState, stateCandidate.clone());

		if(accept.equals(true)){
			referenceState = stateCandidate.clone();
			visitedState = new ArrayList<State>();
			lastState = stateCandidate.clone();
			//tomar xc q pertenesca a la vecindad de xa
		}
		else{
			boolean stop = false;
			while (i < neighborhood.size()&& stop==false) {
				if (Contain(neighborhood.get(i))==false) {
					stateCandidate = neighborhood.get(i);
					Strategy.getStrategy().getProblem().Evaluate(stateCandidate);  
					visitedState.add(stateCandidate);
					accept = candidate.acceptCandidate(lastState, stateCandidate.clone());
					stop=true;
				}
				i++;
			}
			while (stop == false) {
				stateCandidate = Strategy.getStrategy().getProblem().getOperator().generateRandomState(1).get(0);
				if (Contain(stateCandidate)==false) {
					Strategy.getStrategy().getProblem().Evaluate(stateCandidate);  
					stop=true;
					accept = candidate.acceptCandidate(lastState, stateCandidate.clone());

				}
			}
			if(accept.equals(true)){
				referenceState = stateCandidate.clone();
				visitedState = new ArrayList<State>();
				lastState = stateCandidate.clone();
				//tomar xc q pertenesca a la vecindad de xa
			}
		}


		getReferenceList();
	}

	@Override
	public List<State> getReferenceList() {
		listStateReference.add(referenceState.clone());
		return listStateReference;
	}

	@Override
	public List<State> getSonList() {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean Contain(State state){
		boolean found = false;
		for (Iterator<State> iter = visitedState.iterator(); iter.hasNext();) {
			State element = (State) iter.next();
			if(element.Comparator(state)==true){
				found = true;
			}
		}
		return found;
	}

	@Override
	public boolean awardUpdateREF(State stateCandidate) {
		// TODO Auto-generated method stub
		return false;
	}
}
