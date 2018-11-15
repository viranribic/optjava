package hr.fer.zemris.optjava.dz10.function.util;

import java.util.Comparator;

import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Solution;

/**
 * NSGA2DistComparator compares two solutions by the distance between solutions.
 * @author Viran
 *
 */
public class NSGA2DistComparator implements Comparator<NSGA2Solution> {

	@Override
	public int compare(NSGA2Solution arg0, NSGA2Solution arg1) {
		return Double.compare(arg0.getCrowdingDistance(), arg1.getCrowdingDistance());
	}

}
