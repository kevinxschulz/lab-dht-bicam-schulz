/**
 * @(#) AcceptNoBadU.java
 */

package local_search.acceptation_type;

import java.lang.reflect.InvocationTargetException;

import metaheurictics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

/**
 * An acceptance criterion that accepts a candidate solution if the difference in evaluation
 * between the current and candidate solutions is within a certain threshold.
 */
public class AcceptNotBadU extends AcceptableCandidate{

	/**
	 * Accepts the candidate solution if the difference in evaluation is within the threshold.
	 *
	 * @param stateCurrent The current state.
	 * @param stateCandidate The candidate state.
	 * @return `true` if the candidate is accepted, `false` otherwise.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Override
	public Boolean acceptCandidate(State stateCurrent, State stateCandidate) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Boolean accept = null;
		Problem problem = Strategy.getStrategy().getProblem();
		if (problem.getTypeProblem().equals(ProblemType.Maximizar)) {
			Double result = stateCurrent.getEvaluation().get(0) - stateCandidate.getEvaluation().get(0);
			if (result < Strategy.getStrategy().getThreshold())
				accept = true;
			else
				accept = false;
		} else {
			Double result_min = stateCurrent.getEvaluation().get(0) - stateCandidate.getEvaluation().get(0);
			if (result_min > Strategy.getStrategy().getThreshold())
				accept = true;
			else
				accept = false;
		}
		return accept;
	}
}
