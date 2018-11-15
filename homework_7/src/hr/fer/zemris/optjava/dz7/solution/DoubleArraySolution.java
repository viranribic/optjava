package hr.fer.zemris.optjava.dz7.solution;

import java.util.Random;

/**
 * Single solution represented with an array of real number.
 * 
 * @author Viran
 *
 */
public class DoubleArraySolution implements Comparable<DoubleArraySolution>{
	private double[] values;
	private double fitness;

	/**
	 * DoubleArraySolution constructor.
	 * 
	 * @param n
	 *            Dimension of the array.
	 */
	public DoubleArraySolution(int n) {
		values = new double[n];
	}

	/**
	 * Returns a new solution with the same dimension.
	 * 
	 * @return Solution of the same dimension.
	 */
	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(values.length);
	}

	/**
	 * Duplicates this object in terms of values and fitness attributes.
	 * 
	 * @return A duplicate of this object.
	 */
	public DoubleArraySolution duplicate() {
		DoubleArraySolution duplicate = new DoubleArraySolution(values.length);
		duplicate.values = this.values.clone();
		duplicate.fitness = this.fitness;
		return duplicate;
	}

	/**
	 * Randomises the values of this solution in a given interval.
	 * 
	 * @param random
	 *            Pseudorandom number generator.
	 * @param minValues
	 *            Lower boundary of randomly generated solution.
	 * @param maxValues
	 *            Upper boundary of randomly generated solution.
	 */
	public void randomize(Random random, double[] minValues, double[] maxValues) {
		for (int i = 0; i < values.length; i++) {
			double newValue = random.nextDouble() * (maxValues[i] - minValues[i]) + minValues[i];
			this.values[i] = newValue;
		}
	}

	/**
	 * Get the double array contained in this solution.
	 * 
	 * @return Double array solution values.
	 */
	public double[] getValues() {
		return this.values;
	}

	/**
	 * Get the fitness value contained.
	 * 
	 * @return Fitness value.
	 */
	public double getFitness() {
		return this.fitness;
	}

	/**
	 * Set the new fitness value.
	 * 
	 * @param fitness
	 *            New fitness.
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * Get the number of solution vector elements.
	 * 
	 * @return Dimension of the problem.
	 */
	public int getDimension() {
		return values.length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++)
			sb.append(String.format("%4.3f ", values[i]));
		return "Fitness: " + fitness + " Values size:" + values.length + " Values: " + sb.toString();
	}

	@Override
	public int compareTo(DoubleArraySolution o) {
		return Double.compare(this.fitness,o.fitness);
	}
}
