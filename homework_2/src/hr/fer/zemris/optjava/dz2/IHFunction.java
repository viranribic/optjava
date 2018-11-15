package hr.fer.zemris.optjava.dz2;

import Jama.*;
/**
 * Scalar function over n-dimensional vector of real number offering calculation of a Hessian matrix.
 * @author Viran
 *
 */
public interface IHFunction extends IFunction {

	/**
	 * Calculate Hessian matrix of this function for the given value.
	 * @param value Position on the domain.
	 * @return Matrix with the elements of partial derivatives.
	 */
	Matrix HessianMatrix(Matrix value);
}
