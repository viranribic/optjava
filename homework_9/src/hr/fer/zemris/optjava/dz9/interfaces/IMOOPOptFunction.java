package hr.fer.zemris.optjava.dz9.interfaces;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz9.algorithm.NSGASolution;

/**
 * Interface for calculating a scalar function value given a vector.
 * 
 * @author Viran
 *
 */
public interface IMOOPOptFunction {

	/**
	 * Get the dimension of the problem domain this function optimises.
	 * 
	 * @return Decision space domain dimension.
	 */
	public int getDecisionSpaceDim();

	/**
	 * Get the dimension of the problem domain this function optimises.
	 * 
	 * @return Objective space domain dimension.
	 */
	public int getObjectiveSpaceDim();

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

	/**
	 * For the given population, evaluate it's fitnesses.
	 * 
	 * @param population
	 *            Population in need of fitness evaluation.
	 */
	public void evaluatePopulation(LinkedList<NSGASolution> population, double alpha, double sigma);

	/**
	 * Get the MOOP problem assigned to this function.
	 * 
	 * @return MOOP problem.
	 */
	public MOOPProblem getMOOPProblem();

	/**
	 * Get Pareto fronts for the given population.
	 * 
	 * @param population
	 *            Population of subjects.
	 * @return List of Pareto fronts elements.
	 */
	public LinkedList<LinkedList<NSGASolution>> getParetoFronts(LinkedList<NSGASolution> population);
}
