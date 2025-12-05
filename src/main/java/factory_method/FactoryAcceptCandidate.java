/**
 * @(#) FactoryAcceptCandidate.java
 */

package factory_method;

import java.lang.reflect.InvocationTargetException;

import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;


import factory_interface.IFFactoryAcceptCandidate;



/**
 * A factory for creating objects that implement the AcceptableCandidate interface.
 * This class uses the Factory Method design pattern.
 */
public class FactoryAcceptCandidate implements IFFactoryAcceptCandidate{
	private AcceptableCandidate acceptCandidate;
	
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
	public AcceptableCandidate createAcceptCandidate( AcceptType typeacceptation ) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		String className = "local_search.acceptation_type." + typeacceptation.toString();
		acceptCandidate = (AcceptableCandidate) FactoryLoader.getInstance(className);
		return acceptCandidate;
	}
}
