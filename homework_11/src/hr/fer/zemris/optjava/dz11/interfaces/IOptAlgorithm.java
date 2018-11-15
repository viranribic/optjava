package hr.fer.zemris.optjava.dz11.interfaces;

/**
 * Interface for optimisation algorithms.
 * @author Viran
 *
 * @param <T> Type of single solution from the solution space domain.
 */
public interface IOptAlgorithm<T> {
	/**
	 * Run algorithm.
	 */
	public T run();
}
