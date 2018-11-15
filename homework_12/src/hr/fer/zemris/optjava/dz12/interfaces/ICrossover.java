package hr.fer.zemris.optjava.dz12.interfaces;

import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;

public interface ICrossover {

	public CLBSolution crossover(CLBSolution parentA, CLBSolution parentB);

}
