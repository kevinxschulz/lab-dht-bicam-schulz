/**
 * @(#) SearchCandidate.java
 */

package local_search.candidate_type;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import problem.definition.State;


/**
 * An abstract class for search strategies that select a candidate from a list of neighbors.
 * Subclasses must implement the `stateSearch` method.
 */
public abstract class SearchCandidate {
	
	/**
	 * Selects a candidate from a list of neighbors.
	 *
	 * @param listNeighborhood The list of neighbor states.
	 * @return The selected candidate state.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public abstract State stateSearch(List<State> listNeighborhood) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException ;
}
