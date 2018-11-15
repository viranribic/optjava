package hr.fer.zemris.optjava.dz11.util;

/**
 * Contains methods to convert between Gray code and natural binary values.
 * @author Viran
 */
public class GrayCoder {
	
	/**
	 * Converts a natural binary 32 bit value to Gray code.
	 * @param natural The natural binary value to convert.
	 * @return The Gray code equivalent value.
	 */
	public static int encodeGray(int natural) {
		return natural ^ natural >>> 1;
	}

	/**
	 * Converts a Gray code 32 bit value to natural binary.
	 * @param gray The Gray code value to convert.
	 * @return The natural binary equivalent value.
	 */
	public static int decodeGray(int gray) {
		int natural = 0;
		while (gray != 0) {
			natural ^= gray;
			gray >>>= 1;
		}
		return natural;
	}

	/**
	 * Converts a natural binary 64 bit value to Gray code.
	 * @param natural The natural binary value to convert.
	 * @return The Gray code equivalent value.
	 */
	public static long encodeGray(long natural) {
		return natural ^ natural >>> 1;
	}

	/**
	 * Converts a Gray code 64 bit value to natural binary.
	 * @param gray The Gray code value to convert.
	 * @return The natural binary equivalent value.
	 */
	public static long decodeGray(long gray) {
		long natural = 0;
		while (gray != 0) {
			natural ^= gray;
			gray >>>= 1;
		}
		return natural;
	}
}
