package local_search.complement;

import java.util.ArrayList;
import java.util.List;

import problem.definition.State;

/**
 * A class that manages the tabu list for a tabu search algorithm.
 */
public class TabuSolutions {
	
	/**
	 * The list of tabu solutions.
	 */
	public static final List<State> listTabu = new ArrayList<State>();

	/**
	 * The maximum number of elements in the tabu list.
	 */
	public static int maxelements; 

	/**
	 * Filters a neighborhood of solutions, removing those that are in the tabu list.
	 *
	 * @param listNeighborhood The neighborhood to filter.
	 * @return The filtered neighborhood.
	 * @throws Exception if the filtered neighborhood is empty.
	 */
	public List<State> filterNeighborhood(List<State> listNeighborhood) throws Exception {
		List<State> listFiltrate = new ArrayList<State>();
		//List<ProblemState> auxList = new ArrayList<ProblemState>();
		//auxList = listNeighborhood;
		//Problem problem = new Problem();
		if (!listTabu.isEmpty()) {
			for (int i = listNeighborhood.size() - 1; i >= 0 ; i--) {
				int count_tabu = 0; 
				while (listTabu.size() > count_tabu) {
					if (listNeighborhood.get(i).equals(listTabu.get(count_tabu))) {
						listNeighborhood.remove(i);
					}
					count_tabu++;
				}
			}
			listFiltrate = listNeighborhood;
			if (listFiltrate.isEmpty()) {
				throw new Exception();
			}
		} else {
			listFiltrate = listNeighborhood;
		}
		return listFiltrate;
	}

	/**
	 * Adds a state to the tabu list if it is not already present.
	 * If the list is full, removes the oldest entry first.
	 * 
	 * @param state The state to add to the tabu list.
	 */
	public static void addToTabuList(State state) {
		if (listTabu.size() < maxelements) {
			if (!containsState(state)) {
				listTabu.add(state);
			}
		} else {
			listTabu.remove(0);
			if (!containsState(state)) {
				listTabu.add(state);
			}
		}
	}

	/**
	 * Checks if a state is already in the tabu list.
	 * 
	 * @param state The state to check.
	 * @return true if the state is in the tabu list, false otherwise.
	 */
	private static boolean containsState(State state) {
		for (int i = 0; i < listTabu.size(); i++) {
			if (listTabu.get(i).Comparator(state)) {
				return true;
			}
		}
		return false;
	}
}