package hr.fer.zemris.optjava.dz3;

/**
 * Temperature schedule implementation using geometric progression.
 * @author Viran
 *
 */
public class GeomtericTempSchedule implements ITempSchedule {

	private double alpha;
	private double tInitial;
	private double tCurrent;
	private int innerLimit;
	private int outerLimit;
	
	/**
	 * GeomtericTempSchedule constructor.
	 * @param alpha Common ratio of geometric sequence.
	 * @param tInitial Initial temperature.
	 * @param innerLimit Inner 
	 * @param outerLimit
	 */
	public GeomtericTempSchedule(double alpha, double tInitial,int innerLimit, int outerLimit) {
		if(alpha<=0 || alpha>=1)
			throw new IllegalArgumentException("GeomtericTempSchedule-Common ratio coefficient must be between 0 and 1 excluded.");
		this.alpha=alpha;
		this.tInitial=tInitial;
		this.tCurrent=this.tInitial;
		this.innerLimit=innerLimit;
		this.outerLimit=outerLimit;
	}
	
	@Override
	public double getNextTemperature() {
		double out=tCurrent;
		tCurrent=alpha*tCurrent;
		return out;
	}

	@Override
	public int getInnerLoopCounter() {
		return innerLimit;
	}

	@Override
	public int getOuterLoopCounter() {
		return outerLimit;
	}

}
