package hr.fer.zemris.optjava.dz8.neuralNetwork.util;

import hr.fer.zemris.optjava.dz8.interfaces.ITransferFunction;

/**
 * For the given input transfer the data as output.
 * @author Viran
 *
 */
public class LinearTransferFunction implements ITransferFunction {

	@Override
	public double[] transFuntcVal(double[] input) {
		return input;
	}

	@Override
	public String toString() {
		return "LinearTransferFunction: f(x)=x";
	}
}
