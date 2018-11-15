package hr.fer.zemris.optjava.dz3;

import java.util.Random;
/**
 * Single solution represented with a bitvector.
 * @author Viran
 *
 */
public class BitvectorSolution extends SingleObjectSolution {

	public byte[] bits;
	
	/**
	 * BitvectorSolution constructor.
	 * @param n Dimension of the bit vector.
	 */
	public BitvectorSolution(int n) {
		bits=new byte[n];
	}
	
	/**
	 * Returns a new solution with the same dimension.
	 * @return Solution of the same dimension.
	 */
	public BitvectorSolution newLikeThis(){
		return new BitvectorSolution(bits.length);
		
	}
	
	/**
	 * Duplicates this object in terms of values and fitness attributes.
	 * @return A duplicate of this object.
	 */
	public BitvectorSolution duplicate(){
		BitvectorSolution duplicate=new BitvectorSolution(bits.length);
		duplicate.bits=bits.clone();
		return duplicate;
	}
	
	
	
	/**
	 * Randomises this solution on all bit.
	 * @param random Pseudorandom number generator.
	 */
	public void randomize(Random random){
		for(int i=0;i<bits.length;i++){
			bits[i]=(random.nextBoolean())?(byte)1:(byte)0;
		}
	}
}
