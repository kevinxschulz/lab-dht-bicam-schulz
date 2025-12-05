package local_search.complement;

/**
 * A class that provides a method to check if the execution of an algorithm should stop based on the number of iterations.
 */
public class StopExecute {
		
	/**
	 * Checks if the current number of iterations has reached the maximum number of iterations.
	 *
	 * @param countIterationsCurrent The current number of iterations.
	 * @param countmaxIterations The maximum number of iterations.
	 * @return `true` if the execution should stop, `false` otherwise.
	 */
	public Boolean stopIterations(int countIterationsCurrent, int countmaxIterations) {
		if (countIterationsCurrent < countmaxIterations) {
			return false;
		} else {
			return true;
		}
	}
}
