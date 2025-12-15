package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import factory_interface.IFFactoryAcceptCandidate;
import factory_method.FactoryAcceptCandidate;
import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StrategyType;
import problem.definition.State;

/**
 * Shared scaffolding for single-solution local search generators to avoid
 * duplicated plumbing between specific algorithms (Hill Climbing, SA, etc.).
 */
abstract class AbstractLocalSearchGenerator extends Generator {

    protected CandidateValue candidatevalue = new CandidateValue();
    protected AcceptType typeAcceptation = AcceptType.AcceptBest;
    protected StrategyType strategy = StrategyType.NORMAL;
    protected CandidateType typeCandidate = CandidateType.RandomCandidate;
    protected State referenceState;
    protected IFFactoryAcceptCandidate ifacceptCandidate;
    protected GeneratorType generatorType;
    protected List<State> listStateReference = new ArrayList<State>();
    protected float weight = 50f;

    public static int countGender = 0;
    public static int countBetterGender = 0;
    private int[] listCountBetterGender = new int[10];
    private int[] listCountGender = new int[10];
    private float[] listTrace = new float[1200000];

    protected AbstractLocalSearchGenerator(GeneratorType generatorType) {
        this.generatorType = generatorType;
        listTrace[0] = this.weight;
        listCountBetterGender[0] = 0;
        listCountGender[0] = 0;
    }

    @Override
    public void updateReference(State stateCandidate, Integer countIterationsCurrent)
            throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ifacceptCandidate = new FactoryAcceptCandidate();
        AcceptableCandidate candidate = ifacceptCandidate.createAcceptCandidate(typeAcceptation);
        Boolean accept = candidate.acceptCandidate(referenceState, stateCandidate);
        if (accept.equals(true)) {
            referenceState = stateCandidate;
        }
    }

    @Override
    public List<State> getReferenceList() {
        listStateReference.add(referenceState);
        return listStateReference;
    }

    @Override
    public State getReference() {
        return referenceState;
    }

    public void setStateRef(State stateRef) {
        this.referenceState = stateRef;
    }

    @Override
    public void setInitialReference(State stateInitialRef) {
        this.referenceState = stateInitialRef;
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    public void setGeneratorType(GeneratorType generatorType) {
        this.generatorType = generatorType;
    }

    @Override
    public GeneratorType getType() {
        return this.generatorType;
    }

    @Override
    public List<State> getSonList() {
        return null;
    }

    public void setTypeCandidate(CandidateType typeCandidate) {
        this.typeCandidate = typeCandidate;
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
}
