package hr.fer.zemris.optjava.dz5.part2.interfaces;

import java.util.LinkedList;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part2.solution.QAPSolution;

/**
 * Interface for genetic algorithm selection operation.
 * @author Viran
 *
 */
public interface ISelection {

	/**
	 * Assign a population from which the selection chooses elements.
	 * @param curPopulation Population.
	 */
	public void setPopulation(Set<QAPSolution> curPopulation);
	
	/**
	 * Get a random subject from the population.
	 * @return Randomly selected subject.
	 */
	public QAPSolution select();

	
}
