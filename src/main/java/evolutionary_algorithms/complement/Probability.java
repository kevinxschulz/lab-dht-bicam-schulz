package evolutionary_algorithms.complement;

/**
 * Clase que representa una probabilidad asociada a una clave y un valor.
 * Se utiliza para almacenar las probabilidades en los algoritmos de estimación de distribución.
 */
public class Probability {
    private Object key;
    private Object value;
	private float probability;
	
	/**
	 * Obtiene la probabilidad.
	 * @return La probabilidad.
	 */
	public float getProbability() {
		return probability;
	}

	/**
	 * Establece la probabilidad.
	 * @param probability La probabilidad a establecer.
	 */
	public void setProbability(float probability) {
		this.probability = probability;
	}

	/**
	 * Obtiene la clave.
	 * @return La clave.
	 */
	public Object getKey() {
		return key;
	}

	/**
	 * Establece la clave.
	 * @param key La clave a establecer.
	 */
	public void setKey(Object key) {
		this.key = key;
	}

	/**
	 * Obtiene el valor.
	 * @return El valor.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Establece el valor.
	 * @param value El valor a establecer.
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
