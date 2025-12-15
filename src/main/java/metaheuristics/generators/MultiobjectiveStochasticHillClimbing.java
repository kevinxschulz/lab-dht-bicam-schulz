package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import factory_interface.IFFactoryAcceptCandidate;
import factory_method.FactoryAcceptCandidate;
import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StrategyType;
import metaheuristics.strategy.Strategy;
import problem.definition.State;


/**
 * Multi-objective stochastic hill climbing generator.
 *
 * <p>Explores the neighborhood of a reference state and chooses candidate
 * solutions using a multi-objective acceptance rule. Intended for problems
 * with multiple objectives where nondominated moves are preferred.
 */
public class MultiobjectiveStochasticHillClimbing extends AbstractLocalSearchGenerator {

	public MultiobjectiveStochasticHillClimbing() {
		super(GeneratorType.MultiobjectiveStochasticHillClimbing);
		this.typeAcceptation = AcceptType.AcceptNotDominated;
		this.strategy = StrategyType.NORMAL;
		this.typeCandidate = CandidateType.NotDominatedCandidate;
		this.candidatevalue = new CandidateValue();
	}

	@Override
	public void updateReference(State stateCandidate, Integer countIterationsCurrent) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ifacceptCandidate = new FactoryAcceptCandidate();
		AcceptableCandidate candidate = ifacceptCandidate.createAcceptCandidate(typeAcceptation);
		Boolean accept = candidate.acceptCandidate(referenceState, stateCandidate);
		if(accept.equals(true))
		  referenceState = stateCandidate.clone();
		getReferenceList();
	}
	
	@Override
	public List<State> getReferenceList() {
		if (listStateReference == null) {
			listStateReference = new ArrayList<>();
		}
		listStateReference.add(referenceState.clone());
		return listStateReference;
	}

	@Override
	public List<State> getSonList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean awardUpdateREF(State stateCandidate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
