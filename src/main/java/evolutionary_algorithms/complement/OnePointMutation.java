package evolutionary_algorithms.complement;


import metaheurictics.strategy.Strategy;
import problem.definition.State;

/**
 * Clase que implementa el operador de mutación de un solo punto.
 * Este operador modifica un gen de un individuo de forma aleatoria.
 */
public class OnePointMutation extends Mutation {

	/**
	 * Realiza la mutación de un solo punto en un individuo.
	 * @param state El individuo a mutar.
	 * @param PM La probabilidad de mutación.
	 * @return El individuo mutado.
	 */
	@Override
	public State mutation(State state, double PM) {
		double probM = (double)(Math.random() * (double)(1));
		if(PM >= probM)
		{
			Object key = Strategy.getStrategy().getProblem().getCodification().getAleatoryKey();
			Object value = Strategy.getStrategy().getProblem().getCodification().getVariableAleatoryValue((Integer)key);
			state.getCode().set((Integer) key, value);
		}
		return state;
	}
}
