/**
 * @(#) TypeCandidate.java
 */

package local_search.candidate_type;

/**
 * An enumeration of the different types of candidate generation strategies.
 */
public enum CandidateType{
	
	/**
	 * A strategy that selects the candidate with the smallest evaluation value.
	 */
	SmallerCandidate,
	/**
	 * A strategy that selects the candidate with the greatest evaluation value.
	 */
	GreaterCandidate,
	/**
	 * A strategy that selects a random candidate.
	 */
	RandomCandidate,
	/**
	 * A strategy that selects a non-dominated candidate.
	 */
	NotDominatedCandidate;
}
