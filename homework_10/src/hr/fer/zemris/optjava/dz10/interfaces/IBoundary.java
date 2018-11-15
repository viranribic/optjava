package hr.fer.zemris.optjava.dz10.interfaces;

/**
 * Class representation of a real number interval.
 * 
 * @author Viran
 *
 */
public interface IBoundary {

	/**
	 * Test weather the given value is situated between the required values.
	 * 
	 * @param x
	 *            Tested value.
	 * @return True if x is element between upper and lower boundary, false
	 *         otherwise.
	 */
	public boolean isContained(double x);

	/**
	 * Get minimal boundary value;
	 * 
	 * @return Minimal value.
	 */
	public double getMin();

	/**
	 * Get maximal boundary value;
	 * 
	 * @return Maximal value.
	 */
	public double getMax();
}
