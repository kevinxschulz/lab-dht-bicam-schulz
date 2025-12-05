package evolutionary_algorithms.complement;

import java.util.ArrayList;
import java.util.List;

import metaheuristics.generators.LimitRoulette;

import problem.definition.State;

/**
 * Clase que implementa la selección por ruleta para elegir padres.
 * En este método, la probabilidad de que un individuo sea seleccionado es proporcional a su aptitud.
 */
public class RouletteSelection extends FatherSelection {

	/**
	 * Realiza la selección de padres utilizando el método de la ruleta.
	 * @param listState La lista de estados de la población actual.
	 * @param truncation El número de padres a seleccionar (no se utiliza directamente en esta implementación,
	 *                   ya que la ruleta selecciona basándose en probabilidades relativas).
	 * @return Una lista de estados que representan los padres seleccionados.
	 */
	@Override
	public List<State> selection(List<State> listState, int truncation) {/*
		List<State> fatherList = new ArrayList<State>();
		double total = 0;
		double sum = 0;
		for (int i = 0; i < listState.size(); i++) {
			total  = total + listState.get(i).getEvaluation().get(0);
		}
		double number = (double) Math.random() * (double)(1);

		for (int i = 0; i < listState.size(); i++) {
		  sum = sum + listState.get(i).getEvaluation().get(0);
		  if(sum >= number)
			  fatherList.add(listState.get(i));			  
		}
		return fatherList;
	 */
		float totalWeight = 0;
		for (int i = 0; i < listState.size(); i++) {
			totalWeight = (float) (listState.get(i).getEvaluation().get(0) + totalWeight);
		}
		List<Float> listProb = new ArrayList<Float>();
		for (int i = 0; i < listState.size(); i++) {
			float probF = (float) (listState.get(i).getEvaluation().get(0) / totalWeight);
			listProb.add(probF);
		}
		List<LimitRoulette> listLimit = new ArrayList<LimitRoulette>();
		float limitHigh = 0;
		float limitLow = 0;
		for (int i = 0; i < listProb.size(); i++) {
			LimitRoulette limitRoulette = new LimitRoulette();
			limitHigh = listProb.get(i) + limitHigh;
			limitRoulette.setLimitHigh(limitHigh);
			limitRoulette.setLimitLow(limitLow);
			limitLow = limitHigh;
			//			limitRoulette.setGenerator(listGenerators.get(i));
			listLimit.add(limitRoulette);
		}
		List<State> fatherList = new ArrayList<State>();
		for (int j = 0; j < listState.size(); j++) {
			float numbAleatory = (float) (Math.random() * (double)(1));
			boolean find = false;
			int i = 0;
			while ((find == false) && (i < listLimit.size())){
				if((listLimit.get(i).getLimitLow() <= numbAleatory) && (numbAleatory <= listLimit.get(i).getLimitHigh())){
					find = true;
					fatherList.add(listState.get(i));
				}
				else i++;
			}
		}
		return fatherList;
	}
}
