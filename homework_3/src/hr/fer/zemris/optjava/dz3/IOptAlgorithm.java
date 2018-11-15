package hr.fer.zemris.optjava.dz3;

/**
 * Interface for optimisation algorithms.
 * @author Viran
 *
 * @param <T> Type of single solution from the solution space domain.
 */
public interface IOptAlgorithm<T extends SingleObjectSolution> {
	/**
	 * Run algorithm.
	 */
	public void run();
}
