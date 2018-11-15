package hr.fer.zemris.trisat;

/**
 * Interface for modelling the behaviour of the optimisation algorithm.
 * @author Viran
 *
 */
public interface OptimisationFlowControl {
	/**
	 * Decides weather the stop condition for this algorithm is met or not.
	 * @return True if the stop condition is met, false otherwise.
	 */
	public boolean stopCondition();
	
	/**
	 * Method form making the necessary control adjustments during the iteration we want to control.
	 */
	public void iterationPassed();
}
