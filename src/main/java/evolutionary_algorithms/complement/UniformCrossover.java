package evolutionary_algorithms.complement;

import metaheurictics.strategy.Strategy;

import problem.definition.State;

/**
 * Clase que implementa el operador de cruce uniforme.
 * Este operador de cruce combina dos padres para crear un nuevo individuo.
 */
public class UniformCrossover extends Crossover {
	
	/**
	 * Genera una máscara binaria aleatoria.
	 * @param length La longitud de la máscara.
	 * @return Un arreglo de enteros que representa la máscara.
	 */
	public int[] mascara(int length){
		int[] mascara = new int[length];
		for (int i = 0; i < mascara.length; i++) {
			int value = (int)(Math.random() * (int)(2));
			mascara[0] = value;
		}
		return mascara;
	}	
    
	/**
	 * Realiza el cruce uniforme entre dos padres.
	 * @param father1 El primer padre.
	 * @param father2 El segundo padre.
	 * @param PC La probabilidad de cruce (no se utiliza directamente en esta implementación,
	 *           ya que el cruce uniforme suele aplicar la combinación en cada gen con una probabilidad fija).
	 * @return El nuevo individuo resultante del cruce.
	 */
	@Override
	public State crossover(State father1, State father2, double PC) {
		Object value = new Object();
		State state = (State) father1.getCopy();
		int[] mascara = mascara(father1.getCode().size());
   		for (int k = 0; k < mascara.length; k++) {
   			if(mascara[k] == 1){
   				value = father1.getCode().get(k);
   				state.getCode().set(k, value);
   			}
   			else{
   				if(mascara[k] == 0){
   					value = father2.getCode().get(k);  
   					state.getCode().set(k, value);
   				}
   			}
		}
		return state;
	}
}
