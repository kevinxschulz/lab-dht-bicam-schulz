package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import factory_interface.IFFactoryAcceptCandidate;
import factory_method.FactoryAcceptCandidate;
import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;
import metaheuristics.strategy.Strategy;
import problem.definition.Problem;
import problem.definition.State;


/**
 * Multi-case Simulated Annealing generator.
 *
 * <p>Supports multi-case acceptance criteria and temperature scheduling
 * for multi-objective simulated annealing variations.
 */
public class MultiCaseSimulatedAnnealing extends AbstractLocalSearchGenerator {

	public static final Double alpha = 0.95;
    public static Double tinitial;
    public static Double tfinal;
    public static int countIterationsT;
    private int countRept;

	public MultiCaseSimulatedAnnealing(){
    	super(GeneratorType.MultiCaseSimulatedAnnealing);
    	this.typeAcceptation = AcceptType.AcceptMulticase;
    }

	@Override
	public State generate(Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<State> neighborhood = new ArrayList<State>();
		Problem problem = Strategy.getStrategy().getProblem();
		neighborhood = problem.getOperator().generatedNewState(referenceState, operatornumber);
	    State statecandidate = candidatevalue.stateCandidate(referenceState, typeCandidate, strategy, operatornumber, neighborhood);
	    return statecandidate;
	}

	@Override
	public void updateReference(State stateCandidate, Integer countIterationsCurrent)throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		countRept = countIterationsT;
		ifacceptCandidate = new FactoryAcceptCandidate();
		AcceptableCandidate candidate = ifacceptCandidate.createAcceptCandidate(typeAcceptation);
		Boolean accept = candidate.acceptCandidate(referenceState, stateCandidate);
		if(accept.equals(true))
		  referenceState = stateCandidate.clone();
		if(countIterationsCurrent.equals(countIterationsT)){
			tinitial = tinitial * alpha;
			//Variante Fast MOSA
			//tinitial = tinitial/(1 + countIterationsCurrent);
			
			//Variante Two-Stage Annealing MC-MOSA
			/*if(countIterationsCurrent/2 < countIterationsT){
				tinitial = tinitial * alpha;
			}
			else{
				tinitial = tinitial/(1 + countIterationsCurrent);
			}*/
			System.out.println("La T:" + tinitial);
			countIterationsT = countIterationsT + countRept;
			System.out.println("La Cant es: " + countIterationsT);
		}
		getReferenceList();
	}
}
