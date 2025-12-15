package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import metaheuristics.strategy.Strategy;

import problem.definition.State;
import problem.definition.Problem.ProblemType;

import evolutionary_algorithms.complement.DistributionType;
import evolutionary_algorithms.complement.FatherSelection;
import evolutionary_algorithms.complement.Replace;
import evolutionary_algorithms.complement.ReplaceType;
import evolutionary_algorithms.complement.Sampling;
import evolutionary_algorithms.complement.SamplingType;
import evolutionary_algorithms.complement.SelectionType;
import factory_interface.IFFSampling;
import factory_interface.IFFactoryFatherSelection;
import factory_interface.IFFactoryReplace;
import factory_method.FactoryFatherSelection;
import factory_method.FactoryReplace;
import factory_method.FactorySampling;

public class DistributionEstimationAlgorithm extends Generator {
	/**
	 * Distribution Estimation Algorithm (DEA) generator.
	 *
	 * <p>This generator implements a distribution-estimation-based operator for
	 * producing candidate solutions. It keeps a reference list of states and
	 * supports sampling from a distribution built over selected parents (fathers).
	 * The class integrates with the strategy and factory interfaces defined in
	 * the project to perform father selection, sampling and replacement.
	 */

	private State stateReferenceDA;
	private List<State> referenceList = new ArrayList<State>(); 
	public static final List<State> sonList = new ArrayList<State>(); 
	private IFFactoryFatherSelection iffatherselection;
	private IFFSampling iffsampling;
	private IFFactoryReplace iffreplace;
	private DistributionType distributionType;
	private SamplingType Samplingtype;
	
//	private ReplaceType replaceType;
	public static final ReplaceType replaceType = ReplaceType.GenerationalReplace;
	public static final SelectionType selectionType = SelectionType.TruncationSelection;
	
	private GeneratorType generatorType;
	//private ProblemState candidate;
	public static int truncation;
	public static int countRef = 0;
	private float weight;
	
	//problemas dinamicos
	private int countGender = 0;
	private int countBetterGender = 0;
	private int[] listCountBetterGender = new int[10];
	private int[] listCountGender = new int[10];
	private float[] listTrace = new float[1200000];
	
	
	public DistributionEstimationAlgorithm() {
		super();
		this.referenceList = getListStateRef(); // llamada al m�todo que devuelve la lista. 
//		this.selectionType = SelectionType.Truncation;
		//this.replaceType = ReplaceType.Generational;
//		this.replaceType = ReplaceType.Smallest;
		this.generatorType = GeneratorType.DistributionEstimationAlgorithm;
		this.distributionType = DistributionType.Univariate;
		this.Samplingtype = SamplingType.ProbabilisticSampling;
		this.weight = 50;
		listTrace[0] = weight;
		listCountBetterGender[0] = 0;
		listCountGender[0] = 0;
	}
	
	public State MaxValue (List<State> listInd){
		/**
		 * Return the state with the maximum evaluation value (first objective) from the list.
		 *
		 * @param listInd list of candidate states (must be non-empty)
		 * @return a copy of the state that has the maximum value for the first evaluation
		 */
		State state = new State(listInd.get(0));
		double max = state.getEvaluation().get(0);
		for (int i = 1; i < listInd.size(); i++) {
			if(listInd.get(i).getEvaluation().get(0) > max){
				max = listInd.get(i).getEvaluation().get(0);
				state = new State(listInd.get(i));
			}
		}
		return state;
	}
	
	@Override
	public State generate(Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,	NoSuchMethodException {
		/**
		 * Generate a candidate state using the configured sampling operator.
		 *
		 * <p>The method selects fathers using the configured father selection
		 * strategy and then uses the sampling factory to create new candidates.
		 * If more than one candidate is sampled their first-objective evaluations
		 * are computed and the best one (MaxValue) is returned.
		 *
		 * @param operatornumber a parameter forwarded to the sampling operator
		 * @return the generated candidate state
		 * @throws ReflectiveOperationException if factory reflection fails
		 */
		//********************selection*****************************
		//ProblemState candidate = new ProblemState();
		/*State candidate = new State();//(State) Strategy.getStrategy().getProblem().getState().clone()
		List<State> fathers = new ArrayList<State>();
		fathers = getfathersList();
		//**************************distribution***********************************
		iffdistribution = new FactoryDistribution();
    	Distribution distribution = iffdistribution.createDistribution(distributionType);
    	List<Probability> probability = distribution.distribution(fathers);
		
    	//***************************muestreo*****************************************
    	iffsampling = new FactorySampling();
    	Sampling samplingG = iffsampling.createSampling(Samplingtype);
    	List<State> ind = samplingG.sampling(probability, referenceList.size());
    	sonList = ind;
    	candidate = MaxValue(ind);
       // this.candidate = candidate;
    	return candidate;*/	
    	
    	//***************************version 1.0
    	//ArrayList<State> listcandidate = new ArrayList<State>();//(State) Strategy.getStrategy().getProblem().getState().clone()
		
    	List<State> fathers = new ArrayList<State>();
		fathers = getfathersList();
		iffsampling = new FactorySampling();
    	Sampling samplingG = iffsampling.createSampling(Samplingtype);
    	List<State> ind = samplingG.sampling(fathers, operatornumber);
    	State candidate = null;
    	if(ind.size() > 1){
    		for (int i = 0; i < ind.size(); i++) {
    			double evaluation = Strategy.getStrategy().getProblem().getFunction().get(0).Evaluation(ind.get(i));
    			ArrayList<Double> listEval = new ArrayList<Double>();
    			listEval.add(evaluation);
    			ind.get(0).setEvaluation(listEval);
    		}
    		candidate = MaxValue(ind);
    	}
    	else{
    		candidate = ind.get(0);
    	}
		// sonList = ind; (left intentionally commented out)
    	//listcandidate.add(candidate);
       // this.candidate = candidate;
    	return candidate;
    	
    }
    		
	@Override
	public State getReference() {
		/**
		 * Return the current reference state according to the problem objective
		 * type (maximize or minimize). The method inspects the first objective
		 * value to find the best state in {@code referenceList}.
		 *
		 * @return the best reference state (first in list if tie)
		 */
		stateReferenceDA = referenceList.get(0);
		if(Strategy.getStrategy().getProblem().getTypeProblem().equals(ProblemType.Maximizar)){
			for (int i = 1; i < referenceList.size(); i++) {
				if(stateReferenceDA.getEvaluation().get(0) < referenceList.get(i).getEvaluation().get(0))
					stateReferenceDA = referenceList.get(i);
			}
		}
		else{
			for (int i = 1; i < referenceList.size(); i++) {
				if(stateReferenceDA.getEvaluation().get(0) > referenceList.get(i).getEvaluation().get(0))
					stateReferenceDA = referenceList.get(i);
			}
		}
		return stateReferenceDA;
	}

	@Override
	public List<State> getReferenceList() {
		/**
		 * Return a shallow copy of the reference list.
		 *
		 * @return a new list containing the same State references as {@code referenceList}
		 */
		List<State> ReferenceList = new ArrayList<State>();
		for (int i = 0; i < referenceList.size(); i++) {
			State value = referenceList.get(i);
			ReferenceList.add( value);
		}
		return ReferenceList;
	}

	@Override
	public GeneratorType getType() {
		return this.generatorType;
	}

	@Override
	public void setInitialReference(State stateInitialRef) {
		this.stateReferenceDA = stateInitialRef;
	}

	@Override
	public void updateReference(State stateCandidate, Integer countIterationsCurrent) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,	NoSuchMethodException {
		/**
		 * Update the reference list by applying the configured replacement
		 * strategy using the provided candidate.
		 *
		 * @param stateCandidate the candidate state to consider for replacement
		 * @param countIterationsCurrent current iteration counter (unused)
		 * @throws ReflectiveOperationException if replacement factory creation fails
		 */
		iffreplace = new FactoryReplace();
		Replace replace = iffreplace.createReplace(replaceType);
		referenceList = replace.replace(stateCandidate, referenceList);
	}
	
	public List<State> getListStateRef(){
		Boolean found = false;
		List<String> key = Strategy.getStrategy().getListKey();
		int count = 0;
		//if(Strategy.getStrategy().Statistics.getAllStates().size() == 0){
		/*if(RandomSearch.listStateReference.size() == 0){
			return this.referenceList = new ArrayList<State>();
		}*/
		while((found.equals(false)) && (Strategy.getStrategy().mapGenerators.size() > count)){
			if(key.get(count).equals(GeneratorType.DistributionEstimationAlgorithm.toString())){
				GeneratorType keyGenerator = GeneratorType.valueOf(String.valueOf(key.get(count)));
				DistributionEstimationAlgorithm generator = (DistributionEstimationAlgorithm)Strategy.getStrategy().mapGenerators.get(keyGenerator);
				if(generator.getListReference().isEmpty()){
					referenceList.addAll(AbstractLocalSearchGenerator.listStateReference);
					//for (int j = 1; j < Strategy.getStrategy().Statistics.getAllStates().size(); j++) {
//					for (int j = 1; j < RandomSearch.listStateReference.size(); j++) {
//						//if((Strategy.getStrategy().Statistics.getAllStates().get(j).getTypeGenerator().equals(GeneratorType.RandomSearch)) && (referenceList.size()!= countRef)){
//						if(referenceList.size() != countRef){
//							//State problemState = Strategy.getStrategy().Statistics.getAllStates().get(j);
//							referenceList.add(RandomSearch.listStateReference.get(j));
//						}
//					}  
				}
				else{
					referenceList = generator.getListReference();
				}
			    found = true;
			}
			count++;
		}
		return referenceList;
	}

	public List<State> getListReference() {
		return referenceList;
	}

	public void setListReference(List<State> listReference) {
		referenceList = listReference;
	}

	public GeneratorType getGeneratorType() {
		return generatorType;
	}

	public void setGeneratorType(GeneratorType generatorType) {
		this.generatorType = generatorType;
	}

	public List<State> getfathersList() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<State> refList = new ArrayList<State>(this.referenceList); 
    	iffatherselection = new FactoryFatherSelection();
    	FatherSelection selection = iffatherselection.createSelectFather(selectionType);
    	List<State> fathers = selection.selection(refList, truncation);
    	return fathers;
	}

	@Override
	public List<State> getSonList() {
		return sonList;
	}

	public boolean awardUpdateREF(State stateCandidate) {
		boolean find = false;
		int i = 0;
		while (find == false && i < this.referenceList.size()) {
			if(stateCandidate.equals(this.referenceList.get(i)))
				find = true;
			else i++;
		}
		return find;
	}

	@Override
	public float getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWeight(float weight) {
		// TODO Auto-generated method stub
		
	}

	public DistributionType getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(DistributionType distributionType) {
		this.distributionType = distributionType;
	}

	@Override
	public int[] getListCountBetterGender() {
		// TODO Auto-generated method stub
		return this.listCountBetterGender;
	}

	@Override
	public int[] getListCountGender() {
		// TODO Auto-generated method stub
		return this.listCountGender;
	}

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