package hr.fer.zemris.optjava.dz5.part2.interfaces;

import hr.fer.zemris.optjava.dz5.part2.solution.QAPSolution;

/**
 * Interface for genetic algorithm crossover operator.
 * @author Viran
 *
 */
public interface ICrossover {
	/**
	 * Perform a crossover operation..
	 * @param parentA First parent.
	 * @param parentB Second parent.
	 * @return Resulting child.
	 */
	public QAPSolution[] crossover(QAPSolution parentA, QAPSolution parentB);
}
