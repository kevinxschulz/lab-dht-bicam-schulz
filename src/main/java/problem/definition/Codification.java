package problem.definition;

/**
 * Abstract base class representing a problem codification/encoding.
 *
 * <p>Concrete codifications should define how states are represented, how to
 * validate a state, how to get a random/aleatory variable key and value, and
 * how many decision variables are present.</p>
 */
public abstract class Codification {

	/**
	 * Checks whether the provided {@link State} is a valid state according
	 * to this codification rules.
	 *
	 * @param state the state to validate
	 * @return {@code true} if the state is valid, otherwise {@code false}
	 */
	public abstract boolean validState(State state);

	// public abstract int getVariableDomain(int variable);

	/**
	 * Returns an aleatory (random) value for the variable identified by
	 * the given key. The returned object type depends on the concrete
	 * codification (e.g., Integer, Double, Boolean, etc.).
	 *
	 * @param key index/key of the variable
	 * @return a randomly chosen value for the variable
	 */
	public abstract Object getVariableAleatoryValue(int key);

	/**
	 * Returns a random variable key/index valid for this codification.
	 *
	 * @return an integer key representing a random variable index
	 */
	public abstract int getAleatoryKey();

	/**
	 * Returns the number of decision variables in this codification.
	 *
	 * @return number of variables
	 */
	public abstract int getVariableCount();

}