package hr.fer.zemris.optjava.dz4.part1.interfaces;

/**
 * Interface for calculating a scalar function value given a vector.
 * @author Viran
 *
 */
public interface IFunction {
	/**
	 * Calculates the result of a value described with this expression.
	 * @param value Vector value.
	 * @return Scalar result.
	 */
	public double valueAt(double[] value);
}
