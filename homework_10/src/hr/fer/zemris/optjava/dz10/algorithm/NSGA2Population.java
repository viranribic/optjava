
package hr.fer.zemris.optjava.dz10.algorithm;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * NSGA population class representation as seen in genetic algorithms.
 * 
 * @author Viran
 *
 */
public class NSGA2Population implements Iterable<NSGA2Solution> {

	private Random rand = new Random();
	private LinkedList<NSGA2Solution> population = new LinkedList<>();
	private int domainDim;
	private int objDim;

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
	 */
	public NSGA2Population(int populationSize, int domainDim, int objDim, double[] minComponentVals,
			double[] maxComponentVals) {
		this.domainDim = domainDim;
		this.objDim = objDim;
		createRandomPop(populationSize, minComponentVals, maxComponentVals);
	}

	/**
	 * Create a new population from the given subject array.
	 * 
	 * @param initPopulation
	 *            Initial population array.
	 */
	public NSGA2Population(NSGA2Solution[] initPopulation) {
		this.domainDim = initPopulation[0].getDecisionDimension();
		for (int i = 0; i < initPopulation.length; i++)
			this.population.add(initPopulation[i].duplicate());
	}

	/**
	 * Generate a random population with values in given boundaries.
	 * 
	 * @param populationSize
	 *            Number of population subjects.
	 * @param minComponentVals
	 *            Minimal domain value per subject.
	 * @param maxComponentVals
	 *            Maximal domain value per subject.
	 */
	private void createRandomPop(int populationSize, double[] minComponentVals, double[] maxComponentVals) {
		for (int i = 0; i < populationSize; i++) {
			NSGA2Solution s = new NSGA2Solution(domainDim, objDim);
			s.randomize(rand, minComponentVals, maxComponentVals);
			population.add(s);
		}
	}

	/**
	 * Get subject at position index from population. Changes to the subject are
	 * seen in this population.
	 * 
	 * @param index
	 *            Index of subject needed.
	 * @return Subject from this population.
	 */
	public NSGA2Solution getSubjectAtIndex(int index) {
		return population.get(index);
	}

	/**
	 * Get one random subject from population. Changes to the subject are seen
	 * in this population.
	 * 
	 * @return Random population element.
	 */
	public NSGA2Solution getRandomSubject() {
		return population.get(rand.nextInt(population.size()));
	}

	/**
	 * Get population size.
	 * 
	 * @return Population size.
	 */
	public int getPopulationSize() {
		return population.size();
	}

	/**
	 * Sort this population by fitness from best to worst.
	 */
	public void sortThisPopulation() {
		Collections.sort(population);
		Collections.reverse(population);
	}

	/**
	 * Get this population in list format for evaluation and sorting.
	 * 
	 * @return This population.
	 */
	public LinkedList<NSGA2Solution> getPopulation() {
		return population;
	}

	@Override
	public Iterator<NSGA2Solution> iterator() {
		return population.iterator();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (NSGA2Solution s : population) {
			sb.append(s + "\n");
		}
		return sb.toString();
	}
}
