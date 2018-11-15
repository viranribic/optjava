package hr.fer.zemris.optjava.dz8.interfaces;

import hr.fer.zemris.optjava.dz8.algorithm.population.DifEvolPopulation;
import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;

public interface IBaseVectorGenerator {

	public DifEvSolution generateBaseVector(DifEvolPopulation population, DifEvSolution targetVector);
}
