package metaheuristics.generators;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import problem.definition.State;

/**
 * An abstract class representing a generator of states for metaheuristic algorithms.
 * It provides a common interface for generating and managing states.
 */
public abstract class Generator {

	/**
	 * Generates a new state.
	 * @param operatornumber The operator number.
	 * @return A new state.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public abstract State generate(Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

	/**
	 * Updates the reference state.
	 * @param stateCandidate The candidate state.
	 * @param countIterationsCurrent The current number of iterations.
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public abstract void updateReference(State stateCandidate, Integer countIterationsCurrent) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

	/**
	 * Gets the reference state.
	 * @return The reference state.
	 */
	public abstract State getReference();

	/**
	 * Sets the initial reference state.
	 * @param stateInitialRef The initial reference state.
	 */
	public abstract void setInitialReference (State stateInitialRef);

	/**
	 * Gets the type of the generator.
	 * @return The generator type.
	 */
	public abstract GeneratorType getType ();

	/**
	 * Gets the list of reference states.
	 * @return The list of reference states.
	 */
	public abstract List<State> getReferenceList();

	/**
	 * Gets the list of son states.
	 * @return The list of son states.
	 */
	public abstract List<State> getSonList ();

	/**
	 * Awards the update of the reference state.
	 * @param stateCandidate The candidate state.
	 * @return True if the update is awarded, false otherwise.
	 */
	public abstract boolean awardUpdateREF(State stateCandidate);

	/**
	 * Sets the weight of the generator.
	 * @param weight The weight of the generator.
	 */
	public abstract void setWeight(float weight);

	/**
	 * Gets the weight of the generator.
	 * @return The weight of the generator.
	 */
	public abstract float getWeight();

	
	/**
	 * Gets the trace of the generator.
	 * @return The trace of the generator.
	 */
	public abstract float[] getTrace();
	
	/**
	 * Gets the list of count of better gender.
	 * @return the list of count of better gender.
	 */
	public abstract int[] getListCountBetterGender();
	
	/**
	 * Gets the list of count of gender.
	 * @return the list of count of gender.
	 */
	public abstract int[] getListCountGender();

}
