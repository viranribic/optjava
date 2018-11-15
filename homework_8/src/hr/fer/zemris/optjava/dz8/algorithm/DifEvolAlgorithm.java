package hr.fer.zemris.optjava.dz8.algorithm;

import java.util.Random;

import hr.fer.zemris.optjava.dz8.algorithm.operators.baseVectorGenerator.BestBaseVectGenerator;
import hr.fer.zemris.optjava.dz8.algorithm.operators.baseVectorGenerator.RandBaseVectGenerator;
import hr.fer.zemris.optjava.dz8.algorithm.operators.crossover.ExponentialCrossover;
import hr.fer.zemris.optjava.dz8.algorithm.operators.crossover.UniformCrossover;
import hr.fer.zemris.optjava.dz8.algorithm.operators.mutation.ExtendedDifMutation;
import hr.fer.zemris.optjava.dz8.algorithm.operators.mutation.SimpleDifMutation;
import hr.fer.zemris.optjava.dz8.algorithm.operators.mutation.TargetToBestDifMutation;
import hr.fer.zemris.optjava.dz8.algorithm.operators.selection.FitSelection;
import hr.fer.zemris.optjava.dz8.algorithm.population.DifEvolPopulation;
import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;
import hr.fer.zemris.optjava.dz8.interfaces.ICrossover;
import hr.fer.zemris.optjava.dz8.interfaces.IFitnessFunction;
import hr.fer.zemris.optjava.dz8.interfaces.IMutation;
import hr.fer.zemris.optjava.dz8.interfaces.ISelection;
import hr.fer.zemris.optjava.dz8.interfaces.IBaseVectorGenerator;
import hr.fer.zemris.optjava.dz8.util.Constants;


@SuppressWarnings("unused")
public class DifEvolAlgorithm<T> {

	private Random rand=new Random(System.currentTimeMillis());
	private IFitnessFunction<T> function;
	private DifEvolPopulation population;
	private double mErr;
	private int maxIter;
	private ICrossover crossoverOp;
	private IMutation mutationOp;
	private ISelection selectionOp;
	private IBaseVectorGenerator trialGenOp;
	
	
	public DifEvolAlgorithm(IFitnessFunction<T> function, int populationSize, double mErr, int maxIter) {
		this.function=function;
		this.population=new DifEvolPopulation(populationSize,function.getParametersCount());
		this.mErr=mErr;
		this.maxIter=maxIter;
		
		//Crossover Operator
		//this.crossoverOp=new ExponentialCrossover(Constants.MUTATE_CROSSOVER_PROBABILITY,rand);
		this.crossoverOp=new UniformCrossover(Constants.MUTATE_CROSSOVER_PROBABILITY, rand);
		
		//Mutation Operator
		//this.mutationOp=new TargetToBestDifMutation(rand);
		this.mutationOp=new SimpleDifMutation(rand);
		//this.mutationOp=new ExtendedDifMutation(rand);
		
		//Selection Operator
		this.selectionOp=new FitSelection();

		//BestBaseVecGen Operator
		//this.trialGenOp=new BestBaseVectGenerator();
		this.trialGenOp=new RandBaseVectGenerator(rand);

	}

	public DifEvSolution run() {
		
		//0.a Init population
		population.initialise(Constants.MIN_INIT_VAL,Constants.MAX_INIT_VAL,function);
		
		//0.b generic loop
		int curIter=0;
		while(curIter++<maxIter){

			// 0.c generateNextPopulation
			DifEvolPopulation nextGeneration=new DifEvolPopulation(population.solutionVectorSize());
			// 0.d for all subjects
			for(DifEvSolution targetVector:population){
				//1. Subject := targetVector
				//2. generate( baseVector ) -> mutate(baseVector) := mutantVector
				DifEvSolution baseVector=generateBaseVector(targetVector);			
				DifEvSolution mutantVector=generateMutantVector(targetVector,baseVector); //O.K.
				//3. crossover( targetVector, mutatedVector) := trialVector
				DifEvSolution trialVector=generateCrossoverVector(targetVector,mutantVector);
				//4. nextPopulation.add( selection( testVector, goalVector) )
				DifEvSolution selectedVector=executeSelection(trialVector,targetVector);
				nextGeneration.addSolution(selectedVector);
			}
			
			this.population=nextGeneration;
			DifEvSolution best=population.getGlobalBest();
			if(best.getFitness()<mErr){
				System.out.println("\nMinimal error reached.\n");
				iterationStatus(curIter);
		
				return best;
			}

			iterationStatus(curIter);
		}
		System.out.println("End.");
		iterationStatus(curIter);
		DifEvSolution best=population.getGlobalBest();
		return best;
	}

	private DifEvSolution executeSelection(DifEvSolution trialVector, DifEvSolution targetVector) {
		trialVector.setFitness(function.valueAt(trialVector.getSolution()));
		//target should be evaluated by now--remove this 
		targetVector.setFitness(function.valueAt(targetVector.getSolution()));
		return selectionOp.select(targetVector,trialVector);
	}

	private DifEvSolution generateCrossoverVector(DifEvSolution targetVector, DifEvSolution mutantVector) {
		return crossoverOp.crossover(mutantVector,targetVector);
	}

	private DifEvSolution generateMutantVector(DifEvSolution targetVector,DifEvSolution baseVector) {
		return mutationOp.mutate(targetVector,baseVector, population);
	}

	private DifEvSolution generateBaseVector(DifEvSolution targetVector) {
		return trialGenOp.generateBaseVector(population,targetVector);
	}

	private void iterationStatus(int iteration){
		System.out.println("Iteration "+iteration);
		System.out.println(population.getGlobalBest());
	}
	
	
}
