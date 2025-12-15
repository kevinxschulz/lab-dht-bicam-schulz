package metaheuristics.generators;

import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StrategyType;
import local_search.acceptation_type.AcceptType;
import metaheuristics.strategy.Strategy;
import problem.definition.Problem.ProblemType;

/**
 * Specialized Hill Climbing generator that determines the candidate type
 * (Greater/Smaller) based on problem type (Maximize/Minimize).
 * Extends the common local search generator to avoid code duplication.
 */
abstract class AbstractHillClimbingGenerator extends AbstractLocalSearchGenerator {

    protected AbstractHillClimbingGenerator(GeneratorType generatorType) {
        super(generatorType);
        this.typeAcceptation = AcceptType.AcceptBest;
        this.strategy = StrategyType.NORMAL;
        if (Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar)) {
            this.typeCandidate = CandidateType.GreaterCandidate;
        } else {
            this.typeCandidate = CandidateType.SmallerCandidate;
        }
        this.candidatevalue = new CandidateValue();
    }
}
