package hr.fer.zemris.optjava.dz9.function.problem2Functions;

import hr.fer.zemris.optjava.dz9.interfaces.IFunction;

/**
 * For a given vector calculate the function f(v)=(1+v2)/v1 .
 * 
 * @author Viran
 *
 */
public class ComponentRatioFunction implements IFunction {

	@Override
	public double[] f(double[] v) {
		if (v.length < 2)
			throw new IllegalArgumentException("Vector dimension too small.");
		return new double[] { (v[1] + 1) / v[0] };
	}

}
