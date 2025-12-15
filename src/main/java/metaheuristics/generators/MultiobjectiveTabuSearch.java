package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.candidate_type.CandidateType;
import local_search.complement.StrategyType;
import local_search.complement.TabuSolutions;
import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;
import factory_method.FactoryAcceptCandidate;

/**
 * Multi-objective Tabu Search generator.
 *
 * <p>Explores neighborhoods under a tabu strategy and maintains a list of
 * nondominated solutions. Uses multi-objective acceptance rules specific
 * for tabu-based exploration.
 */
public class MultiobjectiveTabuSearch extends AbstractLocalSearchGenerator {

    private List<State> listStateReference = new ArrayList<State>();
	private List<Float> listTrace = new ArrayList<Float>();

	public MultiobjectiveTabuSearch() {
    	super(GeneratorType.MultiobjectiveTabuSearch);
		this.typeAcceptation = AcceptType.AcceptNotDominatedTabu;
		this.strategy = StrategyType.TABU;
		@SuppressWarnings("unused")
		Problem problem = Strategy.getStrategy().getProblem();
		this.typeCandidate = CandidateType.RandomCandidate;
		this.weight = 50;
		listTrace.add(weight);
	}

	@Override
	public void updateReference(State stateCandidate, Integer countIterationsCurrent)throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ifacceptCandidate = new FactoryAcceptCandidate();
		AcceptableCandidate candidate = ifacceptCandidate.createAcceptCandidate(typeAcceptation);
		Boolean acept = candidate.acceptCandidate(referenceState, stateCandidate);
		if(acept.equals(true))
		  referenceState = stateCandidate;

		if (strategy.equals(StrategyType.TABU) && acept.equals(true)) {
			TabuSolutions.addToTabuList(stateCandidate);
		}
		getReferenceList();
  }
	
	@Override
	public List<State> getReferenceList() {
		listStateReference.add(referenceState);
		return listStateReference;
	}

	public State getStateReferenceTS() {
		return referenceState;
	}

	public void setStateReferenceTS(State stateReferenceTS) {
		this.referenceState = stateReferenceTS;
	}

	/**
	 * Gets the type of the generator.
	 * @return The type of the generator.
	 */
	public GeneratorType getTypeGenerator() {
		return generatorType;
	}

	/**
	 * Sets the type of the generator.
	 * @param typeGenerator The type of the generator.
	 */
	public void setTypeGenerator(GeneratorType typeGenerator) {
		this.generatorType = typeGenerator;
	}

}
