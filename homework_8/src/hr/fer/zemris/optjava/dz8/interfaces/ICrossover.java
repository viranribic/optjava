package hr.fer.zemris.optjava.dz8.interfaces;


import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;

public interface ICrossover {

	public DifEvSolution crossover(DifEvSolution mutantVector,DifEvSolution targetVector);
}
