package hr.fer.zemris.optjava.dz3;

/**
 * Interface for a temperature scheduling system as seen in SimulatedAnneling algorithm.
 * @author Viran
 *
 */
public interface ITempSchedule {
	
	/**
	 * Get the temperature for the next iteration.
	 * @return Next temperature value.
	 */
	public double getNextTemperature();
	
	/**
	 * Get the counter for the inner loop of SimulatedAnneling algorithm.
	 * @return Number of inner loop iterations.
	 */
	public int getInnerLoopCounter();
	
	/**
	 * Get the counter for the outer loop of SimulatedAnneling algorithm.
	 * @return Number of outer loop iterations.
	 */
	public int getOuterLoopCounter();
}
