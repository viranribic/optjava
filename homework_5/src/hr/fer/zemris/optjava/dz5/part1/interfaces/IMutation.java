package hr.fer.zemris.optjava.dz5.part1.interfaces;

import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

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
	public BitvectorSolution mutate(BitvectorSolution child);
}
