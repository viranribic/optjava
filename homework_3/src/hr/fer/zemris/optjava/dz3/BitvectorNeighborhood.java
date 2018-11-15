package hr.fer.zemris.optjava.dz3;

import java.util.Random;

/**
 * BitvectorNeighborhood determination class.
 * @author Viran
 *
 */
public class BitvectorNeighborhood implements INeighborhood<BitvectorSolution> {

	private Random rand;
	private int n;
	private int totalBits;
	/**
	 * BitvectorNeighborhood constructor.
	 * @param bitsPerVariable Number of bits used to describe one variable.
	 * @param totalBitsInVector Total size of bits in evaluated bit vectors.
	 */
	public BitvectorNeighborhood(int bitsPerVariable, int totalBitsInVector) {
		n=bitsPerVariable;
		this.totalBits=totalBitsInVector;
		rand=new Random(System.currentTimeMillis());
	}

	@Override
	public BitvectorSolution randomNeighbour(BitvectorSolution value) {
		BitvectorSolution neighbour=value.duplicate();
		int solutionSpaceDim=totalBits/n;
		for(int i=0;i<solutionSpaceDim;i++){
			int randomPos=i*n+rand.nextInt(n); //change every solution on one place
			if(neighbour.bits[randomPos]==0)
				neighbour.bits[randomPos]=1;
			else
				neighbour.bits[randomPos]=0;
		}
		return neighbour;
	}

}
