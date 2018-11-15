package hr.fer.zemris.optjava.dz4.part2.geneticAlgorithms;

import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.part2.function.BoxFillFitness2;
import hr.fer.zemris.optjava.dz4.part2.geneticAlgorithms.population.BoxFillPopulation;
import hr.fer.zemris.optjava.dz4.part2.geneticOperators.selectionOperators.BoxFillTournamentSelection;
import hr.fer.zemris.optjava.dz4.part2.geneticOperatos.crossoverOperators.BoxFillCrossover;
import hr.fer.zemris.optjava.dz4.part2.geneticOperatos.mutationOperators.BoxFillMutation;
import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution;
import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution.BoxFillBucket;

/**
 * Genetic algorithm adjusted for box fill problem needs.
 * 
 * @author Viran
 *
 */
public class BoxFillGeneticAlgorithm {

	// Algorithm general attributes
	Random rand = new Random();
	BoxFillFitness2 function;
	private int populationSize;
	@SuppressWarnings("unused")
	private int n;
	private boolean p;
	@SuppressWarnings("unused")
	private int m;
	private int maxIterations;
	@SuppressWarnings("unused")
	private int acceptableContainerSize;

	// genetic operators
	private BoxFillTournamentSelection parentTournamentSelection;
	private BoxFillTournamentSelection childTournamentSelection;
	private BoxFillMutation mutationOp = new BoxFillMutation();
	private BoxFillCrossover crossoverOp;

	// Best solution
	private BoxFillSolution best;

	/**
	 * BoxFillGeneticAlgorithm constructor.
	 * 
	 * @param function
	 *            Optimisation function.
	 * @param populationSize
	 *            Population size.
	 * @param n
	 *            Tournament size for selecting one parent.
	 * @param m
	 *            Tournament size for selecting the subject destined to be
	 *            banished from the population
	 * @param p
	 *            Flag which indicates if the child gets to the next population
	 *            or his death is imminent.
	 * @param maxIterations
	 *            Number of iterations algorithm has to make before ending his
	 *            run.
	 * @param acceptableContainerSize
	 *            Size of container at which we can stop the search.
	 */
	public BoxFillGeneticAlgorithm(BoxFillFitness2 function, int populationSize, int n, int m, boolean p,
			int maxIterations, int acceptableContainerSize) {
		this.function = function;
		this.populationSize = populationSize;
		this.n = n;
		this.parentTournamentSelection = new BoxFillTournamentSelection(n, true);
		this.m = m;
		this.childTournamentSelection = new BoxFillTournamentSelection(m, false);
		this.p = p;
		this.maxIterations = maxIterations;
		this.acceptableContainerSize = acceptableContainerSize;
		crossoverOp = new BoxFillCrossover();
	}

	/**
	 * Run the algorithm.
	 */
	public void run() {
		BoxFillPopulation population = new BoxFillPopulation(populationSize);
		population = InitialisePopulation.generateStartingPopulation(population, function.stickVlaues);
		evaluatePopulation(population);
		int iteration = maxIterations;

		BoxFillSolution parentA;
		BoxFillSolution parentB;
		BoxFillSolution child;
		BoxFillSolution worstSubjByTournamet;

		while (iteration-- > 0) {
			parentTournamentSelection.setPopulation(population);
			childTournamentSelection.setPopulation(population);
			int populationExchanges = populationSize;
			while (populationExchanges-- > 0) {

				parentA = parentTournamentSelection.selectRandomSubject();
				parentB = parentTournamentSelection.selectRandomSubject();
				LinkedList<BoxFillSolution> twoChilds = crossoverOp.crossover(parentA, parentB);
				child = twoChilds.get(rand.nextInt(2));
				child = mutationOp.mutation(child);
				child.fitness = function.valueAt(child);

				worstSubjByTournamet = childTournamentSelection.selectRandomSubject();
				// p==true:child substitutes the worst found only if it has
				// better fitness
				if (p) {
					// maximisation problem: if child is better add him,
					// otherwise add the other element
					if (child.fitness > worstSubjByTournamet.fitness)
						worstSubjByTournamet = child;
				} else {
					worstSubjByTournamet = child;
				}
			}
			// nadji globalno najbolji fitness
			best = findBestInPopulation(population);
			printSubjectFitness(best, iteration);
			if (best.fitness >= 1) { // hard coded
				break;
			}
		}
		// end of run();
		printSolution(best);
	}

	/**
	 * Find the best solution in population.
	 * 
	 * @param population
	 *            Population in which we search.
	 * @return Best in population.
	 */
	private BoxFillSolution findBestInPopulation(BoxFillPopulation population) {
		int bestIndex = 0;
		double bestFitness = 0;
		for (int i = 0; i < populationSize; i++) {
			if (i == 0) {
				bestIndex = i;
				bestFitness = population.population.get(i).fitness;
			} else {
				if (bestFitness < population.population.get(i).fitness) {
					bestIndex = i;
					bestFitness = population.population.get(i).fitness;
				}
			}
		}

		return population.population.get(bestIndex);
	}

	/**
	 * Joint the corresponding fitness values to every solution in population.
	 * 
	 * @param population
	 *            Population in need of evaluation.
	 */
	private void evaluatePopulation(BoxFillPopulation population) {
		for (BoxFillSolution sol : population.population)
			sol.fitness = function.valueAt(sol);
	}

	/**
	 * Print the solution and its fitness.
	 * 
	 * @param best
	 *            Solution we are sending to standard output.
	 */
	private void printSolution(BoxFillSolution best) {
		System.out.println("End of run:");
		for (int i = 0; i < best.container.size(); i++) {
			StringBuilder sb=new StringBuilder();
			System.out.print("At position " + (i + 1) + " bucked is filled to :");
			System.out.printf(" %5.5f ", best.container.get(i).filledPercentage());
			System.out.println(" percent of maximum.");
			BoxFillBucket b=best.container.get(i);
			for(int j=0;j<b.sticks.size();j++){
				sb.append(" Label: ("+b.sticks.get(j).getLabel()+") Length:("+b.sticks.get(j).getLength()+") |");
			}
			System.out.println(sb.toString());
		}
		System.out.printf("Best fitness: %5.5f\n", best.fitness);
	
	}

	/**
	 * Print subject fitness to standard output.
	 * 
	 * @param subject
	 *            Subject.
	 * @param iteration
	 *            Iteration.
	 */
	private void printSubjectFitness(BoxFillSolution subject, int iteration) {
		System.out.printf("Iteration: %10d | Best fitness: %5.5f\n", iteration, subject.fitness);

	}

}
