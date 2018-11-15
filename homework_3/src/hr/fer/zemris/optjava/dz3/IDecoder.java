package hr.fer.zemris.optjava.dz3;

/**
 * Interface describing the decoding procedure of a T type object.
 * @author Viran
 *
 * @param <T> Coded object.
 */
public interface IDecoder<T extends SingleObjectSolution> {
	/**
	 * For a given value return its corresponding decoded array. 
	 * @param value Coded object.
	 * @return Decoded double array.
	 */
	public double[] decode(T value);
	
	/**
	 * For a given value return its corresponding decoded array. 
	 * @param value Coded object.
	 * @param decodedValues Decoded double array.
	 */
	public void decode(T value,double[] decodedValues);
}
