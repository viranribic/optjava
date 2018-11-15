package hr.fer.zemris.optjava.dz7.function.neuralNetwork;

/**
 * SigmoidTransferFunction class. Takes one input and results in one output.
 * @author Viran
 *
 */
public class SigmoidTransferFunction implements ITransferFunction{

	@Override
	public double[] transFuntcVal(double[] input) {
		if(input.length!=1)
			throw new IllegalArgumentException("SigmoidTransferFunction takes one scalar value and outputs one scalar value.");
		double output=1.0/(1.0+Math.exp(-input[0]));
		return new double[] {output};
	}

	@Override
	public String toString() {
		return "SigmoidTransferFunction: f(x)=1.0/(1.0+e^(-x))";
	}
	
}
