package hr.fer.zemris.optjava.dz3;

/**
 * Single solution representation.
 * @author Viran
 *
 */
public class SingleObjectSolution extends Object{
	public double fitness;
	public double value;
	
	/**
	 * SingleObjectSolution constructor.
	 */
	public SingleObjectSolution() {
	}
	
	/**
	 * Compares this with the given SingleObjectSolution object.
	 * @param other Solution with which we compare.
	 * @return Returns a positive number if this solution is greater than other, 0 if they're equal, or a negative number if this solution is smaller than other.
	 */
	public int compareTo(SingleObjectSolution other){
		double d=this.fitness-other.fitness;
		if(d>0)
			return 1;
		if(d==0)
			return 0;
		return -1;
		
	}
}
