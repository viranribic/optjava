package hr.fer.zemris.optjava.dz4.part2.geneticAlgorithms.population;

import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution;

/**
 * Elite population class representation as seen in genetic algorithms.
 * 
 * @author Viran
 *
 */
public class BoxFillPopulation {

	private int curPopSize = 0;
	private Random rand = new Random();
	private int maxPopulationSize;
	public LinkedList<BoxFillSolution> population = new LinkedList<>();

	/**
	 * ElitePopulationGA constructor.
	 * 
	 * @param populationSize
	 *            Population size.
	 * @param domainDim
	 *            Dimension of the domain.
	 * @param minComponentVals
	 *            Minimal domain value per subject.
	 * @param maxComponentVals
	 *            Maximal domain value per subject.
	 * @param numberOfEliteSubjects
	 *            Elite subjects this population should separate.
	 */
	public BoxFillPopulation(int populationSize) {
		this.maxPopulationSize = populationSize;
	}

	/**
	 * Create a new population from the given subject array.
	 * 
	 * @param initPopulation
	 *            Initial population array.
	 * @param numberOfEliteSubjects
	 *            Elite subjects this population should separate.
	 */
	public BoxFillPopulation(LinkedList<BoxFillSolution> population, int numberOfEliteSubjects) {
		this.population = population;
		this.maxPopulationSize = population.size();
	}

	/**
	 * Get subject at position index from population. Changes to the subject are
	 * seen in this population.
	 * 
	 * @param index
	 *            Index of subject needed.
	 * @return Subject from this population.
	 */
	public BoxFillSolution getSubjectAtIndex(int index) {
		return population.get(index);
	}

	/**
	 * Get one random subject from population. Changes to the subject are seen
	 * in this population.
	 * 
	 * @return Random population element.
	 */
	public BoxFillSolution getRandomSubject() {
		return population.get(rand.nextInt(maxPopulationSize));
	}

	/**
	 * Get population size.
	 * 
	 * @return Population size.
	 */
	public int getPopulationSize() {
		return maxPopulationSize;
	}

	/**
	 * Add this solution to population;
	 * 
	 * @param solution
	 *            new solution;
	 */
	public void addSolution(BoxFillSolution solution) {
		if (curPopSize >= maxPopulationSize)
			throw new RuntimeException("Population full, unable to add.");
		curPopSize++;
		population.add(solution);

	}

}
