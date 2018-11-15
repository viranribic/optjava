package hr.fer.zemris.optjava.dz9.interfaces;

import hr.fer.zemris.optjava.dz9.algorithm.NSGAPopulation;
import hr.fer.zemris.optjava.dz9.algorithm.NSGASolution;

/**
 * Interface for genetic algorithm selection operation.
 * 
 * @author Viran
 *
 */
public interface ISelection {

	/**
	 * Assign a population from which the selection chooses elements.
	 * 
	 * @param population
	 */
	public void setPopulation(NSGAPopulation population);

	/**
	 * Get a random subject from the population.
	 * 
	 * @return Randomly selected subject.
	 */
	public NSGASolution selectRandomSubject();

}
