package hr.fer.zemris.optjava.dz9.interfaces;

/**
 * Multi-objective optimisation problem interface.
 * 
 * @author Viran
 *
 */
public interface MOOPProblem {

	/**
	 * Evaluate the solution and save the results in the objectives array.
	 * 
	 * @param solution
	 *            Solution in need of evaluation.
	 * @param objectives
	 *            Objective values.
	 */
	void evaluateSolution(double[] solution, double[] objectives);

	/**
	 * Get the dimension of the problem domain this function optimises.
	 * 
	 * @return Domain dimension.
	 */
	public int getDecisionSpaceDim();

	/**
	 * Return the number of criteria of the problem.
	 * 
	 * @return Number of criteria.
	 */
	int getObjectiveSpaceDim();

	/**
	 * Get the vector of minimal domain values per dimension.
	 * 
	 * @return Lower domain boundaries.
	 */
	public double[] getMinDomainVals();

	/**
	 * Get the vector of minimal domain values per dimension.
	 * 
	 * @return Upper domain boundaries.
	 */
	public double[] getMaxDomainVals();
}
