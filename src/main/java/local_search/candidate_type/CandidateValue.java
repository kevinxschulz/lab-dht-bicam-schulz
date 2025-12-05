/**
 * @(#) CandidateValue.java
 */

package local_search.candidate_type;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import problem.definition.State;

import local_search.complement.StrategyType;
import local_search.complement.TabuSolutions;
import metaheuristics.strategy.Strategy;

//import ceis.grial.problem.Problem;
import factory_interface.IFFactoryCandidate;
import factory_method.FactoryCandidate;

/**
 * This class is responsible for selecting a candidate state from a neighborhood.
 * It uses a factory to create a search strategy and can handle tabu search.
 */
public class CandidateValue {

	@SuppressWarnings("unused")
	private StrategyType strategy;

	private IFFactoryCandidate ifFactory;

	@SuppressWarnings("unused")
	private CandidateType typecand;

	private TabuSolutions tabusolution;

	private SearchCandidate searchcandidate;

	public CandidateValue(){}

	public CandidateValue(StrategyType strategy, IFFactoryCandidate ifFactory, CandidateType typecand, 
			TabuSolutions tabusolution, SearchCandidate searchcandidate) { //, Strategy executegenerator
		super();
		this.strategy = strategy;
		this.ifFactory = ifFactory;
		this.typecand = typecand;
		this.tabusolution = tabusolution;
		this.searchcandidate = searchcandidate;
	}

	/**
	 * Creates a new search candidate strategy.
	 *
	 * @param typecandidate The type of candidate search strategy to create.
	 * @return A new instance of a search candidate strategy.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public SearchCandidate newSearchCandidate(CandidateType typecandidate) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ifFactory = new FactoryCandidate();
		searchcandidate = ifFactory.createSearchCandidate(typecandidate);
		return searchcandidate;
	}

	/**
	 * Selects a candidate state from a neighborhood.
	 *
	 * @param stateCurrent The current state.
	 * @param typeCandidate The type of candidate search strategy to use.
	 * @param strategy The search strategy (e.g., Tabu search).
	 * @param operatornumber The operator number to use for generating new states.
	 * @param neighborhood The neighborhood of states to search.
	 * @return The selected candidate state.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public State stateCandidate(State stateCurrent, CandidateType typeCandidate, StrategyType strategy, Integer operatornumber, List<State> neighborhood) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		//Problem problem = ExecuteGenerator.getExecuteGenerator().getProblem();
		State stateCandidate;
		List<State> auxList = new ArrayList<State>();
		for (int i = 0; i < neighborhood.size(); i++) {
			auxList.add(neighborhood.get(i));
		}
		this.tabusolution = new TabuSolutions();
		if (strategy.equals(StrategyType.TABU)) {
			try {
				auxList = this.tabusolution.filterNeighborhood(auxList);
			}
			catch (Exception e) {
				Strategy strategys = Strategy.getStrategy();
				if(strategys.getProblem()!=null){
					neighborhood = strategys.getProblem().getOperator().generatedNewState(neighborhood.get(0), operatornumber);
				}
				return stateCandidate(stateCurrent, typeCandidate, strategy, operatornumber, neighborhood);
			}
		}
		SearchCandidate searchCand = newSearchCandidate(typeCandidate);
		stateCandidate = searchCand.stateSearch(auxList);
		return stateCandidate;
	}

	public TabuSolutions getTabusolution() {
		return tabusolution;
	}

	public void setTabusolution(TabuSolutions tabusolution) {
		this.tabusolution = tabusolution;
	}
}
