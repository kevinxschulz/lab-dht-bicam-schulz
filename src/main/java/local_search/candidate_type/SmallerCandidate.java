/**
 * @(#) SmallerCandidate.java
 */

package local_search.candidate_type;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import problem.definition.State;

/**
 * A search strategy that selects the candidate with the smallest evaluation value.
 */
public class SmallerCandidate extends SearchCandidate {

	/**
	 * Selects the candidate with the smallest evaluation value from a list of neighbors.
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
	@Override
	public State stateSearch(List<State> listNeighborhood) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		State stateSmaller = null;
		if(listNeighborhood.size() > 1){
			double counter = 0;
			double currentCount = listNeighborhood.get(0).getEvaluation().get(0);
			for (int i = 1; i < listNeighborhood.size(); i++) {
				counter = listNeighborhood.get(i).getEvaluation().get(0);
				if (counter < currentCount) {
					currentCount = counter;
					stateSmaller = listNeighborhood.get(i);
				}
				counter = 0;
			}
		}
		else stateSmaller = listNeighborhood.get(0);
		return stateSmaller;
	}
}