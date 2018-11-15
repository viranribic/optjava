package hr.fer.zemris.optjava.dz4.part1.interfaces;

import hr.fer.zemris.optjava.dz4.part1.geneticAlgorithms.population.ElitePopulationGA;
import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

/**
 * Interface for genetic algorithm selection operation.
 * @author Viran
 *
 */
public interface ISelection {

	/**
	 * Assign a population from which the selection chooses elements.
	 * @param population
	 */
	public void setPopulation(ElitePopulationGA population);
	
	/**
	 * Get a random subject from the population.
	 * @return Randomly selected subject.
	 */
	public DoubleArraySolution selectRandomSubject();
	
}
