package problem.extension;

import java.util.ArrayList;

import metaheurictics.strategy.Strategy;

import problem.definition.ObjetiveFunction;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

/**
 * Pure multi-objective evaluation method. For each objective function defined
 * in the current problem, this evaluator computes a normalized value and
 * produces a vector of objective values stored in the state's evaluation.
 */
public class MultiObjetivoPuro extends SolutionMethod {

	/**
	 * Evaluate the given state for each objective function.
	 * <p>
	 * The method takes into account the overall problem type (maximize / minimize)
	 * and each objective's declared type. If necessary, it inverts the objective
	 * value so that the returned evaluations are consistent with the problem
	 * direction (higher = better when the problem is a maximization problem).
	 *
	 * @param state the state to evaluate; its evaluation vector will be set
	 */
	@Override
	public void evaluationState(State state) {
		double tempEval = -1;
		ArrayList<Double> evaluation = new ArrayList<Double>(Strategy.getStrategy().getProblem().getFunction().size());
		for (int i = 0; i < Strategy.getStrategy().getProblem().getFunction().size(); i++)
		{
			ObjetiveFunction objfunction = (ObjetiveFunction)Strategy.getStrategy().getProblem().getFunction().get(i);
			if(Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar)){
				if(objfunction.getTypeProblem().equals(ProblemType.Maximizar))
				{
					tempEval = objfunction.Evaluation(state);
				}
				else{
					tempEval = 1-objfunction.Evaluation(state);
				}
			}
			else{
				if(objfunction.getTypeProblem().equals(ProblemType.Maximizar))
				{
					tempEval = 1-objfunction.Evaluation(state);
				}
				else{
					tempEval = objfunction.Evaluation(state);
				}
			}
			evaluation.add(tempEval);
		}
		state.setEvaluation(evaluation);
	}

}
