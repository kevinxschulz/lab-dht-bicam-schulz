package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import local_search.candidate_type.CandidateType;
import metaheuristics.strategy.Strategy;

import problem.definition.State;
import problem.definition.Problem.ProblemType;

/**
 * A generator that implements the Hill Climbing with Restart algorithm.
 * It performs a series of Hill Climbing searches, restarting from a random position
 * when a local optimum is reached.
 */
public class HillClimbingRestart extends AbstractLocalSearchGenerator{

	public static int count;
	public static int countCurrent;
	private List<State> listRef = new ArrayList<State>();

	/**
	 * Constructs a new HillClimbingRestart generator with default values.
	 */
	public HillClimbingRestart() {
		super(GeneratorType.HillClimbingRestart);
//		countIterations = Strategy.getStrategy().getCountCurrent();
//		countSame = 1;
		countCurrent = count;
		if (Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar)) {
			this.typeCandidate = CandidateType.GreaterCandidate;
		} else {
			this.typeCandidate = CandidateType.SmallerCandidate;
		}
	}


	/**
	 * Generates a new state. If the restart condition is met, it restarts the search from a new random state.
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
	public State generate (Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//ArrayList<State>list=new ArrayList<State>();
		State statecandidate = new State();
		if(count == Strategy.getStrategy().getCountCurrent()){
			State stateR = new State(referenceState);
			listRef.add(stateR);
			referenceState = Strategy.getStrategy().getProblem().getOperator().generateRandomState(1).get(0);
			Strategy.getStrategy().getProblem().Evaluate(referenceState);
			count = count + countCurrent;
		}
		List<State> neighborhood = Strategy.getStrategy().getProblem().getOperator().generatedNewState(referenceState, operatornumber);
		statecandidate = candidatevalue.stateCandidate(referenceState, typeCandidate, strategy, operatornumber, neighborhood);
		//list.add(statecandidate);
		return statecandidate;
	}

	/*public State generate2(Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		State statecandidate = new State();
		countIterations = Strategy.getStrategy().getCountCurrent();
		if (countIterations>0){
			if(Strategy.getStrategy().Statistics.getbestListStates().get(countIterations)==Strategy.getStrategy().Statistics.getbestListStates().get(countIterations-1)){
				countSame++;
				if(countSame == count-1){
					State stateR = new State(referenceState);
					listRef.add(stateR);
					referenceState = Strategy.getStrategy().getProblem().getOperator().generateRandomState(1).get(0);
				}
			}
			else
				countSame = 1;
		}
		List<State> neighborhood = Strategy.getStrategy().getProblem().getOperator().generatedNewState(referenceState, operatornumber);
		statecandidate = candidatevalue.stateCandidate(referenceState, typeCandidate, strategy, operatornumber, neighborhood);
		return statecandidate;
	}
*/
	
}
