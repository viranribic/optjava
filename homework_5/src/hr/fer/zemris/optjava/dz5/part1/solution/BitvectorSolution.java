package hr.fer.zemris.optjava.dz5.part1.solution;

import java.util.Arrays;

/**
 * Bit vector solution represented with an array of booleans.
 * @author Viran
 *
 */
public class BitvectorSolution implements Comparable<BitvectorSolution>{
	private double fitness;
	private boolean[] solutionVector;
	
	/**
	 * BitvectorSolution constructor.
	 * @param vectorSize Vector size.
	 */
	public BitvectorSolution(int vectorSize) {
		this.solutionVector=new boolean[vectorSize];
	}
	
	/**
	 * BitvectorSolution constructor.
	 * @param vector Bit vector.
	 */
	public BitvectorSolution(boolean[] vector) {
		this.solutionVector=vector.clone();
	}

	/**
	 * Get size of this vector.
	 * @return Vector size.
	 */
	public int getVectorSize(){
		return solutionVector.length;
	}
	
	/**
	 * Get the vector value of this solution. Changes to the given list are reflected to this object.
	 * @return Vector value.
	 */
	public boolean[] getVector(){
		return solutionVector;
	}
	
	/**
	 * Get fitness of this solution.
	 * @return Solution fitness.
	 */
	public double getFitness(){
		return fitness;
	}
	
	/**
	 * Set fitness for this solution.
	 * @param fitness New solution fitness.
	 */
	public void setFitness(double fitness){
		this.fitness=fitness;
	}
	
	/**
	 * Create a duplicate of this solution.
	 * @return Duplicated solution.
	 */
	public BitvectorSolution duplicate(){
		BitvectorSolution clone=new BitvectorSolution(solutionVector.length);
		clone.fitness=this.fitness;
		clone.solutionVector=this.solutionVector.clone();
		return clone;
	}
	
	/**
	 * Return a new solution with the same vector dimension as this solution.
	 * @return A clean solution with the same dimension.
	 */
	public BitvectorSolution newLikeThis(){
		return new BitvectorSolution(solutionVector.length);
	}

	@Override
	public int compareTo(BitvectorSolution o) {
		if(this.solutionVector.length!=o.solutionVector.length)
			throw new IllegalArgumentException("BitvectorError: Can not compare the given solutions.");
		for(int i=0;i<o.solutionVector.length;i++){
			if(this.solutionVector[i]==true && o.solutionVector[i]==false){
				return 1;
			}else if(this.solutionVector[i]==false && o.solutionVector[i]==true){
				return -1;
			}
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fitness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(solutionVector);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BitvectorSolution other = (BitvectorSolution) obj;
		if (Double.doubleToLongBits(fitness) != Double.doubleToLongBits(other.fitness))
			return false;
		if (!Arrays.equals(solutionVector, other.solutionVector))
			return false;
		return true;
	}
}