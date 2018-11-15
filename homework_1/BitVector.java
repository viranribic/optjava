package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

/**
 * Vector of bits represented as boolean values. It offers an interface for the read-only variable assignment.
 * @author Viran
 *
 */
public class BitVector {
	boolean[] bits;
	/**
	 * Generate a random BitVector.
	 * @param rand Object of Random class which generates the sequence.
	 * @param numberOfBits Size of the vector.
	 */
	public BitVector(Random rand, int numberOfBits){
		bits=new boolean[numberOfBits];
		for(int i=0;i<numberOfBits;i++){
			bits[i]=rand.nextBoolean();
		}
	}
	
	/**
	 * Generate a BitVector representing the given array of bits.
	 * @param bits Array of bits which construct this vector.
	 */
	public BitVector(boolean ...bits){
		this.bits=bits.clone();
	}
	
	/**
	 * Generate an empty BitVector of size n.
	 * @param n BitVector size.
	 */
	public BitVector(int n){
		bits=new boolean[n];
		for(int i=0;i<n;i++)
			bits[i]=false;
	}
	
	/**
	 * Return the bit at position specified by the input argument.
	 * @param index Index of the bit in the field.
	 * @return
	 */
	public boolean get(int index){
		if(index<0 || index>bits.length)
			throw new IndexOutOfBoundsException();
		return bits[index];
		
	}
	
	/**
	 * Returns the size of this vector.
	 * @return Vector size.
	 */
	public int getSize(){
		return bits.length;
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<bits.length;i++)
			if(bits[i])
				sb.append("1");
			else
				sb.append("0");
		return sb.toString();
	}
	
	/**
	 * Make a copy of this vector in the MutableBitVector format.
	 * @return MutableBitVector representation of this vector.
	 */
	public MutableBitVector copy(){
		return new MutableBitVector(bits);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bits);
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
		BitVector other = (BitVector) obj;
		if (!Arrays.equals(bits, other.bits))
			return false;

		
		return true;
	}
	
	
	
}