package problem.extension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jxl.read.biff.BiffException;
import problem.definition.State;

/**
 * Utility class that provides common multi-objective quality metrics used to
 * evaluate approximation sets (e.g. Pareto fronts) produced by multi-objective
 * algorithms.
 *
 * <p>Implemented metrics include:
 * <ul>
 *   <li>TasaError: proportion of solutions in the current front that are not
 *       members of a reference (true) Pareto front.</li>
 *   <li>DistanciaGeneracional: generational distance from the current front
 *       to the reference front.</li>
 *   <li>Dispersion: spread/dispersion of solutions within a front.</li>
 * </ul>
 * </p>
 */
public class MetricasMultiobjetivo {

	/**
	 * Proportion of solutions in the current Pareto front that are not
	 * members of the reference (true) Pareto front.
	 *
	 * @param solutionsFPcurrent the current/approximated Pareto front
	 * @param solutionsFPtrue the reference (true) Pareto front
	 * @return fraction between 0 and 1 indicating the error rate
	 * @throws BiffException when reading reference data fails
	 * @throws IOException when an I/O error occurs
	 */
	public double TasaError(List<State> solutionsFPcurrent, List<State> solutionsFPtrue) throws BiffException, IOException{
		float tasaError = 0;
		for (int i = 0; i < solutionsFPcurrent.size() ; i++) { // frente de pareto actual
			State solutionVO = solutionsFPcurrent.get(i);
			if(!Contains(solutionVO, solutionsFPtrue)){ // no esta en el frente de pareto verdadero 
				tasaError++;
			}
		}
		double total = tasaError/solutionsFPcurrent.size();
		return total;
	}

	/**
	 * Generational distance: indicates how far elements of the current Pareto
	 * front are from the reference (true) Pareto front.
	 *
	 * @param solutionsFPcurrent the current/approximated Pareto front
	 * @param solutionsFPtrue the reference (true) Pareto front
	 * @return the generational distance (Euclidean-based) averaged over solutions
	 * @throws BiffException when reading reference data fails
	 * @throws IOException when an I/O error occurs
	 */
	public double DistanciaGeneracional(List<State> solutionsFPcurrent, List<State> solutionsFPtrue) throws BiffException, IOException{
		float min = 1000;
		float distancia = 0;
		float distanciaGeneracional = 0;
		for (int i = 0; i < solutionsFPcurrent.size();i++) {
			State solutionVO = solutionsFPcurrent.get(i);
			//Calculando la distancia euclideana entre solutionVO y el miembro m�s cercano del frente de Pareto verdadero
			min = 1000;
			for (int j = 0; j < solutionsFPtrue.size();j++) { 
				for (int j2 = 0; j2 < solutionVO.getEvaluation().size(); j2++) {
					State solutionFPV = solutionsFPtrue.get(j);
					// porq elevar la distancia al cuadrado
					distancia += (solutionVO.getEvaluation().get(j2) - solutionFPV.getEvaluation().get(j2))*  
							(solutionVO.getEvaluation().get(j2) - solutionFPV.getEvaluation().get(j2));
				}
				if(distancia < min){
					min = distancia;
				}
			}
			distanciaGeneracional += min;
		}
		double total = Math.sqrt(distanciaGeneracional)/solutionsFPcurrent.size();
		return total;
	}

	/**
	 * Computes the dispersion (spread) of the given set of solutions. The
	 * measure is the standard deviation of the nearest-neighbor distances
	 * among solutions.
	 *
	 * @param solutions list of solutions to measure
	 * @return dispersion value (standard deviation) or 0 if not enough samples
	 * @throws BiffException when reading external data fails
	 * @throws IOException when an I/O error occurs
	 */
	public double Dispersion(ArrayList<State> solutions) throws BiffException, IOException{
		LinkedList<Float> distancias = new LinkedList<Float>();
		float distancia = 0;
		float min = 1000;
		for (Iterator<State> iter = solutions.iterator(); iter.hasNext();) {
			State solutionVO = (State) iter.next();
			min = 1000;
			for (Iterator<State> iterator = solutions.iterator(); iterator.hasNext();) {
				State solVO = (State) iterator.next();
				for (int i = 0; i < solutionVO.getEvaluation().size(); i++) {
					if(!solutionVO.getEvaluation().equals(solVO.getEvaluation())){
						distancia += (solutionVO.getEvaluation().get(i)- solVO.getEvaluation().get(i));
					}}
				if(distancia < min){
					min = distancia;
				}
			}
			distancias.add(Float.valueOf(min));
		}
		//Calculando las media de las distancias 
		float sum = 0;
		for (Iterator<Float> iter = distancias.iterator(); iter.hasNext();) {
			Float dist = (Float) iter.next();
			sum += dist;
		}
		float media = sum/distancias.size();
		float sumDistancias = 0;
		for (Iterator<Float> iter = distancias.iterator(); iter.hasNext();) {
			Float dist = (Float) iter.next();
			sumDistancias += Math.pow((media - dist),2);
		}
		double dispersion = 0;
		if(solutions.size() > 1){
			dispersion = Math.sqrt((1.0/(solutions.size()-1))*sumDistancias);
		}
		return dispersion;
	}

	/**
	 * Checks whether the provided solution (by evaluation vector) is
	 * contained in the list of solutions.
	 *
	 * @param solA the solution to search for
	 * @param solutions the list to search inside
	 * @return true if a solution with an equal evaluation vector exists
	 */
	private boolean Contains(State solA, List<State> solutions){
		int i = 0;
		boolean result = false;
		while(i<solutions.size()&& result==false){
			if(solutions.get(i).getEvaluation().equals(solA.getEvaluation()))
				result=true;
			else
				i++;
		}
		return result;
	}

	/**
	 * Compute minimum value in a list of metrics.
	 *
	 * @param allMetrics list of metric values
	 * @return minimum value found
	 */
	public double CalcularMin(ArrayList<Double> allMetrics){
		double min = 1000;
		for (Iterator<Double> iter = allMetrics.iterator(); iter.hasNext();) {
			double element = (Double) iter.next();
			if(element < min){
				min = element;
			}
		}
		return min;
	}

	/**
	 * Compute maximum value in a list of metrics.
	 *
	 * @param allMetrics list of metric values
	 * @return maximum value found
	 */
	public double CalcularMax(ArrayList<Double> allMetrics){
		double max = 0;
		for (Iterator<Double> iter = allMetrics.iterator(); iter.hasNext();) {
			double element = (Double) iter.next();
			if(element > max){
				max = element;
			}
		}
		return max;
	}

	/**
	 * Compute arithmetic mean of a list of metric values.
	 *
	 * @param allMetrics list of metric values
	 * @return arithmetic mean
	 */
	public double CalcularMedia(ArrayList<Double> allMetrics){
		double sum = 0;
		for (Iterator<Double> iter = allMetrics.iterator(); iter.hasNext();) {
			double element = (Double) iter.next();
			sum = sum + element;
		}
		double media = sum/allMetrics.size();
		return media;
	}
}
