package hr.fer.zemris.optjava.dz11.interfaces;

import java.util.LinkedList;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;

/**
 * Interface for genetic algorithm selection operation.
 * @author Viran
 *
 */
public interface ISelectionOp {

	/**
	 * Assign a population from which the selection chooses elements.
	 * @param population
	 */
	public void setPopulation(LinkedList<GASolution<int[]>> population);
	
	/**
	 * Get a random subject from the population.
	 * @return Randomly selected subject.
	 */
	public GASolution<int[]> selectRandomSubject(IRNG rand);
	
}
