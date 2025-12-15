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

public class DistributionEstimationAlgorithm extends AbstractPopulationBasedGenerator {
	/**
	 * Distribution Estimation Algorithm (DEA) generator.
	 *
	 * <p>This generator implements a distribution-estimation-based operator for
	 * producing candidate solutions. It keeps a reference list of states and
	 * supports sampling from a distribution built over selected parents (fathers).
	 * The class integrates with the strategy and factory interfaces defined in
	 * the project to perform father selection, sampling and replacement.
	 */

	public static final List<State> sonList = new ArrayList<State>(); 
	private IFFactoryFatherSelection iffatherselection;
	private IFFSampling iffsampling;
	private IFFactoryReplace iffreplace;
	private DistributionType distributionType;
	private SamplingType Samplingtype;
	
	public static final ReplaceType replaceType = ReplaceType.GenerationalReplace;
	public static final SelectionType selectionType = SelectionType.TruncationSelection;
	
	public DistributionEstimationAlgorithm() {
		super(GeneratorType.DistributionEstimationAlgorithm);
		this.referenceList = initializeReferenceList();
		this.distributionType = DistributionType.Univariate;
		this.Samplingtype = SamplingType.ProbabilisticSampling;
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

	public DistributionType getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(DistributionType distributionType) {
		this.distributionType = distributionType;
	}
}