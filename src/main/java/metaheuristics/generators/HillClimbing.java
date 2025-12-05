package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StrategyType;
import metaheuristics.strategy.Strategy;

import problem.definition.State;
import problem.definition.Problem.ProblemType;



import factory_interface.IFFactoryAcceptCandidate;
import factory_method.FactoryAcceptCandidate;

/**
 * A generator that implements the Hill Climbing algorithm.
 * It explores the search space by iteratively moving to a better neighbor solution.
 */
public class HillClimbing extends Generator{

    protected CandidateValue candidatevalue;
	protected AcceptType typeAcceptation;
	protected StrategyType strategy;
	protected CandidateType typeCandidate;
	protected State stateReferenceHC;
	protected IFFactoryAcceptCandidate ifacceptCandidate;
	protected GeneratorType Generatortype;
	protected List<State> listStateReference = new ArrayList<State>(); 
	protected float weight;
	
	//problemas dinamicos
	public static int countGender = 0;
	public static int countBetterGender = 0;
	private int[] listCountBetterGender = new int[10];
	private int[] listCountGender = new int[10];
	private float[] listTrace = new float[1200000];
	
	/**
	 * Constructs a new HillClimbing generator with default values.
	 */
	public HillClimbing() {
		super();
		this.typeAcceptation = AcceptType.AcceptBest;
		this.strategy = StrategyType.NORMAL;
		if(Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar)) {
			this.typeCandidate = CandidateType.GreaterCandidate;
		}
		else{
			this.typeCandidate = CandidateType.SmallerCandidate;
		}
		this.candidatevalue = new CandidateValue();
		this.Generatortype = GeneratorType.HillClimbing;
		this.weight = 50;
		listTrace[0] = this.weight;
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
		List<State> neighborhood = new ArrayList<State>();
		neighborhood = Strategy.getStrategy().getProblem().getOperator().generatedNewState(stateReferenceHC, operatornumber);
	    State statecandidate = candidatevalue.stateCandidate(stateReferenceHC, typeCandidate, strategy, operatornumber, neighborhood);
	  
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
		Boolean accept = candidate.acceptCandidate(stateReferenceHC, stateCandidate);
		if(accept.equals(true))
		  stateReferenceHC = stateCandidate;
//		getReferenceList();
	}
	
	/**
	 * Gets the list of reference states.
	 * @return The list of reference states.
	 */
	@Override
	public List<State> getReferenceList() {
		listStateReference.add(stateReferenceHC);
		return listStateReference;
	}

	/**
	 * Gets the reference state.
	 * @return The reference state.
	 */
	@Override
	public State getReference() {
		return stateReferenceHC;
	}

	/**
	 * Sets the reference state.
	 * @param stateRef The reference state.
	 */
	public void setStateRef(State stateRef) {
		this.stateReferenceHC = stateRef;
	}

	/**
	 * Sets the initial reference state.
	 * @param stateInitialRef The initial reference state.
	 */
	@Override
	public void setInitialReference(State stateInitialRef) {
		this.stateReferenceHC = stateInitialRef;
	}

	/**
	 * Gets the type of the generator.
	 * @return The generator type.
	 */
	public GeneratorType getGeneratorType() {
		return Generatortype;
	}

	/**
	 * Sets the type of the generator.
	 * @param Generatortype The generator type.
	 */
	public void setGeneratorType(GeneratorType Generatortype) {
		this.Generatortype = Generatortype;
	}

	/**
	 * Gets the type of the generator.
	 * @return The generator type.
	 */
	@Override
	public GeneratorType getType() {
		return this.Generatortype;
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
	@Override
	public float getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Sets the weight of the generator.
	 * @param weight The weight of the generator.
	 */
	@Override
	public void setWeight(float weight) {
		// TODO Auto-generated method stub
		
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
}
