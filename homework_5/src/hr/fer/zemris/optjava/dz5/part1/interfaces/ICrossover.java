package hr.fer.zemris.optjava.dz5.part1.interfaces;

import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

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
	public BitvectorSolution crossover(BitvectorSolution parentA, BitvectorSolution parentB);
}
