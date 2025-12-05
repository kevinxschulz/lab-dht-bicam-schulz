/**
 * @(#) IFFactoryCandidate.java
 */

package factory_interface;

import java.lang.reflect.InvocationTargetException;

import local_search.candidate_type.CandidateType;
import local_search.candidate_type.SearchCandidate;



/**
 * An interface for a factory that creates objects that implement the SearchCandidate interface.
 * These objects are responsible for searching for candidate solutions in a local search algorithm.
 */
public interface IFFactoryCandidate
{
	/**
	 * Creates an instance of a class that implements the SearchCandidate interface.
	 *
	 * @param typeCandidate The type of candidate search strategy to use.
	 * @return An object that implements the SearchCandidate interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	SearchCandidate createSearchCandidate(CandidateType typeCandidate) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
