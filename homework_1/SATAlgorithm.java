package hr.fer.zemris.trisat;

/**
 * SATAlgorith interface for modelling and running various types of algorithm approaches.
 * @author Viran
 *
 */
public interface SATAlgorithm {
	/**
	 * Start the algorithm.
	 * @return The necessary informations as the ResultLog object. 
	 */
	public ResultLog run();
}
