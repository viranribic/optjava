package hr.fer.zemris.optjava.dz8.function;

import hr.fer.zemris.optjava.dz8.interfaces.ITransferFunction;

/**
 * Tangent hyperbolic Transfer function class.
 * @author Viran
 *
 */
public class TanHTransferFuncition implements ITransferFunction {

	@Override
	public double[] transFuntcVal(double[] input) {
		if(input.length!=1)
			throw new IllegalArgumentException("TanHTransferFuncition takes one scalar value and outputs one scalar value.");
		double output=(1-Math.exp(-input[0]))/(1+Math.exp(-input[0]));
		return new double[] {output};
	}

	@Override
	public String toString() {
		return "Tangent hyperbolic transfer function: f(x)=(1-e^-x)/(1+e^-x)";
	}
}
