package metaheuristics.generators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import local_search.acceptation_type.AcceptType;
import local_search.candidate_type.CandidateType;
import local_search.complement.StrategyType;
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
