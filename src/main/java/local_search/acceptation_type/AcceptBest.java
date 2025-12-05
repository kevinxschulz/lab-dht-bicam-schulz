/**
 * @(#) AcceptBest.java
 */

package local_search.acceptation_type;

import java.lang.reflect.InvocationTargetException;

import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

/**
 * An acceptance criterion that accepts a candidate solution if it is better than or equal to the current solution.
 * "Better" is determined by the problem type (maximization or minimization).
 */
public class AcceptBest extends AcceptableCandidate {

	/**
	 * Accepts the candidate solution if its evaluation is better than or equal to the current solution's evaluation.
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
		if(problem.getTypeProblem().equals(ProblemType.Maximizar)) {
			if (stateCandidate.getEvaluation().get(0) >= stateCurrent.getEvaluation().get(0)) {
				accept = true;
			} else {
				accept = false;
			}
		} else {
			if (stateCandidate.getEvaluation().get(0) <= stateCurrent.getEvaluation().get(0)) {
				accept = true;
			} else {
				accept = false;
			}
		}
		return accept;
	}
}
