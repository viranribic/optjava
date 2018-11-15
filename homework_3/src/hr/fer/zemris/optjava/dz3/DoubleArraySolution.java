package hr.fer.zemris.optjava.dz3;

import java.util.Random;

/**
 * Single solution represented with an array of real number.
 * @author Viran
 *
 */
public class DoubleArraySolution extends SingleObjectSolution {
	public double[] values;
	
	/**
	 * DoubleArraySolution constructor.
	 * @param n Dimension of the array.
	 */
	public DoubleArraySolution(int n) {
		values=new double[n]; 
	}
	
	/**
	 * Returns a new solution with the same dimension.
	 * @return Solution of the same dimension.
	 */
	public DoubleArraySolution newLikeThis(){
		return new DoubleArraySolution(values.length);
	}
	
	/**
	 * Duplicates this object in terms of values and fitness attributes.
	 * @return A duplicate of this object.
	 */
	public DoubleArraySolution duplicate() {
		DoubleArraySolution duplicate=new DoubleArraySolution(values.length);
		duplicate.values=this.values.clone();
		duplicate.fitness=this.fitness; //
		duplicate.value=this.value;		//
		return duplicate;
	}
	
	/**
	 * Randomises the values of this solution in a given interval.
	 * @param random Pseudorandom number generator.
	 * @param minValues Lower boundary of randomly generated solution.
	 * @param maxValues Upper boundary of randomly generated solution.
	 */
	public void randomize(Random random, double[] minValues, double[] maxValues){
		for(int i=0;i<values.length;i++){
			double newValue=random.nextDouble()*(maxValues[i]-minValues[i])+minValues[i];
			this.values[i]=newValue;
		}
	}
	
}
