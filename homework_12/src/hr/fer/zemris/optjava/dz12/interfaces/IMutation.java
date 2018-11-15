package hr.fer.zemris.optjava.dz12.interfaces;

import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;

public interface IMutation {

	public CLBSolution mutation(CLBSolution child);

}
