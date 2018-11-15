package hr.fer.zemris.optjava.dz9.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * Single solution represented with an array of real number.
 * 
 * @author Viran
 *
 */
public class NSGASolution implements Comparable<NSGASolution> {
	private double[] values;
	private double fitness;
	private double[] objSolValues;

	/**
	 * DoubleArraySolution constructor.
	 * 
	 * @param dim
	 *            Dimension of the array.
	 */
	public NSGASolution(int dim, int objDim) {
		values = new double[dim];
		objSolValues = new double[objDim];
	}

	/**
	 * Returns a new solution with the same dimension.
	 * 
	 * @return Solution of the same dimension.
	 */
	public NSGASolution newLikeThis() {
		return new NSGASolution(values.length, objSolValues.length);
	}

	/**
	 * Duplicates this object in terms of values and fitness attributes.
	 * 
	 * @return A duplicate of this object.
	 */
	public NSGASolution duplicate() {
		NSGASolution copy = new NSGASolution(values.length, objSolValues.length);
		copy.fitness = this.fitness;
		copy.values = this.values.clone();
		copy.objSolValues = this.objSolValues.clone();
		return copy;
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
	 * Get the dimension of the domain this solution belongs to.
	 * 
	 * @return Dimension of the problem domain.
	 */
	public int getDimension() {
		return values.length;
	}

	/**
	 * Set fitness value for this subject.
	 * 
	 * @param fit
	 *            Subject fitness.
	 */
	public void setFit(double fit) {
		this.fitness = fit;

	}

	/**
	 * Get fitness value for this subject.
	 * 
	 * @return Subject fitness.
	 */
	public double getFit() {
		return this.fitness;

	}

	/**
	 * Get the values of this vector.
	 * 
	 * @return Vector values.
	 */
	public double[] getValuesVector() {
		return values;
	}

	/**
	 * Set the values of this vector.
	 * 
	 * @param values
	 *            Set values.
	 */
	public void setValuesVector(double[] values) {
		this.values = values;
	}

	@Override
	public int compareTo(NSGASolution arg0) {
		return Double.compare(fitness, arg0.fitness);
	}

	/**
	 * Resolve weather this solution dominates over the other.
	 * 
	 * @param other
	 *            Other solution we are comparing.
	 * @param moopProblem
	 *            Optimisation function which determinate the criteria of
	 *            domination.
	 * @return True if this solution dominates over the other, false otherwise.
	 */
	public boolean dominates(NSGASolution other) {
		double[] thisFit = objSolValues;
		double[] otherFit = other.objSolValues;

		boolean thisBetter = false;

		int thisBetterDims = 0;

		double dif;
		for (int i = 0; i < thisFit.length; i++) {
			dif = thisFit[i] - otherFit[i];

			if (dif == 0) {
				thisBetterDims++;
			} else if (dif < 0) {
				thisBetterDims++;
				thisBetter = true;
			} else if (dif > 0) {
				thisBetter = false;
				break;
			}
		}

		if (thisBetter && thisBetterDims == thisFit.length)
			return true;

		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fitness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NSGASolution other = (NSGASolution) obj;
		if (Double.doubleToLongBits(fitness) != Double.doubleToLongBits(other.fitness))
			return false;
		if (!Arrays.equals(values, other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Fitness: " + String.format("%5.5f", fitness) + " ");
		sb.append("Values: ");
		for (int i = 0; i < values.length; i++)
			sb.append(String.format("%5.5f", values[i]) + " ");
		sb.append("Solution objectives: ");
		for (int i = 0; i < objSolValues.length; i++)
			sb.append(String.format("%5.5f", objSolValues[i]) + " ");

		return sb.toString();
	}

	/**
	 * Get objective solution values.
	 * 
	 * @return Objective solution values.
	 */
	public double[] getObjSolValues() {
		return objSolValues;
	}

	/**
	 * Set objective solution values.
	 * 
	 * @param objSolValues
	 *            Objective solution values.
	 */
	public void setObjSolValues(double[] objSolValues) {
		this.objSolValues = objSolValues;
	}

	public String valToString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++)
			sb.append(String.format("%5.5f", values[i]) + " ");
		return sb.toString();
	}

	public String objToString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < objSolValues.length; i++)
			sb.append(String.format("%5.5f", objSolValues[i]) + " ");

		return sb.toString();
	}
}
