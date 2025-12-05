package evolutionary_algorithms.complement;

/**
 * Enumeración que define los tipos de operadores de mutación disponibles.
 */
public enum MutationType {
	/**
	 * Mutación de dos puntos.
	 */
	TowPointsMutation, 
	/**
	 * Mutación de un solo punto.
	 */
	OnePointMutation, 
	/**
	 * Mutación AIO (All-In-One).
	 */
	AIOMutation;    
}
