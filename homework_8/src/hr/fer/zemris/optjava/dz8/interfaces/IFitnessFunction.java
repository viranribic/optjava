package hr.fer.zemris.optjava.dz8.interfaces;

import data.DataContainer;

/**
 * Fitness function interface.
 * @author Viran
 *
 */
public interface IFitnessFunction<T> {

	/**
	 * Calculate error value.
	 * 
	 * @param weights
	 *            Weights in need of evaluation.
	 * @return Error value.
	 */
	public double valueAt(double[] weights);
	
	/**
	 * Get number of weights for neural network used.
	 * 
	 * @return Neural network weights.
	 */
	public int getParametersCount();
	
	public T getNeuralNetwork();

	public DataContainer getData();
}
