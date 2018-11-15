package hr.fer.zemris.optjava.dz5.part1.interfaces;

import hr.fer.zemris.optjava.dz5.part1.geneticAlgorithms.population.RAPGAPopulation;
import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

/**
 * Interface for genetic algorithm selection operation.
 * @author Viran
 *
 */
public interface ISelection {

	/**
	 * Assign a population from which the selection chooses elements.
	 * @param population Population.
	 */
	public void setPopulation(RAPGAPopulation population);
	
	/**
	 * Get a random subject from the population.
	 * @return Randomly selected subject.
	 */
	public BitvectorSolution select();

	
}
