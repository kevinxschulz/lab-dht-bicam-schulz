/**
 * @(#) IFFactoryAcceptCandidate.java
 */

package factory_interface;

import java.lang.reflect.InvocationTargetException;

import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;





/**
 * An interface for a factory that creates objects that implement the AcceptableCandidate interface.
 * These objects determine whether a candidate solution should be accepted in a local search algorithm.
 */
public interface IFFactoryAcceptCandidate
{
	/**
	 * Creates an instance of a class that implements the AcceptableCandidate interface.
	 *
	 * @param typeacceptation The type of acceptance criteria to use.
	 * @return An object that implements the AcceptableCandidate interface.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	AcceptableCandidate createAcceptCandidate(AcceptType typeacceptation) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

}
