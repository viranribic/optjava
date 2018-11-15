package hr.fer.zemris.optjava.dz4.part2.geneticOperatos.crossoverOperators;

import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution;
import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution.BoxFillBucket;

/**
 * Genetic algorithm crossover operation.
 * 
 * @author Viran
 *
 */
public class BoxFillCrossover {

	private static Random rand = new Random();

	/**
	 * Perform a crossover genetic operation on these subjects.
	 * 
	 * @param parentA
	 *            First subject.
	 * @param parentB
	 *            Second subject.
	 * @return List of created children.
	 */
	public LinkedList<BoxFillSolution> crossover(BoxFillSolution parentA, BoxFillSolution parentB) {
		BoxFillSolution parentAClone = parentA.duplicate();
		BoxFillSolution parentBClone = parentB.duplicate();

		int crossoverPointA1 = rand.nextInt(parentAClone.getNumOfBins());
		int offsetA2 = rand.nextInt(parentAClone.getNumOfBins()-crossoverPointA1);
		int crossoverPointB1 = rand.nextInt(parentBClone.getNumOfBins());
		int offsetB2 = rand.nextInt(parentBClone.getNumOfBins()-crossoverPointB1);

		BoxFillBucket[] parentAGenes = parentAClone.getBuckets(crossoverPointA1, crossoverPointA1+offsetA2);
		BoxFillBucket[] parentBGenes = parentBClone.getBuckets(crossoverPointB1, crossoverPointB1+offsetB2);

		parentAClone.insertAtCrossoverPoint(parentBGenes, crossoverPointA1+offsetA2);
		parentBClone.insertAtCrossoverPoint(parentAGenes, crossoverPointB1);
		LinkedList<BoxFillSolution> result = new LinkedList<BoxFillSolution>();
		result.add(parentAClone);
		result.add(parentBClone);
		return result;
	}

}
