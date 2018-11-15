package hr.fer.zemris.optjava.rng;

/**
 * Interface representing the objects who contain generator of random numbers which 
 * provides the use of {@link #getRNG()}. Objects who implement this interface can't return a different
 * generator. Rather, they must return the same object every time the method is called. 
 * @author Viran
 *
 */
public interface IRNGProvider {

	/**
	 * Method for acquiring random number generator which belong to this object.
	 * @return Random number generator. 
	 */
	public IRNG getRNG();
}
