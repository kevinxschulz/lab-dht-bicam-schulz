package evolutionary_algorithms.complement;

/**
 * Clase que representa un rango de probabilidad asociado a un objeto {@link Probability}.
 * Se utiliza para definir intervalos en la selección basada en probabilidades.
 */
public class Range {
   private Probability data;
   private float max;
   private float min;
   
   /**
    * Obtiene el objeto de probabilidad asociado al rango.
    * @return El objeto {@link Probability}.
    */
   public Probability getData() {
	  return data;
   }
   
   /**
    * Establece el objeto de probabilidad asociado al rango.
    * @param data El objeto {@link Probability} a establecer.
    */
   public void setData(Probability data) {
	  this.data = data;
   }
   
   /**
    * Obtiene el valor máximo del rango.
    * @return El valor máximo.
    */
   public float getMax() {
	  return max;
   }
   
   /**
    * Establece el valor máximo del rango.
    * @param max El valor máximo a establecer.
    */
   public void setMax(float max) {
	  this.max = max;
   }
   
   /**
    * Obtiene el valor mínimo del rango.
    * @return El valor mínimo.
    */
   public float getMin() {
	  return min;
   }
   
   /**
    * Establece el valor mínimo del rango.
    * @param min El valor mínimo a establecer.
    */
   public void setMin(float min) {
	  this.min = min;
   }
}
