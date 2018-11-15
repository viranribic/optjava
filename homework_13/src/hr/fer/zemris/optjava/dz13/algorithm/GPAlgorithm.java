package hr.fer.zemris.optjava.dz13.algorithm;

import java.util.Random;

import hr.fer.zemris.optjava.dz13.algorithm.operators.Crossover;
import hr.fer.zemris.optjava.dz13.algorithm.operators.Evaluation;
import hr.fer.zemris.optjava.dz13.algorithm.operators.Mutation;
import hr.fer.zemris.optjava.dz13.algorithm.operators.Reproduction;
import hr.fer.zemris.optjava.dz13.algorithm.operators.Selection;
import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.util.AlgConst;
import hr.fer.zemris.optjava.dz13.util.GPPopulation;
import hr.fer.zemris.optjava.dz13.util.InitGPPopulation;

/**
 * TODO Swing
*/


public class GPAlgorithm {

	private Random rand=new Random();
	private GPPopulation population;
	private Selection selection;
	private Crossover crossover;
	private Mutation mutation;
	private Reproduction reproduction;
	private Evaluation evaluation;
	private int maxGeneration;
	private int minimalFit;
	
	
	public GPAlgorithm(String mapPath, int maxGeneration, int popSize, int minimalFit) {
		this.population=InitGPPopulation.genInitialPopulation(popSize, AlgConst.INIT_MAX_DEPTH, AlgConst.MAX_NODE_COUNT);
		this.selection=new Selection(AlgConst.TOURNAMENT_SIZE);
		this.crossover=new Crossover();
		this.mutation=new Mutation();
		this.reproduction=new Reproduction();
		this.evaluation=new Evaluation(mapPath);
		for(GPSolution solution:population)
			evaluation.evaluation(solution);
		this.maxGeneration=maxGeneration;
		this.minimalFit=minimalFit;
		
	}

	public GPSolution run() {
		int iter=0;
		while(iter<maxGeneration){
			GPPopulation nextPopulation= new  GPPopulation();
			while(nextPopulation.size()<population.size()){
				double opProbability=rand.nextDouble();
				
				GPSolution child=null;
				
				if(0<=opProbability && opProbability<AlgConst.REPRODUCTION_PROBABILITY){
					//System.out.println("REP.");
					child=reproduction.reproduction(population);
					
				}else if(AlgConst.REPRODUCTION_PROBABILITY<=opProbability && opProbability<AlgConst.MUTATION_PROBABILITY+AlgConst.REPRODUCTION_PROBABILITY){
					//System.out.println("MUT.");
					GPSolution parentA=selection.select(population);
					while(child==null)
						child=mutation.mutate(parentA);
					evaluation.evaluation(child);
					plagiarismPenalty(parentA,child);

					
				}else if(AlgConst.MUTATION_PROBABILITY+AlgConst.REPRODUCTION_PROBABILITY<=opProbability && opProbability<1){
					//System.out.println("XOVER.");
					GPSolution parentA=selection.select(population);
					GPSolution parentB=selection.select(population);
					while(child==null)
						child=crossover.crossover(parentA, parentB);	
					evaluation.evaluation(child);
					plagiarismPenalty(parentA,child);
					
				}
				nextPopulation.add(child);
			}
			
			population=mergePopulations(nextPopulation,population);
			if(population.getGlobalBest().getFitness()>=minimalFit)
				break;
			iter++;
			System.out.println("Iteration: "+iter+" BestSol: "+population.getGlobalBest());
		}
		
		return population.getGlobalBest();
	}

	private GPPopulation mergePopulations(GPPopulation nextPopulation, GPPopulation population) {
		if(nextPopulation.getGlobalBest().getFitness()<population.getGlobalBest().getFitness()){
			nextPopulation.remove(nextPopulation.getGlobalBest());
			nextPopulation.add(population.getGlobalBest());
		}
		return nextPopulation;
	}

	private void plagiarismPenalty(GPSolution parentA, GPSolution child) {
		if(parentA.getFoodCollected()==child.getFoodCollected())
			child.setFitness(AlgConst.PLAG_PEN_VAL*child.getFitness());
	}

}
