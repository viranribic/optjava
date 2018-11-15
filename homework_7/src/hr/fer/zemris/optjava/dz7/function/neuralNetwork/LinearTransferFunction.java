package hr.fer.zemris.optjava.dz7.function.neuralNetwork;

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
