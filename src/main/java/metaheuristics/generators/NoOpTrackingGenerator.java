package metaheuristics.generators;

import problem.definition.State;

/**
 * Abstract base class for generators that do not use weight, trace, or gender tracking features.
 * 
 * <p>This class provides default no-op implementations for tracking methods that are not
 * applicable to certain generator types (e.g., Particle, MultiGenerator).
 * Subclasses can focus on implementing core generation logic without duplicating
 * boilerplate methods.
 */
abstract class NoOpTrackingGenerator extends Generator {

    @Override
    public float getWeight() {
        return 0;
    }

    @Override
    public void setWeight(float weight) {
        // Not used in this generator type
    }

    @Override
    public float[] getTrace() {
        return null;
    }

    @Override
    public int[] getListCountBetterGender() {
        return null;
    }

    @Override
    public int[] getListCountGender() {
        return null;
    }

    @Override
    public int getCountGender() {
        return 0;
    }

    @Override
    public void setCountGender(int countGender) {
        // Not used in this generator type
    }

    @Override
    public int getCountBetterGender() {
        return 0;
    }

    @Override
    public void setCountBetterGender(int countBetterGender) {
        // Not used in this generator type
    }

    @Override
    public boolean awardUpdateREF(State stateCandidate) {
        return false;
    }
}
