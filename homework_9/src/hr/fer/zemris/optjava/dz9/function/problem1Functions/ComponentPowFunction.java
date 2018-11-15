package hr.fer.zemris.optjava.dz9.function.problem1Functions;

import hr.fer.zemris.optjava.dz9.interfaces.IFunction;

/**
 * For a given vector, and a position i, calculate the function f(v)=vi^2 .
 * 
 * @author Viran
 *
 */

public class ComponentPowFunction implements IFunction {

	private int position;

	/**
	 * ComponentPowFunction constructor.
	 * 
	 * @param position
	 *            The position of the vector which power should be returned.
	 */
	public ComponentPowFunction(int position) {
		this.position = position;
	}

	@Override
	public double[] f(double[] v) {
		if (v.length < position + 1)
			throw new IllegalArgumentException("Vector dimension too small.");
		return new double[] { v[position] * v[position] };

	}

}
