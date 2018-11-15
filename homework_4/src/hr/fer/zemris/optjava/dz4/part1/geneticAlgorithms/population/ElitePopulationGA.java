package hr.fer.zemris.optjava.dz4.part1.geneticAlgorithms.population;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.part1.constants.ConstantsTask1;
import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

/**
 * Elite population class representation as seen in genetic algorithms.
 * @author Viran
 *
 */
public class ElitePopulationGA {
	
	private int numberOfEliteSubjects;
	private Random rand=new Random();
	private int populationSize;
	private LinkedList<DoubleArraySolution> population=new LinkedList<>();
	private int domainDim;
	
	/**
	 * ElitePopulationGA constructor.
	 * @param populationSize Population size.
	 * @param domainDim Dimension of the domain.
	 * @param minComponentVals Minimal domain value per subject.
	 * @param maxComponentVals Maximal domain value per subject.
	 * @param numberOfEliteSubjects Elite subjects this population should separate.
	 */
	public ElitePopulationGA(int populationSize, int domainDim, double[] minComponentVals,double[] maxComponentVals, int numberOfEliteSubjects ) {
		this.populationSize=populationSize;
		this.domainDim=domainDim;
		this.numberOfEliteSubjects=numberOfEliteSubjects;
		createRandomPop(populationSize,minComponentVals,maxComponentVals);
	}
	
	
	/**
	 * Create a new population from the given subject array.
	 * @param initPopulation Initial population array.
	 * @param numberOfEliteSubjects Elite subjects this population should separate.
	 */
	public ElitePopulationGA(DoubleArraySolution[] initPopulation, int numberOfEliteSubjects){
		this.populationSize=initPopulation.length;
		this.domainDim=initPopulation[0].getDimension();
		this.numberOfEliteSubjects=ConstantsTask1.NUMBER_OF_ELITE_SUBJECTS;
		for(int i=0;i<populationSize;i++)
			this.population.add(initPopulation[i].duplicate());
	}
	
	/**
	 * Generate a random population with values in given boundaries.
	 * @param populationSize Number of population subjects.
	 * @param minComponentVals Minimal domain value per subject.
	 * @param maxComponentVals Maximal domain value per subject.
	 */
	private void createRandomPop(int populationSize, double[] minComponentVals, double[] maxComponentVals) {
		for(int i=0;i<populationSize;i++){
			DoubleArraySolution s=new DoubleArraySolution(domainDim);
			s.randomize(rand, minComponentVals, maxComponentVals);
			population.add(s);
		}
	}
	
	/**
	 * Get subject at position index from population. Changes to the subject are seen in this population.
	 * @param index Index of subject needed.
	 * @return Subject from this population.
	 */
	public DoubleArraySolution getSubjectAtIndex(int index){
		return population.get(index);
	}
	
	/**
	 * Get one random subject from population. Changes to the subject are seen in this population.
	 * @return Random population element.
	 */
	public DoubleArraySolution getRandomSubject(){
		return population.get(rand.nextInt(populationSize));
	}
	
	/**
	 * Get the array of best solution in this population, evaluated by fitness, determinated with numberOfEliteSubjects specified.
	 * @return Array of elite subjects.
	 */
	public DoubleArraySolution[] getEliteSubjects(){
		DoubleArraySolution[] eliteSubjects=new DoubleArraySolution[numberOfEliteSubjects];
		sortThisPopulation();
		for(int i=0;i<numberOfEliteSubjects;i++)
			eliteSubjects[i]=population.get(i).duplicate();
		return eliteSubjects;
	}

	/**
	 * Get population size.
	 * @return Population size.
	 */
	public int getPopulationSize(){
		return populationSize;
	}
	
	/**
	 * Sort given population by fitness.
	 * @param population Population in need of sorting.
	 */
	public void sortThisPopulation(){
		Collections.sort(population);
		//Collections.reverse(population);
	}

	/**
	 * Get number of elite subjects.
	 * @return Number of elite subjects.
	 */
	public int getNumberOfEliteSubjects() {
		return numberOfEliteSubjects;
	}

	/**
	 * Set number of elite subjects.
	 * @param numberOfEliteSubjects New number of elite subjects.
	 */
	public void setNumberOfEliteSubjects(int numberOfEliteSubjects) {
		this.numberOfEliteSubjects = numberOfEliteSubjects;
	}
	
	
}
