package problem.definition;

import problem.definition.Problem.ProblemType;

public abstract class ObjetiveFunction {
	
	private ProblemType typeProblem;
	private float weight;
	
	/**
	 * Represents an objective (fitness) function for the problem.
	 *
	 * <p>Concrete implementations must provide the {@link #Evaluation(State)}
	 * method which computes the objective value for a given {@link State}.</p>
	 */

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public ProblemType getTypeProblem() {
		return typeProblem;
	}

	public void setTypeProblem(ProblemType typeProblem) {
		this.typeProblem = typeProblem;
	}

	/**
	 * Evaluate the provided {@code state} and return a numeric score.
	 *
	 * @param state the state to evaluate
	 * @return numeric objective value (higher or lower depends on the
	 *         problem type, see {@link ProblemType})
	 */
	public abstract Double Evaluation(State state);
}
