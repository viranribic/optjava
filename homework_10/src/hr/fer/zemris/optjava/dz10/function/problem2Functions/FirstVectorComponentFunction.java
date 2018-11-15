package hr.fer.zemris.optjava.dz10.function.problem2Functions;

import hr.fer.zemris.optjava.dz10.interfaces.IFunction;

/**
 * For a given vector calculate the function f(v)=v1 .
 * 
 * @author Viran
 *
 */
public class FirstVectorComponentFunction implements IFunction {

	@Override
	public double[] f(double[] v) {
		if (v.length < 1)
			throw new IllegalArgumentException("Vector dimension too small.");
		return new double[] { v[0] };
	}

}
