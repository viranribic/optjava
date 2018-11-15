package hr.fer.zemris.optjava.dz9.algorithm.crossover;

import java.util.Random;

import hr.fer.zemris.optjava.dz9.algorithm.NSGASolution;

/**
 * Class offering crossover operation as used in genetic algorithms. This
 * approach takes two parents and produces one offspring. For more info visit
 * page: <a href="http://www.tomaszgwiazda.com/blendX.htm"> BLX crossover.</a>
 * 
 * @author Viran
 *
 */
public class BLXAlphaOperator {

	private static Random rand = new Random();
	private double[] minDomainVals;
	private double[] maxDomainVals;

	/**
	 * BLXAlphaOperator constructor.
	 * 
	 * @param minDomainVals
	 *            Minimal allowed domain values.
	 * @param maxDomainVals
	 *            Maximal allowed domain values.
	 */
	public BLXAlphaOperator(double[] minDomainVals, double[] maxDomainVals) {
		this.minDomainVals = minDomainVals;
		this.maxDomainVals = maxDomainVals;
	}

	/**
	 * For the given parents perform a BLX-alpha crossover.
	 * 
	 * @param parentA
	 *            First parent solution.
	 * @param parentB
	 *            Second parent solution.
	 * @param alpha
	 *            Alpha coefficient of algorithm.
	 * @return Offspring of parentA and parentB.
	 */
	public NSGASolution crossover(NSGASolution parentA, NSGASolution parentB, double alpha) {
		int solutionDim = parentA.getDimension();
		NSGASolution offspring = parentA.newLikeThis();
		double cMin;
		double cMax;
		double I;
		double upperB;
		double lowerB;
		double[] valuesA = parentA.getValuesVector();
		double[] valuesB = parentB.getValuesVector();
		double[] offspringValues = offspring.getValuesVector();
		for (int i = 0; i < solutionDim; i++) {
			cMin = Math.min(valuesA[i], valuesB[i]);
			cMax = Math.max(valuesA[i], valuesB[i]);
			I = cMax - cMin;
			upperB = cMax + I * alpha;
			lowerB = cMin - I * alpha;
			// offspring.values[i]= unif( cMin-I*alpha, cMax+I*alpha);
			offspringValues[i] = rand.nextDouble() * (upperB - lowerB) + lowerB;
			if (offspringValues[i] < minDomainVals[i])
				offspringValues[i] = minDomainVals[i];
			if (offspringValues[i] > maxDomainVals[i])
				offspringValues[i] = maxDomainVals[i];
		}

		return offspring;
	}

}
