package hr.fer.zemris.optjava.dz8.algorithm.operators.mutation;

import java.util.Random;

import hr.fer.zemris.optjava.dz8.algorithm.population.DifEvolPopulation;
import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;
import hr.fer.zemris.optjava.dz8.interfaces.IMutation;
import hr.fer.zemris.optjava.dz8.util.Constants;

public class SimpleDifMutation implements IMutation {
	protected static final int RANDOM_ELEMENTS=2;
	protected static double F;
	protected Random rand;
	
	public SimpleDifMutation(Random rand) {
		this.rand=rand;
		F=Constants.F *rand.nextDouble() ;
	}
	
	@Override
	public DifEvSolution mutate(DifEvSolution targetVector, DifEvSolution baseVector, DifEvolPopulation population) {
		DifEvSolution mutant;
		DifEvSolution[] randomVectors=new DifEvSolution[RANDOM_ELEMENTS+2];
		randomVectors[0]=targetVector;
		randomVectors[1]=baseVector;
		for(int i=2;i<RANDOM_ELEMENTS+2;i++)
			randomVectors[i]=generateUniqueVector(randomVectors,population,rand);
		
		DifEvSolution diference;
		diference=differentiate(randomVectors[2],randomVectors[3]);
		diference=multiply(diference,F);
		mutant=add(diference,randomVectors[1]);
		return mutant;
	}

	protected DifEvSolution add(DifEvSolution diference, DifEvSolution baseVector) {
		DifEvSolution sum=new DifEvSolution(baseVector.getDimension());
		double[] sumVec=sum.getSolution();
		double[] difVec=diference.getSolution();
		double[] baseVec=baseVector.getSolution();
		
		for(int i=0;i<sumVec.length;i++){
			sumVec[i]=difVec[i]+baseVec[i];
		}
		return sum;
	}

	protected DifEvSolution multiply(DifEvSolution multiplied, double F) {
		DifEvSolution mul=new DifEvSolution(multiplied.getDimension());
		double[] differenceVecotr=multiplied.getSolution();
		double[] mulVecotr=mul.getSolution();
		
		for(int i=0;i<mulVecotr.length;i++){
			mulVecotr[i]=differenceVecotr[i]*F;
		}
		return mul;
	}

	protected DifEvSolution differentiate(DifEvSolution solutionA, DifEvSolution solutionB) {
		DifEvSolution dif=new DifEvSolution(solutionA.getDimension());
		double[] difVecotr=dif.getSolution();
		double[] aVecotr=solutionA.getSolution();
		double[] bVecotr=solutionB.getSolution();
		
		for(int i=0;i<difVecotr.length;i++){
			difVecotr[i]=aVecotr[i]-bVecotr[i];
		}
		return dif;
	}

	protected DifEvSolution generateUniqueVector(DifEvSolution[] randomVectors,  DifEvolPopulation population, Random rand) {
		while(true){
			int randPos=rand.nextInt(population.size());
			DifEvSolution candidate=population.getSolution(randPos);
			for(int i=0;i<randomVectors.length;i++){
				if(randomVectors[i]==null)
					return candidate.duplicate();
				if(randomVectors[i].equals(candidate))
					continue;
			}
			return candidate.duplicate();
		}	
	}



}
