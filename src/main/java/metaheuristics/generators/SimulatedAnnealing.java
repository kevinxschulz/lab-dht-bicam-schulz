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

import factory_interface.IFFactoryAcceptCandidate;
import factory_method.FactoryAcceptCandidate;

/**
 * A generator that implements the Simulated Annealing algorithm.
 * It uses a probabilistic approach to accept worse solutions, allowing it to escape local optima.
 */
public class SimulatedAnnealing extends Generator {

	private CandidateValue candidatevalue;
	private AcceptType typeAcceptation;
	private StrategyType strategy;
	private CandidateType typeCandidate;
	private State stateReferenceSA;
    private IFFactoryAcceptCandidate ifacceptCandidate;
    public static final Double alpha = 0.93;
    public static Double tinitial;
    public static Double tfinal;
    public static int countIterationsT;
    private int countRept;
    private GeneratorType typeGenerator;
    private List<State> listStateReference = new ArrayList<State>();
    private float weight;

	//problemas dinamicos
    public static int countGender = 0;
    public static int countBetterGender = 0;
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
	 * Constructs a new SimulatedAnnealing generator with default values.
	 */
	public SimulatedAnnealing(){

    	super();
    	/*SimulatedAnnealing.alpha = 0.93;
    	SimulatedAnnealing.tinitial = 250.0;
    	SimulatedAnnealing.tfinal = 41.66;
    	SimulatedAnnealing.countIterationsT = 50;*/

    	this.typeAcceptation = AcceptType.AcceptNotBadT;
		this.strategy = StrategyType.NORMAL;
		this.typeCandidate = CandidateType.RandomCandidate;
		this.candidatevalue = new CandidateValue();
		this.typeGenerator = GeneratorType.SimulatedAnnealing;
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
		//<State>list=new ArrayList<State>();
		List<State> neighborhood = new ArrayList<State>();
		neighborhood = Strategy.getStrategy().getProblem().getOperator().generatedNewState(stateReferenceSA, operatornumber);
	    State statecandidate = candidatevalue.stateCandidate(stateReferenceSA, typeCandidate, strategy, operatornumber, neighborhood);
	   // list.add(statecandidate);
	    return statecandidate;
	}

	/**
	 * Gets the reference state.
	 * @return The reference state.
	 */
	@Override
	public State getReference() {
		return stateReferenceSA;
	}

	/**
	 * Sets the reference state.
	 * @param stateRef The reference state.
	 */
	public void setStateRef(State stateRef) {
		this.stateReferenceSA = stateRef;
	}

	/**
	 * Sets the initial reference state.
	 * @param stateInitialRef The initial reference state.
	 */
	@Override
	public void setInitialReference(State stateInitialRef) {
		this.stateReferenceSA = stateInitialRef;
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
	public void updateReference(State stateCandidate, Integer countIterationsCurrent)throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		countRept = countIterationsT;
		ifacceptCandidate = new FactoryAcceptCandidate();
		AcceptableCandidate candidate = ifacceptCandidate.createAcceptCandidate(typeAcceptation);
		Boolean accept = candidate.acceptCandidate(stateReferenceSA, stateCandidate);
		if(accept.equals(true))
		  stateReferenceSA = stateCandidate;
		if(countIterationsCurrent.equals(countIterationsT)){
			tinitial = tinitial * alpha;
			countIterationsT = countIterationsT + countRept;
		}
//		getReferenceList();
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
		listStateReference.add(stateReferenceSA);
		return listStateReference;
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

}
