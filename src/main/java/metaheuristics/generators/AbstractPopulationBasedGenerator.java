package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import metaheuristics.strategy.Strategy;
import problem.definition.State;
import problem.definition.Problem.ProblemType;

/**
 * Shared scaffolding for population-based metaheuristic generators to avoid
 * duplicated code between specific algorithms (GA, ES, DEA, etc.).
 * 
 * <p>This abstract class provides common functionality for:
 * <ul>
 *   <li>Managing a population (reference list) of states</li>
 *   <li>Tracking dynamic problem metrics (gender counts, weights, traces)</li>
 *   <li>Finding the best reference state based on problem type</li>
 *   <li>Initializing reference lists from other generators</li>
 * </ul>
 */
abstract class AbstractPopulationBasedGenerator extends Generator {

    protected List<State> referenceList = new ArrayList<>();
    protected State bestReference;
    protected GeneratorType generatorType;
    
    // Static counters shared across population-based generators
    public static int countRef = 0;
    public static int truncation;
    
    // Dynamic problem tracking
    protected float weight = 50f;
    protected int countGender = 0;
    protected int countBetterGender = 0;
    private int[] listCountBetterGender = new int[10];
    private int[] listCountGender = new int[10];
    private float[] listTrace = new float[1200000];

    protected AbstractPopulationBasedGenerator(GeneratorType generatorType) {
        this.generatorType = generatorType;
        this.weight = 50f;
        listTrace[0] = this.weight;
        listCountBetterGender[0] = 0;
        listCountGender[0] = 0;
    }

    @Override
    public State getReference() {
        if (referenceList.isEmpty()) {
            return bestReference;
        }
        
        bestReference = referenceList.get(0);
        ProblemType problemType = Strategy.getStrategy().getProblem().getTypeProblem();
        
        if (problemType.equals(ProblemType.Maximizar)) {
            for (int i = 1; i < referenceList.size(); i++) {
                if (bestReference.getEvaluation().get(0) < referenceList.get(i).getEvaluation().get(0)) {
                    bestReference = referenceList.get(i);
                }
            }
        } else {
            for (int i = 1; i < referenceList.size(); i++) {
                if (bestReference.getEvaluation().get(0) > referenceList.get(i).getEvaluation().get(0)) {
                    bestReference = referenceList.get(i);
                }
            }
        }
        return bestReference;
    }

    @Override
    public List<State> getReferenceList() {
        List<State> copy = new ArrayList<>();
        for (State state : referenceList) {
            copy.add(state);
        }
        return copy;
    }

    @Override
    public void setInitialReference(State stateInitialRef) {
        this.bestReference = stateInitialRef;
    }

    @Override
    public GeneratorType getType() {
        return this.generatorType;
    }

    @Override
    public List<State> getSonList() {
        return null;
    }

    @Override
    public boolean awardUpdateREF(State stateCandidate) {
        return false;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public int[] getListCountBetterGender() {
        return this.listCountBetterGender;
    }

    @Override
    public int[] getListCountGender() {
        return this.listCountGender;
    }

    @Override
    public float[] getTrace() {
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

    /**
     * Initialize the reference list from existing generators in the strategy.
     * If this generator's list is empty, it will be populated with states from
     * {@link AbstractLocalSearchGenerator#listStateReference}.
     * 
     * @return the initialized reference list
     */
    protected List<State> initializeReferenceList() {
        Boolean found = false;
        List<String> keys = Strategy.getStrategy().getListKey();
        int count = 0;
        
        while (!found && Strategy.getStrategy().mapGenerators.size() > count) {
            if (keys.get(count).equals(this.generatorType.toString())) {
                GeneratorType keyGenerator = GeneratorType.valueOf(keys.get(count));
                AbstractPopulationBasedGenerator generator = 
                    (AbstractPopulationBasedGenerator) Strategy.getStrategy().mapGenerators.get(keyGenerator);
                
                if (generator.referenceList.isEmpty()) {
                    referenceList.addAll(AbstractLocalSearchGenerator.listStateReference);
                } else {
                    referenceList = generator.referenceList;
                }
                found = true;
            }
            count++;
        }
        return referenceList;
    }

    // Accessor methods for subclasses
    public List<State> getInternalReferenceList() {
        return referenceList;
    }

    public void setInternalReferenceList(List<State> referenceList) {
        this.referenceList = referenceList;
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    public void setGeneratorType(GeneratorType generatorType) {
        this.generatorType = generatorType;
    }
}
