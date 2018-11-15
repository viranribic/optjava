package hr.fer.zemris.optjava.dz5.part2.interfaces;

import hr.fer.zemris.optjava.dz5.part2.solution.QAPSolution;

/**
 * Interface for genetic algorithm mutation operation.
 * @author Viran
 *
 */
public interface IMutation {

	/**
	 * Mutate the given solution.
	 * @param child Subject.
	 * @return Mutated subject.
	 */
	public QAPSolution mutate(QAPSolution child);
}
