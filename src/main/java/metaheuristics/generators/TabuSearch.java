package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StrategyType;
import local_search.complement.TabuSolutions;
import metaheuristics.strategy.Strategy;

import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;



import factory_interface.IFFactoryAcceptCandidate;
import factory_method.FactoryAcceptCandidate;

/**
 * A generator that implements the Tabu Search algorithm.
 * It uses a tabu list to avoid previously visited solutions and escape local optima.
 */
public class TabuSearch extends Generator {

	private CandidateValue candidatevalue;
	private AcceptType typeAcceptation;
	private StrategyType strategy;
	private CandidateType typeCandidate;
	private State stateReferenceTS;
    private IFFactoryAcceptCandidate ifacceptCandidate;
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
	 * Constructs a new TabuSearch generator with default values.
	 */
	public TabuSearch() {
    	super();
		this.typeAcceptation = AcceptType.AcceptAnyone;
		this.strategy = StrategyType.TABU;
		
		
		Problem problem = Strategy.getStrategy().getProblem();

		if(problem.getTypeProblem().equals(ProblemType.Maximizar)) {
			this.typeCandidate = CandidateType.GreaterCandidate;
		}
		else{
			this.typeCandidate = CandidateType.SmallerCandidate;
		}

		this.candidatevalue = new CandidateValue();
		this.typeGenerator = GeneratorType.TabuSearch;
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
		//ArrayList<State>list=new ArrayList<State>();
		List<State> neighborhood = new ArrayList<State>();
		neighborhood = Strategy.getStrategy().getProblem().getOperator().generatedNewState(stateReferenceTS, operatornumber);
	    State statecandidate = candidatevalue.stateCandidate(stateReferenceTS, typeCandidate, strategy, operatornumber, neighborhood);
	   // list.add(statecandidate);
	    return statecandidate;
	}

	/**
	 * Gets the reference state.
	 * @return The reference state.
	 */
	@Override
	public State getReference() {
		return stateReferenceTS;
	}

	/**
	 * Sets the initial reference state.
	 * @param stateInitialRef The initial reference state.
	 */
	@Override
	public void setInitialReference(State stateInitialRef) {
		this.stateReferenceTS = stateInitialRef;
	}

	/**
	 * Sets the reference state.
	 * @param stateRef The reference state.
	 */
	public void setStateRef(State stateRef) {
		this.stateReferenceTS = stateRef;
	}

	/**
	 * Updates the reference state and the tabu list.
	 * If the candidate is accepted, it becomes the new reference state.
	 * If the strategy is TABU, the new state is added to the tabu list to avoid revisiting it.
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
		ifacceptCandidate = new FactoryAcceptCandidate();
		AcceptableCandidate candidate = ifacceptCandidate.createAcceptCandidate(typeAcceptation);
		Boolean acept = candidate.acceptCandidate(stateReferenceTS, stateCandidate);
		if(acept.equals(true))
			stateReferenceTS = stateCandidate;

		if (strategy.equals(StrategyType.TABU) && acept.equals(true)) {
			if (TabuSolutions.listTabu.size() < TabuSolutions.maxelements) {
				Boolean find = false;
				int count = 0;
				while ((TabuSolutions.listTabu.size() > count) && (find.equals(false))) {
					if ((TabuSolutions.listTabu.get(count).Comparator(stateCandidate))==true) {
						find = true;
					}
					count++;
				}
				if (find.equals(false)) {
					TabuSolutions.listTabu.add(stateCandidate);
				}
			} else {
				TabuSolutions.listTabu.remove(0);
				Boolean find = false;
				int count = 0;
				while (TabuSolutions.listTabu.size() > count && find.equals(false)) {
					if ((TabuSolutions.listTabu.get(count).Comparator(stateCandidate))==true) {
						find = true;
					}
					count++;
				}
				if (find.equals(false)) {
					TabuSolutions.listTabu.add(stateCandidate);
				}
			}
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
		listStateReference.add(stateReferenceTS);
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

}



