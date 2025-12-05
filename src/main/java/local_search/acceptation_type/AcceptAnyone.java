/**
 * @(#) AcceptAnyone.java
 */

package local_search.acceptation_type;

import problem.definition.State;


/**
 * An acceptance criterion that accepts any candidate solution.
 */
public class AcceptAnyone extends AcceptableCandidate{

	/**
	 * Always returns `true`, accepting any candidate solution.
	 *
	 * @param stateCurrent The current state (ignored).
	 * @param stateCandidate The candidate state (ignored).
	 * @return `true`.
	 */
	@Override
	public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
		Boolean accept = true;
		return accept;
	}
	
}
