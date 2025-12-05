package problem.definition;



import java.util.ArrayList;

import metaheuristics.generators.GeneratorType;

public class State {
	
	protected GeneratorType typeGenerator;
	protected ArrayList<Double> evaluation;
	protected int number;
	protected ArrayList<Object> code;
	


	/**
	 * Copy constructor. Creates a shallow copy of the given {@code ps} state.
	 *
	 * @param ps the state to copy
	 */
	public State(State ps) {
		typeGenerator = ps.getTypeGenerator();
		evaluation = ps.getEvaluation();
		number = ps.getNumber();
		code = new ArrayList<Object>(ps.getCode());
	}

	/**
	 * Construct a state with the provided code list.
	 *
	 * @param code list of encoded variable values
	 */
	public State(ArrayList<Object> code) {
		super();
		this.code = code;
	}

	/**
	 * Default constructor. Initializes an empty code list.
	 */
	public State() {
		code = new ArrayList<Object>();
	}

	public ArrayList<Object> getCode() {
		return code;
	}

	public void setCode(ArrayList<Object> listCode) {
		this.code = listCode;
	}

	public GeneratorType getTypeGenerator() {
		return typeGenerator;
	}

	public void setTypeGenerator(GeneratorType typeGenerator) {
		this.typeGenerator = typeGenerator;
	}

	public ArrayList<Double> getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(ArrayList<Double> evaluation) {
		this.evaluation = evaluation;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Returns this state object. Note: this implementation returns the
	 * same instance (no deep clone). Override for a proper clone when
	 * needed.
	 *
	 * @return this state instance
	 */
	public State clone() {
		return this;
	}

	/**
	 * Returns a shallow copy containing the same code list reference.
	 *
	 * @return a new {@link State} created from the current code list
	 */
	public Object getCopy() {
		return new State(this.getCode());
	}

	/**
	 * Compares this state with another by checking equality of the code lists.
	 *
	 * @param state the state to compare with
	 * @return {@code true} if both states have equal code lists
	 */
	public boolean Comparator(State state) {

		boolean result = false;
		if (state.getCode().equals(getCode())) {
			result = true;
		}
		return result;
	}

	/**
	 * Computes a simple Hamming-like distance between the code lists: the
	 * number of positions with different values.
	 *
	 * @param state the other state
	 * @return the number of differing positions
	 */
	public double Distance(State state) {
		double distancia = 0;
		for (int i = 0; i < state.getCode().size(); i++) {
			if (!(state.getCode().get(i).equals(this.getCode().get(i)))) {
				distancia++;
			}
		}
		return distancia;
	}
}
