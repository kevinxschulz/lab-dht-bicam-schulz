package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import local_search.acceptation_type.AcceptType;
import local_search.candidate_type.CandidateType;
import local_search.complement.StrategyType;
import metaheuristics.strategy.Strategy;
import problem.definition.State;

/**
 * A generator that implements the Simulated Annealing algorithm.
 * It uses a probabilistic approach to accept worse solutions, allowing it to escape local optima.
 */
public class SimulatedAnnealing extends AbstractLocalSearchGenerator {

    public static final Double alpha = 0.93;
    public static Double tinitial;
    public static Double tfinal;
    public static int countIterationsT;
    private int countRept;

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

    /**
     * Constructs a new SimulatedAnnealing generator with default values.
     */
    public SimulatedAnnealing(){
        super(GeneratorType.SimulatedAnnealing);
        this.typeAcceptation = AcceptType.AcceptNotBadT;
        this.strategy = StrategyType.NORMAL;
        this.typeCandidate = CandidateType.RandomCandidate;
    }

    /**
     * Generates a new state by exploring the neighborhood of the current reference state.
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
    @Override
    public State generate(Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<State> neighborhood = new ArrayList<State>();
        neighborhood = Strategy.getStrategy().getProblem().getOperator().generatedNewState(referenceState, operatornumber);
        State statecandidate = candidatevalue.stateCandidate(referenceState, typeCandidate, strategy, operatornumber, neighborhood);
        return statecandidate;
    }

    /**
     * Updates the reference state based on the acceptance criteria and the current temperature.
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
    @Override
    public void updateReference(State stateCandidate, Integer countIterationsCurrent) throws IllegalArgumentException,
            SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        countRept = countIterationsT;
        super.updateReference(stateCandidate, countIterationsCurrent);
        if (countIterationsCurrent.equals(countIterationsT)) {
            tinitial = tinitial * alpha;
            countIterationsT = countIterationsT + countRept;
        }
    }
}
