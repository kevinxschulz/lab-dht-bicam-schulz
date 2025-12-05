/**
 * @(#) FactoryCandidate.java
 */
package factory_method;

import java.lang.reflect.InvocationTargetException;

import local_search.candidate_type.CandidateType;
import local_search.candidate_type.SearchCandidate;

import factory_interface.IFFactoryCandidate;



/**
 * A factory for creating objects that implement the SearchCandidate interface.
 * This class uses the Factory Method design pattern.
 */
public class FactoryCandidate implements IFFactoryCandidate{
	private SearchCandidate searchcandidate;
	
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
	public SearchCandidate createSearchCandidate(CandidateType typeCandidate) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String className = "local_search.candidate_type." + typeCandidate.toString();
		searchcandidate = (SearchCandidate) FactoryLoader.getInstance(className);
		return searchcandidate;
	}
}
