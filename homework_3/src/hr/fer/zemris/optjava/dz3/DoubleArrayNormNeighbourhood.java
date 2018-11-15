package hr.fer.zemris.optjava.dz3;

import java.util.Random;

/**
 * Neighbourhood generator for a solution represented with a array of double values using normal distribution.
 * @author Viran
 *
 */
public class DoubleArrayNormNeighbourhood implements INeighborhood<DoubleArraySolution> {

	private double[] deltas;
	Random rand;
	
	/**
	 * DoubleArrayNormNeighbourhood constructor.
	 * @param deltas Delta environment in which we search for a neighbour.
	 */
	public DoubleArrayNormNeighbourhood(double[] deltas) {
		this.deltas=deltas;
		rand=new Random(System.currentTimeMillis());
	}
	
	@Override
	public DoubleArraySolution randomNeighbour(DoubleArraySolution value) {
		DoubleArraySolution neighbour=value.newLikeThis();
		for(int i=0;i<neighbour.values.length;i++){
			double offset=rand.nextGaussian()*deltas[i];
			neighbour.values[i]=value.values[i]+offset;
		}
		return neighbour;
	}
}
