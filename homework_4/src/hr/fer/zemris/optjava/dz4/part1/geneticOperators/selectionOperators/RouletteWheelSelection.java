package hr.fer.zemris.optjava.dz4.part1.geneticOperators.selectionOperators;

import java.util.Random;

import hr.fer.zemris.optjava.dz4.part1.geneticAlgorithms.population.ElitePopulationGA;
import hr.fer.zemris.optjava.dz4.part1.interfaces.ISelection;
import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

/**
 * Roulette wheel selection class.
 * @author Viran
 *
 */
public class RouletteWheelSelection implements ISelection {

	private Random rand=new Random();
	private ElitePopulationGA population;
	private double[] subjectProbabilities;
	private double[] scaledFitness;
	@Override
	public void setPopulation(ElitePopulationGA population){
		this.population=population;
		resolveScaleProblem();
		calculateProbabilities();
	}
	
	@Override
	public DoubleArraySolution selectRandomSubject(){
		double x=rand.nextDouble();
		double lowerBound=0;
		double upperBound=0;
		
		int size=population.getPopulationSize();
		for(int i=0;i<size;i++){
			upperBound+=subjectProbabilities[i];
			if(lowerBound<x && x<upperBound){
				return population.getSubjectAtIndex(i).duplicate();
			}else{
				lowerBound=upperBound;
			}
		}
		return null;
	}
	
	/**
	 * Calculate the probabilities of a particular subject being selected.
	 */
	private void calculateProbabilities() {
		int size=population.getPopulationSize();
		subjectProbabilities=new double[size];
		double totalFitness=0;
		for(int i=0;i<size-1;i++){
			totalFitness+=scaledFitness[i];
		}
		for(int i=0;i<size;i++){
			subjectProbabilities[i]=scaledFitness[i]/totalFitness;
		}
	}

	/**
	 * Resolves the scaling problem, decreasing each fitness with the minimal population fitness.
	 */
	private void resolveScaleProblem(){
		population.sortThisPopulation();
		int size=population.getPopulationSize();
		DoubleArraySolution worstSubject=population.getSubjectAtIndex(size-1);
		
		double fWorst=worstSubject.fitness;
		
		scaledFitness=new double[size];
		
		//rescale for the whole population
		for(int i=0;i<size;i++){
			DoubleArraySolution subj=population.getSubjectAtIndex(i);
			if(subj.fitness>1)
				scaledFitness[i]=1/subj.fitness;
			else
				scaledFitness[i]=Math.abs(fWorst-subj.fitness);
		}
	}
}
