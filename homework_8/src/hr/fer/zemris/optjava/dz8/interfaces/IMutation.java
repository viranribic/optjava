package hr.fer.zemris.optjava.dz8.interfaces;


import hr.fer.zemris.optjava.dz8.algorithm.population.DifEvolPopulation;
import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;

public interface IMutation {

	DifEvSolution mutate(DifEvSolution targetVector,DifEvSolution baseVector, DifEvolPopulation population);

}
