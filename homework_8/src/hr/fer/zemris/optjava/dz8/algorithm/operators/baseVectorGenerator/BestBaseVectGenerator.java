package hr.fer.zemris.optjava.dz8.algorithm.operators.baseVectorGenerator;

import hr.fer.zemris.optjava.dz8.algorithm.population.DifEvolPopulation;
import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;
import hr.fer.zemris.optjava.dz8.interfaces.IBaseVectorGenerator;

public class BestBaseVectGenerator  implements IBaseVectorGenerator{

	public BestBaseVectGenerator() {
	}
	
	@Override
	public DifEvSolution generateBaseVector(DifEvolPopulation population, DifEvSolution targetVector) {
		return population.getGlobalBest().duplicate();
	}


}
