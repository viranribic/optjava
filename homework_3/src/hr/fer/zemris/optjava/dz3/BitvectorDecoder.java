package hr.fer.zemris.optjava.dz3;

/**
 * Abstract bit vector decoder.
 * @author Viran
 *
 */

public abstract class BitvectorDecoder implements IDecoder<BitvectorSolution> {

	protected double[] mins;
	protected double[] maxs;
	protected int[] bits;
	protected int n;
	protected int totalBits;
	
	/**
	 * BitvectorDecoder constructor.
	 * @param mins Lower boundaries of decoding interval. 
	 * @param maxs Lower boundaries of decoding interval.
	 * @param bits Initial bit vector value.
	 * @param n Number of bits per variable.
	 */
	public BitvectorDecoder(double[]mins, double[] maxs,int[] bits, int n) {
		if(bits.length/n!=(int)(bits.length/n))
			throw new IllegalArgumentException("Total bits must be dividable with the binary encoding of one variable.");
		this.mins=mins.clone();
		this.maxs=maxs.clone();
		this.bits=bits.clone();
		this.n=n;	
		this.totalBits=bits.length;
	}
	
	/**
	 * BitvectorDecoder constructor.
	 * @param mins Lower boundary of decoding interval. 
	 * @param maxs Lower boundary of decoding interval.
	 * @param n Number of bits per variable.
	 * @param totalBits Number of bits in total.
	 */
	public BitvectorDecoder(double mins, double maxs, int n, int totalBits){
		this.mins= new double[totalBits];
		for(int i=0;i<totalBits;i++)
			this.mins[i]=mins;
		this.maxs= new double[totalBits];
		for(int i=0;i<totalBits;i++)
			this.maxs[i]=maxs;
		this.bits=new int[totalBits];
		this.n=n;
		this.totalBits=totalBits;
	}
	
	/**
	 * Number of total bits in this vector.
	 * @return Get total bits needed for a successful decoding process.
	 */
	public int getTotalBits() {
		return totalBits;
	}
	
	/**
	 * Number of bits per variable.
	 * @return Dimension of one variable problem.
	 */
	public int getDimensions(){
		return n;
	}
	
	/**
	 * Decode the given bit vector to a function domain element.
	 * @param codedInput Coded bit vector.
	 * @return Double vector of the function domain.
	 */
	public abstract double[] decode(BitvectorSolution codedInput);
	
	/**
	 * Decode the given bit vector to a function domain element.
	 * @param codedInput Coded bit vector.
	 * @param decodedInput Double vector of the function domain.
	 */
	public abstract void decode(BitvectorSolution codedInput,double[] decodedInput);
	
	
}
