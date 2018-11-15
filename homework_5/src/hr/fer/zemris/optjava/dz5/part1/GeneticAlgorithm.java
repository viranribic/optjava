package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.part1.constants.Constants;
import hr.fer.zemris.optjava.dz5.part1.geneticAlgorithms.RAPGA;
import hr.fer.zemris.optjava.dz5.part1.interfaces.IOptAlgorithm;

/**
 * Main class for solving modified Max-Ones problem.
 * @author Viran
 *
 */
public class GeneticAlgorithm {

	public static void main(String[] args) {
		if(args.length!=1)
			throw new IllegalArgumentException("Input arguments are not valid.");
		int n=Integer.parseInt(args[0]);
		
		IOptAlgorithm algorithm=new RAPGA(n,Constants.MIN_POP_SIZE,Constants.MAX_POP_SIZE,Constants.MAX_SELECTION_PRESSURE, Constants.COMPARISON_FACTOR);
		algorithm.run();
	}
}
