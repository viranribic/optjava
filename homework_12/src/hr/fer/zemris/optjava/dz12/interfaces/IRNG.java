package hr.fer.zemris.optjava.dz12.interfaces;

/**
 * Interface for random number generator.
 * @author Viran
 *
 */
public interface IRNG {

	
	/**
	 * Return a decimal number uniformly distributed from interval [0,1).
	 * @return Randomly generated decimal number.
	 */
	public double nextDouble();
	
	/**
	 * Return a decimal number uniformly distributed from interval [min,max).
	 * @param min Interval lower boundary (inclusive).
	 * @param max Interval upper boundary (exclusive).
	 * @return Randomly generated decimal number.
	 */
	public double nextDouble(double min, double max);
	
	/**
	 * Return a decimal number uniformly distributed from interval [0,1).
	 * @return Randomly generated decimal number.
	 */
	public float nextFloat();
	
	/**
	 * Return a decimal number uniformly distributed from interval [min,max).
	 * @param min Interval lower boundary (inclusive).
	 * @param max Interval upper boundary (exclusive).
	 * @return Randomly generated decimal number.
	 */
	public float nextFloat(float min, float max);
	
	/**
	 * Return a integer number uniformly distributed from the interval of all possible integer numbers.
	 * @return Randomly generated integer number.
	 */
	public int nextInt();
	
	/**
	 * Return a integer number uniformly distributed from interval [min,max).
	 * @param min Interval lower boundary (inclusive).
	 * @param max Interval upper boundary (exclusive).
	 * @return Randomly generated integer number.
	 */
	public int nextInt(int min, int max);
	
	/**
	 * Returns a randomly generated boolean value. Value is generated from uniform distribution.
	 * @return Randomly generated boolean. 
	 */
	public boolean nextBoolean();
	
	/**
	 * Returns a decimal number from a normal distribution with parameters (0,1).
	 * @return Randomly generated decimal number.
	 */
	public double nextGaussian();
	
}
