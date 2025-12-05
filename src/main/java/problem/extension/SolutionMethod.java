package problem.extension;

import problem.definition.State;

/**
 * Abstract base class for solution evaluation methods used by the
 * problem extension mechanisms. Concrete subclasses implement a
 * strategy for evaluating a {@link problem.definition.State} and
 * storing the resulting evaluation vector in the provided state.
 */
public abstract class SolutionMethod {

	/**
	 * Evaluate the given state and store the computed evaluation values
	 * inside the state object (via {@code state.setEvaluation(...) }).
	 *
	 * @param state the state to evaluate; implementations should update
	 *              the state's evaluation vector accordingly
	 */
	public abstract void evaluationState(State state);
}
