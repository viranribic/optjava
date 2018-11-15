package hr.fer.zemris.optjava.dz5.part1.geneticAlgorithms.population;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

/**
 * Relevant Alleles Preserving  Genetic Algorithm population class.
 * @author Viran
 *
 */
public class RAPGAPopulation {
	private Random rand=new Random();
	private int minPopSize;
	private int maxPopSize;
	private HashSet<BitvectorSolution> popSolutions=new HashSet<>();
	
	/**
	 * RAPGAPopulation constructor.
	 * @param minPopSize Minimal allowed population size.
	 * @param maxPopSize Maximal allowed population size.
	 */
	public RAPGAPopulation(int minPopSize, int maxPopSize) {
		this.minPopSize=minPopSize;
		this.maxPopSize=maxPopSize;
	}
	
	
	/**
	 * Check if the current population size reached its maximum.
	 * @return True if the population size is maximal, false otherwise.
	 */
	public boolean maximalSizeReacher(){
		return (popSolutions.size()==maxPopSize)?true:false;
	}
	
	/**
	 * Return true if the population size is lower than minimal allowed.
	 * @return True if the population size has not reached the minimal allowed size.
	 */
	public boolean sizeTooLow(){
		return (popSolutions.size()<minPopSize)?true:false;
	}
	/**
	 * Add the given solution to this population.
	 * @param solution New solution we want to add.
	 * @return True if the added successfully, false in case maximal population size has been reached, or the element is a duplicate.
	 */
	public boolean addSolution(BitvectorSolution solution){
		if(popSolutions.size()==maxPopSize)
			return false;
		return popSolutions.add(solution);
	}

	/**
	 * Minimal allowed population size.
	 * @return Minimal population size.
	 */
	public int getMinimalPopulationSize() {
		return minPopSize;
	}
	
	/**
	 * Maximal allowed population size.
	 * @return Maximal population size.
	 */
	public int getMaximalPopulationSize() {
		return maxPopSize;
	}

	/**
	 * Get one random subject.
	 * @return Random subject from population.
	 */
	public BitvectorSolution getRandomSubject() {
		LinkedList<BitvectorSolution> tmp=new LinkedList<>(popSolutions);
		return tmp.get(rand.nextInt(tmp.size()));
	}

	/**
	 * Return the best fitness in this population.
	 * @return Best fitness.
	 */
	public double getBestFitness() {
		LinkedList<BitvectorSolution> list=new LinkedList<>(popSolutions);
		Collections.sort(list);
		Collections.reverse(list);
		double f=0;
		if(list.size()!=0)
			f=list.getFirst().getFitness();
		return f;
	}


	/**
	 * Get current size.
	 * @return Current size.
	 */
	public int currentPopulationSize() {
		return popSolutions.size();
	}



	/**
	 * Retrieve best solution from population.
	 * @return Best population solution.
	 */
	public BitvectorSolution getBestSolution() {
		LinkedList<BitvectorSolution> list=new LinkedList<>(popSolutions);
		Collections.sort(list);
		Collections.reverse(list);
		BitvectorSolution sol=null;
		if(list.size()!=0)
			sol=list.getFirst();
		return sol;
	}
	
	
}
