package hr.fer.zemris.optjava.dz4.part2.geneticAlgorithms;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.part2.constants.ConstantsTask2;
import hr.fer.zemris.optjava.dz4.part2.geneticAlgorithms.population.BoxFillPopulation;
import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution;
import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution.BoxFillBucket;
import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution.BoxFillBucket.BoxFillStick;

/**
 * Class for initialising BoxFill problem population.
 * 
 * @author Viran
 *
 */
public class InitialisePopulation {

	/**
	 * Generates the starting population.
	 * 
	 * @param population
	 *            Population we construct.
	 * @param stickVlaues
	 *            Stick values in our problem.
	 * @return Initialised population.
	 */
	public static BoxFillPopulation generateStartingPopulation(BoxFillPopulation population,
			LinkedList<Double> stickVlaues) {
		Random rand = new Random();
		int populationSize = population.getPopulationSize();
		LinkedList<BoxFillStick> bFSticks = new LinkedList<>();

		for (int i = 0; i < stickVlaues.size(); i++) {
			bFSticks.add(i, new BoxFillStick(String.valueOf(i), stickVlaues.get(i)));
		}

		for (int pop = 0; pop < populationSize; pop++) {
			BoxFillSolution popSolution = new BoxFillSolution();

			LinkedList<BoxFillStick> solutionListCopy = new LinkedList<>(bFSticks);
			while (solutionListCopy.size() != 0) {
				int nextPos = rand.nextInt(solutionListCopy.size());
				BoxFillStick nextStick = solutionListCopy.remove(nextPos);
				addToSolutionContainer(nextStick, popSolution.container);
			}

			population.addSolution(popSolution);
			;
		}

		sortPopulation(population);

		return population;
	}

	/**
	 * Sort all sticks in every bucket in every container in every solutoin in
	 * this population.
	 * 
	 * @param population
	 *            Population we are sorting.
	 */
	private static void sortPopulation(BoxFillPopulation population) {
		LinkedList<BoxFillSolution> pop = population.population;
		for (BoxFillSolution sol : pop) {
			LinkedList<BoxFillBucket> con = sol.container;
			for (BoxFillBucket buc : con) {
				Collections.sort(buc.sticks);
				Collections.reverse(buc.sticks);
				// sort descending
			}
		}
	}

	/**
	 * Adds one stick to solution.
	 * 
	 * @param nextStick
	 *            Next stick we need to add to a bucket.
	 * @param container
	 *            Container to which we add sticks.
	 */
	private static void addToSolutionContainer(BoxFillStick nextStick, LinkedList<BoxFillBucket> container) {
		boolean addResult = false;
		for (int i = 0; i < container.size(); i++) {
			BoxFillBucket bucket = container.get(i);
			addResult = bucket.addStick(nextStick);
			if (addResult)
				return;
		}
		BoxFillBucket bucket = new BoxFillBucket(ConstantsTask2.MAX_CAPACITY);
		bucket.addStick(nextStick);
		container.add(bucket);
	}

}
