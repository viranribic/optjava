package hr.fer.zemris.optjava.dz3;

import java.util.Random;

/**
 * Neighbourhood generator for a solution represented with a array of double values using uniform distribution.
 * @author Viran
 *
 */
public class DoubleArrayUnifNeighborhood implements INeighborhood<DoubleArraySolution> {

	private double[] deltas;
	Random rand;

	/**
	 * DoubleArrayUnifNeighborhood constructor.
	 * @param deltas Delta environment in which we search for a neighbour.
	 */
	public DoubleArrayUnifNeighborhood(double[] deltas) {
		this.deltas=deltas;
		rand=new Random(System.currentTimeMillis());
	}

	@Override
	public DoubleArraySolution randomNeighbour(DoubleArraySolution value) {
		DoubleArraySolution neighbour=value.newLikeThis();
		for(int i=0;i<neighbour.values.length;i++){
			double offset=rand.nextDouble()*deltas[i];
			offset=(rand.nextBoolean())?offset:-offset;	
			neighbour.values[i]=value.values[i]+offset;
		}
		return neighbour;
	}

}
