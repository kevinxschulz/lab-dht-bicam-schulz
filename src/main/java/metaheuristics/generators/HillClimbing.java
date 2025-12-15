package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import local_search.candidate_type.CandidateType;
import metaheuristics.strategy.Strategy;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

/**
 * A generator that implements the Hill Climbing algorithm.
 * It explores the search space by iteratively moving to a better neighbor solution.
 */
public class HillClimbing extends AbstractLocalSearchGenerator {

	/**
	 * Constructs a new HillClimbing generator with default values.
	 */
	public HillClimbing() {
		super(GeneratorType.HillClimbing);
		if (Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar)) {
			this.typeCandidate = CandidateType.GreaterCandidate;
		} else {
			this.typeCandidate = CandidateType.SmallerCandidate;
		}
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

}
