package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StrategyType;
import metaheuristics.strategy.Strategy;

import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;



import factory_interface.IFFactoryAcceptCandidate;
import factory_method.FactoryAcceptCandidate;

/**
 * A generator that implements the Limit Threshold metaheuristic.
 * This metaheuristic accepts solutions that are not much worse than the current solution.
 */
public class LimitThreshold extends Generator{
	
	private CandidateValue candidatevalue;
	private AcceptType typeAcceptation;
	private StrategyType strategy;
	private CandidateType typeCandidate;
	private State stateReferenceLT;
    private IFFactoryAcceptCandidate ifacceptCandidate;
	private GeneratorType typeGenerator;
	//private List<State> listStateReference = new ArrayList<State>(); // lista de estados referencias por los que va pasando el algoritmo
	private float weight;
	
	//problemas dinamicos
	private int countGender = 0;
	private int countBetterGender = 0;
	private int[] listCountBetterGender = new int[10];
	private int[] listCountGender = new int[10];
	private float[] listTrace = new float[1200000];
	
	/**
	 * Gets the type of the generator.
	 * @return The type of the generator.
	 */
	public GeneratorType getTypeGenerator() {
		return typeGenerator;
	}

	/**
	 * Sets the type of the generator.
	 * @param typeGenerator The type of the generator.
	 */
	public void setTypeGenerator(GeneratorType typeGenerator) {
		this.typeGenerator = typeGenerator;
	}

	/**
	 * Constructs a new LimitThreshold generator with default values.
	 */
	public LimitThreshold() {
		super();
		this.typeAcceptation = AcceptType.AcceptNotBadU;
		this.strategy = StrategyType.NORMAL;


		Problem problem = Strategy.getStrategy().getProblem();

		if(problem.getTypeProblem().equals(ProblemType.Maximizar)) {
			this.typeCandidate = CandidateType.GreaterCandidate;
		}
		else{
			this.typeCandidate = CandidateType.SmallerCandidate;
		}

		this.candidatevalue = new CandidateValue();
		this.typeGenerator = GeneratorType.LimitThreshold;
		this.weight = (float) 50.0;
		listTrace[0] = weight;
		listCountBetterGender[0] = 0;
		listCountGender[0] = 0;

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
	public State generate(Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<State> neighborhood = Strategy.getStrategy().getProblem().getOperator().generatedNewState(stateReferenceLT, operatornumber);
	    State statecandidate = candidatevalue.stateCandidate(stateReferenceLT, typeCandidate, strategy, operatornumber, neighborhood);
	    return statecandidate;
	}

	/**
	 * Updates the reference state based on the acceptance criteria.
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
	public void updateReference(State stateCandidate, Integer countIterationsCurrent) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ifacceptCandidate = new FactoryAcceptCandidate();
		AcceptableCandidate candidate = ifacceptCandidate.createAcceptCandidate(typeAcceptation);
		Boolean accept = candidate.acceptCandidate(stateReferenceLT , stateCandidate);
		if(accept.equals(true)){
			//listStateReference.add(stateCandidate);
			stateReferenceLT = stateCandidate;
		}
		//else listStateReference.add(stateReferenceHC);
	}
	

	/**
	 * Gets the reference state.
	 * @return The reference state.
	 */
	@Override
	public State getReference() {
		return stateReferenceLT;
	}

	/**
	 * Sets the reference state.
	 * @param stateRef The reference state.
	 */
	public void setStateRef(State stateRef) {
		this.stateReferenceLT = stateRef;
	}

	/**
	 * Sets the initial reference state.
	 * @param stateInitialRef The initial reference state.
	 */
	@Override
	public void setInitialReference(State stateInitialRef) {
		this.stateReferenceLT = stateInitialRef;
	}


	/**
	 * Gets the type of the generator.
	 * @return The type of the generator.
	 */
	@Override
	public GeneratorType getType() {
		return this.typeGenerator;
	}

	/**
	 * Gets the list of reference states.
	 * @return The list of reference states.
	 */
	@Override
	public List<State> getReferenceList() {
		return null;
	}

	/**
	 * Gets the list of son states.
	 * @return The list of son states.
	 */
	@Override
	public List<State> getSonList() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the type of the candidate.
	 * @param typeCandidate The type of the candidate.
	 */
	public void setTypeCandidate(CandidateType typeCandidate){
		this.typeCandidate = typeCandidate;
	}

	/**
	 * Awards the update of the reference state.
	 * @param stateCandidate The candidate state.
	 * @return True if the update is awarded, false otherwise.
	 */
	@Override
	public boolean awardUpdateREF(State stateCandidate) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Gets the weight of the generator.
	 * @return The weight of the generator.
	 */
	public float getWeight() {
		// TODO Auto-generated method stub
		return this.weight;
	}

	/**
	 * Sets the weight of the generator.
	 * @param weight The weight of the generator.
	 */
	@Override
	public void setWeight(float weight) {
		// TODO Auto-generated method stub
		this.weight = weight;
	}


	/**
	 * Gets the list of count of better gender.
	 * @return the list of count of better gender.
	 */
	@Override
	public int[] getListCountBetterGender() {
		// TODO Auto-generated method stub
		return this.listCountBetterGender;
	}

	/**
	 * Gets the list of count of gender.
	 * @return the list of count of gender.
	 */
	@Override
	public int[] getListCountGender() {
		// TODO Auto-generated method stub
		return this.listCountGender;
	}

	/**
	 * Gets the trace of the generator.
	 * @return The trace of the generator.
	 */
	@Override
	public float[] getTrace() {
		// TODO Auto-generated method stub
		return this.listTrace;
	}

	@Override
	public int getCountGender() {
		return countGender;
	}

	@Override
	public void setCountGender(int countGender) {
		this.countGender = countGender;
	}

	@Override
	public int getCountBetterGender() {
		return countBetterGender;
	}

	@Override
	public void setCountBetterGender(int countBetterGender) {
		this.countBetterGender = countBetterGender;
	}

}
