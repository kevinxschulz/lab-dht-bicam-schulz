package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StrategyType;
import local_search.complement.TabuSolutions;
import metaheuristics.strategy.Strategy;

import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

import factory_method.FactoryAcceptCandidate;

/**
 * A generator that implements the Tabu Search algorithm.
 * It uses a tabu list to avoid previously visited solutions and escape local optima.
 */
public class TabuSearch extends AbstractLocalSearchGenerator {

	/**
	 * Constructs a new TabuSearch generator with default values.
	 */
	public TabuSearch() {
    	super(GeneratorType.TabuSearch);
		this.typeAcceptation = AcceptType.AcceptAnyone;
		this.strategy = StrategyType.TABU;
		
		Problem problem = Strategy.getStrategy().getProblem();

		if(problem.getTypeProblem().equals(ProblemType.Maximizar)) {
			this.typeCandidate = CandidateType.GreaterCandidate;
		}
		else{
			this.typeCandidate = CandidateType.SmallerCandidate;
		}

		this.candidatevalue = new CandidateValue();
	}

	void setCandidateValue(CandidateValue candidateValue) {
		this.candidatevalue = candidateValue;
	}

	void setAcceptCandidateFactory(factory_interface.IFFactoryAcceptCandidate factory) {
		this.ifacceptCandidate = factory;
	}

	/**
	 * Generates a new state by exploring the neighborhood of the current reference state.
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
		neighborhood = Strategy.getStrategy().getProblem().getOperator().generatedNewState(referenceState, operatornumber);
	    State statecandidate = candidatevalue.stateCandidate(referenceState, typeCandidate, strategy, operatornumber, neighborhood);
	    return statecandidate;
	}

	/**
	 * Updates the reference state and the tabu list.
	 * If the candidate is accepted, it becomes the new reference state.
	 * If the strategy is TABU, the new state is added to the tabu list to avoid revisiting it.
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
	public void updateReference(State stateCandidate, Integer countIterationsCurrent)throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (ifacceptCandidate == null) {
			ifacceptCandidate = new FactoryAcceptCandidate();
		}
		AcceptableCandidate candidate = ifacceptCandidate.createAcceptCandidate(typeAcceptation);
		Boolean acept = candidate.acceptCandidate(referenceState, stateCandidate);
		if(acept.equals(true))
			referenceState = stateCandidate;

		if (strategy.equals(StrategyType.TABU) && acept.equals(true)) {
			if (TabuSolutions.listTabu.size() < TabuSolutions.maxelements) {
				Boolean find = false;
				int count = 0;
				while ((TabuSolutions.listTabu.size() > count) && (find.equals(false))) {
					if ((TabuSolutions.listTabu.get(count).Comparator(stateCandidate))==true) {
						find = true;
					}
					count++;
				}
				if (find.equals(false)) {
					TabuSolutions.listTabu.add(stateCandidate);
				}
			} else {
				TabuSolutions.listTabu.remove(0);
				Boolean find = false;
				int count = 0;
				while (TabuSolutions.listTabu.size() > count && find.equals(false)) {
					if ((TabuSolutions.listTabu.get(count).Comparator(stateCandidate))==true) {
						find = true;
					}
					count++;
				}
				if (find.equals(false)) {
					TabuSolutions.listTabu.add(stateCandidate);
				}
			}
		}
	}
}



