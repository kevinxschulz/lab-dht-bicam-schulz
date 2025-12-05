package local_search.acceptation_type;

import java.lang.reflect.InvocationTargetException;

import problem.definition.State;

/**
 * An abstract class for determining whether a candidate solution should be accepted in a local search algorithm.
 * Subclasses must implement the `acceptCandidate` method.
 */
public abstract class AcceptableCandidate {
  
	/**
	 * Determines whether a candidate solution should be accepted.
	 *
	 * @param stateCurrent The current state.
	 * @param stateCandidate The candidate state.
	 * @return `true` if the candidate state is accepted, `false` otherwise.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public abstract Boolean acceptCandidate(State stateCurrent, State stateCandidate) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException ;
}
