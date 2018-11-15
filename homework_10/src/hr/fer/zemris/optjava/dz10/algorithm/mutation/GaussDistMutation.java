package hr.fer.zemris.optjava.dz10.algorithm.mutation;

import java.util.Random;

import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Solution;

/**
 * Class offering mutation operation as used in genetic algorithms. This
 * approach exposes subject solution values for mutation supported by normal
 * distribution.
 * 
 * @author Viran
 *
 */
public class GaussDistMutation {

	private static Random rand = new Random();
	private double[] minDomainVals;
	private double[] maxDomainVals;

	public GaussDistMutation(double[] minDomainVals, double[] maxDomainVals) {
		this.minDomainVals = minDomainVals;
		this.maxDomainVals = maxDomainVals;
	}

	/**
	 * For a given subject, execute genetic mutation using normal distribution
	 * with the mean of 0 and standard deviation sigma. This method does not
	 * modify the original subject, instead offering a new copy.
	 * 
	 * @param subject
	 *            Subject exposed with mutation.
	 * @param sigma
	 *            Standard deviation of normal distribution used.
	 * @return Mutated result.
	 */
	public NSGA2Solution mutation(NSGA2Solution subject, double sigma) {
		NSGA2Solution mutatedSolution = subject.duplicate();
		int solutionDim = mutatedSolution.getDecisionDimension();
		double[] values = mutatedSolution.getValuesVector();
		for (int i = 0; i < solutionDim; i++) {
			values[i] += rand.nextGaussian() * sigma;
			if (values[i] < minDomainVals[i])
				values[i] = minDomainVals[i];
			if (values[i] > maxDomainVals[i])
				values[i] = maxDomainVals[i];
		}
		return mutatedSolution;
	}

}
