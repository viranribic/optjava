package hr.fer.zemris.trisat;

/**
 * IterativeFlowControl is a specific implementation of the OptimisationFlowControl interface which counts
 * the number of iterations before the stop condition is met.
 * @author Viran
 *
 */
public class IterationFlowControl implements OptimisationFlowControl {

	int targetNumberOfIterations=0;
	
	/**
	 * Constructor which sets the target number of iterations needed to signal the end of the search.
	 * @param n Numebr of iterations.
	 */
	public IterationFlowControl(int n) {
		targetNumberOfIterations=n;
	}

	@Override
	public boolean stopCondition() {
		return (targetNumberOfIterations==0)?true:false;
	}

	@Override
	public void iterationPassed() {
		targetNumberOfIterations--;
	}

}
