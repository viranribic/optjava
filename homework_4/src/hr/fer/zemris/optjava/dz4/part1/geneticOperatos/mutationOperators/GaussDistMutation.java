package hr.fer.zemris.optjava.dz4.part1.geneticOperatos.mutationOperators;

import java.util.Random;

import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

/**
 * Class offering mutation operation as used in genetic algorithms.
 * This approach exposes subject solution values for mutation supported by normal distribution.
 * @author Viran
 *
 */
public class GaussDistMutation {

	private static Random rand=new Random();
	
	/**
	 * For a given subject, execute genetic mutation using normal distribution with the mean of 0 and standard deviation sigma.
	 * This method does not modify the original subject, instead offering a new copy.
	 * @param subject Subject exposed with mutation.
	 * @param sigma Standard deviation of normal distribution used.
	 * @return Mutated result.
	 */
	public DoubleArraySolution mutation(DoubleArraySolution subject,double sigma){
		DoubleArraySolution mutatedSolution=subject.duplicate();
		int solutionDim=mutatedSolution.getDimension();
		for(int i=0;i<solutionDim;i++)
			mutatedSolution.values[i]+=rand.nextGaussian()*sigma;
		return mutatedSolution;
	}
	
	
}
