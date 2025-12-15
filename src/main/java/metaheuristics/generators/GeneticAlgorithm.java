package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import metaheuristics.strategy.Strategy;

import problem.definition.State;
import problem.definition.Problem.ProblemType;

import evolutionary_algorithms.complement.Crossover;
import evolutionary_algorithms.complement.CrossoverType;
import evolutionary_algorithms.complement.FatherSelection;
import evolutionary_algorithms.complement.Mutation;
import evolutionary_algorithms.complement.MutationType;
import evolutionary_algorithms.complement.Replace;
import evolutionary_algorithms.complement.ReplaceType;
import evolutionary_algorithms.complement.SelectionType;
import factory_interface.IFFactoryCrossover;
import factory_interface.IFFactoryFatherSelection;
import factory_interface.IFFactoryMutation;
import factory_interface.IFFactoryReplace;
import factory_method.FactoryCrossover;
import factory_method.FactoryFatherSelection;
import factory_method.FactoryMutation;
import factory_method.FactoryReplace;

/**
 * Genetic Algorithm (GA) generator.
 *
 * <p>Implements standard GA operations (selection, crossover, mutation)
 * using the pluggable factory-provided operators. Produces offspring
 * from selected parents and applies the configured replacement strategy.
 */
public class GeneticAlgorithm extends AbstractPopulationBasedGenerator {

	private IFFactoryFatherSelection iffatherselection;
	private IFFactoryCrossover iffactorycrossover;
	private IFFactoryMutation iffactorymutation;
	private IFFactoryReplace iffreplace;
	
	public static final MutationType mutationType = MutationType.OnePointMutation;
	public static final CrossoverType crossoverType = CrossoverType.UniformCrossover;
	public static final ReplaceType replaceType = ReplaceType.GenerationalReplace;
	public static final SelectionType selectionType = SelectionType.TruncationSelection;
	public static final double PC = 0.8;
	public static final double PM = 0.1;
	
    public GeneticAlgorithm() {
		super(GeneratorType.GeneticAlgorithm);
		this.referenceList = initializeReferenceList();
	}
    
	@Override
	public State generate(Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	
		//******************selection*******************************
		iffatherselection = new FactoryFatherSelection();
    	FatherSelection selection = iffatherselection.createSelectFather(selectionType);
    	List<State> fathers = selection.selection(this.referenceList, truncation);
    	int pos1 = (int)(Math.random() * fathers.size());
    	int pos2 = (int)(Math.random() * fathers.size());
    	
    	State auxState1 = (State) Strategy.getStrategy().getProblem().getState().getCopy();
    	auxState1.setCode(new ArrayList<Object>(fathers.get(pos1).getCode()));
    	auxState1.setEvaluation(fathers.get(pos1).getEvaluation());
    	auxState1.setNumber(fathers.get(pos1).getNumber());
    	auxState1.setTypeGenerator(fathers.get(pos1).getTypeGenerator());
    	
    	State auxState2 = (State) Strategy.getStrategy().getProblem().getState().getCopy();
    	auxState2.setCode(new ArrayList<Object>(fathers.get(pos2).getCode()));
    	auxState2.setEvaluation(fathers.get(pos2).getEvaluation());
    	auxState2.setNumber(fathers.get(pos2).getNumber());
    	auxState2.setTypeGenerator(fathers.get(pos2).getTypeGenerator());
    	
	    //**********cruzamiento*************************************
	    iffactorycrossover = new FactoryCrossover();
    	Crossover crossover = iffactorycrossover.createCrossover(crossoverType);
    	auxState1 = crossover.crossover(auxState1, auxState2, PC);
	    
    	//**********mutacion******************************************** 	
    	iffactorymutation = new FactoryMutation();
    	Mutation mutation =iffactorymutation.createMutation(mutationType);
    	auxState1 = mutation.mutation(auxState1, PM);
    	//list.add(auxState1);
    	
    	return auxState1;
	}
    


	@Override
	public void updateReference(State stateCandidate, Integer countIterationsCurrent) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,	NoSuchMethodException {
		iffreplace = new FactoryReplace();
		Replace replace = iffreplace.createReplace(replaceType);
		referenceList = replace.replace(stateCandidate, referenceList);
	}
	


}
