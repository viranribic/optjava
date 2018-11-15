package hr.fer.zemris.optjava.dz10.interfaces;

import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Population;
import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Solution;

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
	public void setPopulation(NSGA2Population population);

	/**
	 * Get a random subject from the population.
	 * 
	 * @return Randomly selected subject.
	 */
	public NSGA2Solution selectRandomSubject();

}
