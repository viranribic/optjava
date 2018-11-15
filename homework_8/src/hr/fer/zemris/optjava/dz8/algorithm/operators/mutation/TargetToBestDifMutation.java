package hr.fer.zemris.optjava.dz8.algorithm.operators.mutation;

import java.util.Random;

import hr.fer.zemris.optjava.dz8.algorithm.population.DifEvolPopulation;
import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;
public class TargetToBestDifMutation extends SimpleDifMutation{

	protected static final int RANDOM_ELEMENTS=2;
	
	public TargetToBestDifMutation(Random rand) {
		super(rand);
	}
	
	@Override
	public DifEvSolution mutate(DifEvSolution targetVector, DifEvSolution baseVector, DifEvolPopulation population) {
		DifEvSolution mutant;
		DifEvSolution gBestVector=baseVector;
		DifEvSolution[] randomVectors=new DifEvSolution[RANDOM_ELEMENTS+2];
		randomVectors[0]=targetVector;
		randomVectors[1]=gBestVector;
		
		for(int i=2;i<RANDOM_ELEMENTS+2;i++)
			randomVectors[i]=generateUniqueVector(randomVectors,population,rand);
		
		DifEvSolution diference1;
		diference1=differentiate(gBestVector,targetVector);
		diference1=multiply(diference1,F);
		
		DifEvSolution diference2;
		diference2=differentiate(randomVectors[2],randomVectors[3]);
		diference2=multiply(diference2,F);
		
		DifEvSolution mutant1;
		mutant1=add(diference1,diference2);
		mutant=add(targetVector,mutant1);
		return mutant;
	}

}
