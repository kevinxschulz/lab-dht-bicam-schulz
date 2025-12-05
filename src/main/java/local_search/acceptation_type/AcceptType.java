/**
 * @(#) TypeAcceptation.java
 */

package local_search.acceptation_type;

/**
 * An enumeration of the different acceptance criteria that can be used in a local search algorithm.
 */
public enum AcceptType
{
	/**
	 * Accepts a candidate solution if it is better than or equal to the current solution.
	 */
	AcceptBest,
	/**
	 * Accepts any candidate solution.
	 */
	AcceptAnyone,
	/**
	 * An acceptance criterion for simulated annealing that accepts a candidate solution if it is not worse than the current solution,
	 * or with a certain probability if it is worse.
	 */
	AcceptNotBadT,
	/**
	 * An acceptance criterion that accepts a candidate solution if the difference in evaluation
	 * between the current and candidate solutions is within a certain threshold.
	 */
	AcceptNotBadU,
	/**
	 * An acceptance criterion that accepts a candidate solution if it is not dominated by the current solution.
	 * For multi-objective problems.
	 */
	AcceptNotDominated,
	/**
	 * An acceptance criterion for tabu search that accepts a candidate solution if it is not dominated.
	 * For multi-objective problems.
	 */
	AcceptNotDominatedTabu,
	/**
	 * An acceptance criterion that accepts a candidate solution if it is not worse than the current solution.
	 */
	AcceptNotBad,
	/**
	 * An acceptance criterion for multi-objective simulated annealing.
	 */
	AcceptMulticase;
	
}
