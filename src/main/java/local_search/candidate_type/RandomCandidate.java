/**
 * @(#) AleatoryCandidate.java
 */

package local_search.candidate_type;

import java.util.List;

import problem.definition.State;

/**
 * A search strategy that selects a random candidate from a list of neighbors.
 */
public class RandomCandidate extends SearchCandidate {

	/**
	 * Selects a random candidate from a list of neighbors.
	 *
	 * @param listNeighborhood The list of neighbor states.
	 * @return The selected random candidate state.
	 */
	@Override
	public State stateSearch(List<State> listNeighborhood) {
		int pos = (int)(Math.random() * (double)(listNeighborhood.size() - 1));
		State stateAleatory = listNeighborhood.get(pos);
		return stateAleatory;
	}
}
