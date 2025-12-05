package problem.extension;

import java.util.ArrayList;

import metaheuristics.strategy.Strategy;

import problem.definition.State;
import problem.definition.Problem.ProblemType;

/**
 * Weighted-factors (scalarization) solution method.
 *
 * <p>This evaluator computes a single scalar value by combining each
 * objective's (possibly inverted) evaluation with its configured weight.
 * The computed scalar is stored as a single-element evaluation vector in
 * the provided {@link State}.</p>
 */
public class FactoresPonderados extends SolutionMethod {

	/**
	 * Evaluate the provided state by computing a weighted sum of the
	 * problem's objective function values. If the overall problem or the
	 * objective has a minimization semantics, the value is inverted as
	 * required to produce a consistent (higher-is-better) scalar.
	 *
	 * @param state the state to evaluate; its evaluation vector will be set
	 *              to a single-element list containing the scalarized value
	 */
	@Override
	public void evaluationState(State state) {
		double eval = 0;       
		double tempWeight = 0;    
		ArrayList<Double> evaluation = new ArrayList<Double>(Strategy.getStrategy().getProblem().getFunction().size());
        
		for (int i = 0; i < Strategy.getStrategy().getProblem().getFunction().size(); i++) {

			tempWeight = 0;
			if(Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar)){
				if(Strategy.getStrategy().getProblem().getFunction().get(i).getTypeProblem().equals(ProblemType.Maximizar)){
					tempWeight = Strategy.getStrategy().getProblem().getFunction().get(i).Evaluation(state);
					tempWeight = tempWeight * Strategy.getStrategy().getProblem().getFunction().get(i).getWeight();
				}
				else{
					tempWeight = 1 - Strategy.getStrategy().getProblem().getFunction().get(i).Evaluation(state);
					tempWeight = tempWeight * Strategy.getStrategy().getProblem().getFunction().get(i).getWeight();
				}
			}
			else{
				if(Strategy.getStrategy().getProblem().getFunction().get(i).getTypeProblem().equals(ProblemType.Maximizar)){
					tempWeight = 1 - Strategy.getStrategy().getProblem().getFunction().get(i).Evaluation(state);
					tempWeight = tempWeight * Strategy.getStrategy().getProblem().getFunction().get(i).getWeight();
				}
				else{
					tempWeight = Strategy.getStrategy().getProblem().getFunction().get(i).Evaluation(state);
					tempWeight = tempWeight * Strategy.getStrategy().getProblem().getFunction().get(i).getWeight();
				}
			}
			eval += tempWeight;
		}
		evaluation.add(evaluation.size(), eval);
		state.setEvaluation(evaluation);
        
	}

}
