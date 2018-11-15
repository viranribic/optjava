package hr.fer.zemris.optjava.dz4.part1.solution;

import java.util.Random;

/**
 * Single solution represented with an array of real number.
 * @author Viran
 *
 */
public class DoubleArraySolution implements Comparable<DoubleArraySolution> {
	public double[] values;
	public double fitness;
	/**
	 * DoubleArraySolution constructor.
	 * @param dim Dimension of the array.
	 */
	public DoubleArraySolution(int dim) {
		values=new double[dim]; 
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
		DoubleArraySolution copy=new DoubleArraySolution(values.length);
		copy.fitness=this.fitness;
		copy.values=this.values.clone();
		return copy;
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

	/**
	 * Get the dimension of the domain this solution belongs to.
	 * @return Dimension of the problem domain.
	 */
	public int getDimension(){
		return values.length;
	}
	
	@Override
	public int compareTo(DoubleArraySolution o) {
		double d=this.fitness-o.fitness;
		if(d>0)
			return 1;
		if(d==0)
			return 0;
		return -1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof DoubleArraySolution))
			throw new IllegalArgumentException("Error. DoubleArraySolution and the given object can not be compared.");
		return (this.compareTo((DoubleArraySolution)obj)==0)?true:false;
	}
}
