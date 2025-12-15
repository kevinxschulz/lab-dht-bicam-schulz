package metaheuristics.generators;

import local_search.acceptation_type.AcceptType;
import local_search.candidate_type.CandidateType;
import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;

/**
 * A generator that implements the Limit Threshold metaheuristic.
 * This metaheuristic accepts solutions that are not much worse than the current solution.
 */
public class LimitThreshold extends AbstractLocalSearchGenerator {

	/**
	 * Constructs a new LimitThreshold generator with default values.
	 */
	public LimitThreshold() {
		super(GeneratorType.LimitThreshold);
		this.typeAcceptation = AcceptType.AcceptNotBadU;

		Problem problem = Strategy.getStrategy().getProblem();

		if(problem.getTypeProblem().equals(ProblemType.Maximizar)) {
			this.typeCandidate = CandidateType.GreaterCandidate;
		}
		else{
			this.typeCandidate = CandidateType.SmallerCandidate;
		}
	}

	/**
	 * Gets the type of the generator.
	 * @return The type of the generator.
	 */
	public GeneratorType getTypeGenerator() {
		return generatorType;
	}

	/**
	 * Sets the type of the generator.
	 * @param typeGenerator The type of the generator.
	 */
	public void setTypeGenerator(GeneratorType typeGenerator) {
		this.generatorType = typeGenerator;
	}

}
