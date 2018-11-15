package hr.fer.zemris.optjava.dz4.part2.function;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz4.part2.constants.ConstantsTask2;
import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution;

/**
 * BoxFill problem fitness function.
 * 
 * @author Viran
 *
 */
public class BoxFillFitness2 {
	public LinkedList<Double> stickVlaues;
	public int maxHeight;

	/**
	 * Constructor for the BoxFillFitness, 2nd model.
	 * 
	 * @param functionVlaues
	 *            Sticks lengths in one list.
	 * @param maxHeight
	 *            Maximal allowed height in container.
	 */
	public BoxFillFitness2(LinkedList<Double> stickVlaues, int maxHeight) {
		this.stickVlaues = stickVlaues;
		this.maxHeight = maxHeight;
	}

	/**
	 * Evaluate the fitness of a given solution.
	 * 
	 * @param solution
	 *            Solution in need of evaluation.
	 * @return Solution fitness.
	 */
	public double valueAt(BoxFillSolution solution) {
		double f = 0;
		for (int i = 0; i < solution.getNumOfBins(); i++) {
			f += Math.pow(solution.container.get(i).filledPercentage(), ConstantsTask2.K);
		}
		return f / solution.getNumOfBins();
	}

}
