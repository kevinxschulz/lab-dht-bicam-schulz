package metaheuristics.generators;

/**
 * Simple helper used by `MultiGenerator` to store roulette wheel limits.
 *
 * <p>Each `LimitRoulette` maps a probability interval (low/high) to a
 * specific `Generator` instance for roulette selection.
 */
public class LimitRoulette {

	private float limitLow;
	private float limitHigh;
	private Generator generator;
	
	public Generator getGenerator() {
		return generator;
	}
	public void setGenerator(Generator generator) {
		this.generator = generator;
	}
	public float getLimitHigh() {
		return limitHigh;
	}
	public void setLimitHigh(float limitHigh) {
		this.limitHigh = limitHigh;
	}
	public float getLimitLow() {
		return limitLow;
	}
	public void setLimitLow(float limitLow) {
		this.limitLow = limitLow;
	}
	
	
}
