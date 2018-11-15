package hr.fer.zemris.trisat;

/**
 * Vector of bits represented as boolean values. It offers an interface for the mutable variable assignment.
 * @author Viran
 *
 */
public class MutableBitVector extends BitVector{

	/**
	 * Generate a MutableBitVector representing the given array of bits.
	 * @param bits Array of bits which construct this vector.
	 */
	public MutableBitVector(boolean... bits) {
		super(bits);
	}
	
	/**
	 * Generate a 32 bit vector with the sequence equivalent to the binary representation of the given integer value.
	 * @param n BitVector given as an decad value.
	 */
	public MutableBitVector(int n) {
		super(n);
	}

	/**
	 * Sets the value of this vector at the given index to the specified value.
	 * @param index Position of bit to be changed.
	 * @param value New value of the changed bit.
	 */
	public void set(int index, boolean value) {
		bits[index]=value;
	}
}
