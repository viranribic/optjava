package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

/**
 * Scalar function over n-dimensional vector of real number.
 * @author Viran
 *
 */
public interface IFunction {
	/**
	 * Size of domain this function is defined on.
	 * @return The dimension of the domain.
	 */
	public int domainDimension();
	
	/**
	 * Calculates the value of this scalar function of the vector defined on the function domain.
	 * @param value Position on the domain.
	 * @return Result of function.
	 */
	public double functionCalc(Matrix value);
	
	/**
	 * Calculates the value of the gradient of this function of the vector defined on the function domain.
	 * @param value Position on the domain.
	 * @return Result of gradient operator of the function.
	 */
	public Matrix gradCalc(Matrix value);
	
}
