package hr.fer.zemris.optjava.dz3;

/**
 * Natural binary decoder.
 * @author Viran
 *
 */
public class NaturalBinaryDecoder extends BitvectorDecoder {

	/**
	 * NaturalBinaryDecoder constructor.
	 * @param mins Lower boundaries of decoding interval. 
	 * @param maxs Lower boundaries of decoding interval.
	 * @param bits Integer representation of bit vectors.
	 * @param n Size of one bit vector.
	 */
	public NaturalBinaryDecoder(double[]mins, double[] maxs, int[] bits,int n) {
		super(mins,maxs,bits,n);
	}

	/**
	 * NaturalBinaryDecoder constructor.
	 * @param mins Lower boundary of decoding interval. 
	 * @param maxs Lower boundary of decoding interval.
	 * @param n Number of bits per value.
	 * @param totalBits Number of total bits.
	 */
	public NaturalBinaryDecoder(double mins,double maxs,int n,int totalBits) {
		super(mins,maxs,n,totalBits);
	}
	
	
	@Override
	public double[] decode(BitvectorSolution codedInput) {
		int size=totalBits/n;
		double[] decoded=new double[n];
		
		for(int i=0;i<n;i++){
			//extrude one variable
			//bin->dec
			int start=i*size;
			int end=(i+1)*size;
			int k=bin2dec(extrudeVar(codedInput.bits,start,end));
			//dec->double
				decoded[i]=mins[i]+(maxs[i]-mins[i])*k/(Math.pow(2, size));
		}
		
		return decoded;
	}
	
	/**
	 * Transform binary code to decimal value.
	 * @param binCode Input binary.
	 * @return Output integer.
	 */
	private int bin2dec(byte[] binCode) {
		int codeLength = binCode.length;
		int res=0;
		int pow=0;
		for(int i=codeLength-1;i>=0;i--){
			res=(binCode[i]==1)?(int)(res+ Math.pow(2, pow)):res;
			pow++;
		}
		return res;
	}

	/**
	 * Extruded one variable from the bit array from starting to finishing position.
	 * @param bits Original bit vector.
	 * @param start Starting position;
	 * @param end End position.
	 * @return Extruded bit vector.
	 */
	private byte[] extrudeVar(byte[] bits, int start, int end) {
		byte[] res=new byte[end-start];
		int j=0;
		for(int i=start;i<end;i++){
			res[j]=bits[i];
			j++;
		}
		return res;
	}

	@Override
	public void decode(BitvectorSolution codedInput, double[] decodedInput) {
		decodedInput=decode(codedInput);
	}

}
