package hr.fer.zemris.optjava.dz9.interfaces;

/**
 * Function interface.
 * 
 * @author Viran
 *
 */
public interface IFunction {
	/**
	 * For a v domain vector, calculate the w codomain vector.
	 * 
	 * @param v
	 *            Input vector.
	 * @return Output vector.
	 */
	public double[] f(double[] v);
}
