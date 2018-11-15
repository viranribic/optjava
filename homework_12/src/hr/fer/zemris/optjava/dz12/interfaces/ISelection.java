package hr.fer.zemris.optjava.dz12.interfaces;


import hr.fer.zemris.optjava.dz12.algorithm.population.CLBSolPopulation;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;


/**
 * Interface for genetic algorithm selection operation.
 * @author Viran
 *
 */
public interface ISelection {


	public CLBSolution select(CLBSolPopulation population);
	
}
