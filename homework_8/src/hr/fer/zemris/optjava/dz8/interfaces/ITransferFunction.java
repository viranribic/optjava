package hr.fer.zemris.optjava.dz8.interfaces;

/**
 * Interface for transfer functions used in neural network data structure.
 * @author Viran
 *
 */
public interface ITransferFunction {

	/**
	 * Calculate the value of this function.
	 * @param input Function input.
	 * @return Function output.
	 */
	public double[] transFuntcVal(double[] input);
}
