package hr.fer.zemris.optjava.dz12.util.random;

import java.util.Random;

import hr.fer.zemris.optjava.dz12.interfaces.IRNG;


/**
 * RNGRandomImpl is a random number generator implementation.
 * @author Viran
 *
 */
public class RNGRandomImpl implements IRNG {

	private Random rand;
	
	/**
	 * RNGRandomImpl constructor.
	 */
	public RNGRandomImpl() {
		rand=new Random();
	}
	@Override
	public double nextDouble() {
		return rand.nextDouble();
	}

	@Override
	public double nextDouble(double min, double max) {
		return rand.nextDouble()*(max-min)+min;
	}

	@Override
	public float nextFloat() {
		return rand.nextFloat();
	}

	@Override
	public float nextFloat(float min, float max) {
		return rand.nextFloat()*(max-min)+min;
	}

	@Override
	public int nextInt() {
		return rand.nextInt();
	}

	@Override
	public int nextInt(int min, int max) {
		return rand.nextInt(max-min)+min;
	}

	@Override
	public boolean nextBoolean() {
		return rand.nextBoolean();
	}

	@Override
	public double nextGaussian() {
		return rand.nextGaussian();
	}

}
