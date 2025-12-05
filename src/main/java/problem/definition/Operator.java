package problem.definition;

import java.util.List;

/**
 * Abstract operator class used to generate new {@link State} instances.
 *
 * <p>Operators are responsible for producing new candidate states either
 * from an existing state (e.g. via mutation/crossover) or by generating
 * random states according to the problem codification.</p>
 */
public abstract class Operator {

	/**
	 * Generate a list of new states derived from {@code stateCurrent}.
	 *
	 * @param stateCurrent the state to operate on
	 * @param operatornumber an identifier for the operator variant
	 * @return list of generated states (may be empty)
	 */
	public abstract List<State> generatedNewState(State stateCurrent, Integer operatornumber);

	/**
	 * Generate a list of random states (not derived from an existing
	 * state) according to the operator configuration.
	 *
	 * @param operatornumber an identifier for the operator variant
	 * @return list of randomly generated states
	 */
	public abstract List<State> generateRandomState(Integer operatornumber);

}

