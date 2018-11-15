package hr.fer.zemris.optjava.dz3;

/**
 * PassTroughDecoder class forwards the DoubleArraySolution vector directly at the decoder exit.
 * @author Viran
 *
 */
public class PassTroughDecoder implements IDecoder<DoubleArraySolution>{

	/**
	 * PassTroughDecoder constructor.
	 */
	public PassTroughDecoder() {
	}

	@Override
	public double[] decode(DoubleArraySolution value) {
		return value.values.clone();
	}

	@Override
	public void decode(DoubleArraySolution value, double[] decodedValues) {
		decodedValues=value.values.clone();
	}
	
	
	
}
