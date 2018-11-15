package hr.fer.zemris.optjava.dz6.solution;

import java.util.Arrays;

/**
 * Class representing a travelling salesman problem solution.
 * 
 * @author Viran
 *
 */
public class TSPSolution {

	public int[] nodeIndexes;
	public double tourLength;

	@Override
	public String toString() {
		return Arrays.toString(nodeIndexes)+", len= "+tourLength;
	}
}
