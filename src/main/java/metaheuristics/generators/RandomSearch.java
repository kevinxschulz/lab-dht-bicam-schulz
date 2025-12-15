package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StrategyType;
import metaheuristics.strategy.Strategy;

import problem.definition.State;

import factory_method.FactoryAcceptCandidate;

/**
 * A generator that implements the Random Search algorithm.
 * It explores the search space by generating random solutions.
 */
public class RandomSearch extends AbstractLocalSearchGenerator {

    /**
     * Constructs a new RandomSearch generator with default values.
     */
	public RandomSearch() {
		super(GeneratorType.RandomSearch);
		this.typeAcceptation = AcceptType.AcceptBest;
		this.strategy = StrategyType.NORMAL;
		this.typeCandidate = CandidateType.RandomCandidate;
		this.candidatevalue = new CandidateValue();
	}
	
	/**
	 * Generates a new random state.
	 * @param operatornumber The operator number.
	 * @return A new state.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Override
	public State generate(Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<State> neighborhood = new ArrayList<State>();
		neighborhood = Strategy.getStrategy().getProblem().getOperator().generateRandomState(operatornumber);
	    State statecandidate = candidatevalue.stateCandidate(referenceState, typeCandidate, strategy, operatornumber, neighborhood);
	    if(GeneticAlgorithm.countRef != 0 || EvolutionStrategies.countRef != 0 || DistributionEstimationAlgorithm.countRef != 0 || ParticleSwarmOptimization.countRef != 0)
	    	listStateReference.add(statecandidate);
	    return statecandidate;
	}

	/**
	 * Updates the reference state based on the acceptance criteria.
	 * @param stateCandidate The candidate state.
	 * @param countIterationsCurrent The current number of iterations.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Override
	public void updateReference(State stateCandidate, Integer countIterationsCurrent) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException,	IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ifacceptCandidate = new FactoryAcceptCandidate();
		AcceptableCandidate candidate = ifacceptCandidate.createAcceptCandidate(typeAcceptation);
		Boolean accept = candidate.acceptCandidate(referenceState, stateCandidate);
		if(accept.equals(true))
		  referenceState = stateCandidate;
	}

}
