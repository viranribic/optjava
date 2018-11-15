package hr.fer.zemris.optjava.dz8.algorithm.operators.baseVectorGenerator;

import java.util.Random;

import hr.fer.zemris.optjava.dz8.algorithm.population.DifEvolPopulation;
import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;
import hr.fer.zemris.optjava.dz8.interfaces.IBaseVectorGenerator;

public class RandBaseVectGenerator implements IBaseVectorGenerator {
	
	private Random rand;

	public RandBaseVectGenerator(Random rand) {
		this.rand=rand;
	}
	
	@Override
	public DifEvSolution generateBaseVector(DifEvolPopulation population, DifEvSolution targetVector) {
		while(true){
			int pos=rand.nextInt(population.size());
			DifEvSolution randCandidate=population.getSolution(pos);
			if(randCandidate.equals(targetVector))
				continue;
			else
				return randCandidate.duplicate();
		}
	}

}
