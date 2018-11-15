package hr.fer.zemris.optjava.dz10.function.util;

import java.util.Comparator;

import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Solution;

/**
 * DimComparator class implements Comparable interface for NSGA2Solution objects.
 * For the dimension provided in constructor, this class compares two solutions by vector values at specified positions.
 * @author Viran
 *
 */
public class NSGA2DimComparator implements Comparator<NSGA2Solution> {

	private int dimCompared;

	/**
	 * DimComparator constructor.
	 * @param dimCompared Dimension of comparison.
	 */
	public NSGA2DimComparator(int dimCompared) {
		this.dimCompared=dimCompared;
	}
	
	@Override
	public int compare(NSGA2Solution arg0, NSGA2Solution arg1) {
		if(arg0.getObjectiveDimension()<dimCompared)
			throw new RuntimeException("Dimension comparing failed. Requested dimension comparison is larger than the solution's vector given.");
		return Double.compare(arg0.getObjSolValues()[dimCompared], arg1.getObjSolValues()[dimCompared]);
	}

}
