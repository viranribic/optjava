package hr.fer.zemris.optjava.dz5.part1.geneticAlgorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part1.constants.Constants;
import hr.fer.zemris.optjava.dz5.part1.function.ModifiedMaxOnes;
import hr.fer.zemris.optjava.dz5.part1.geneticAlgorithms.population.RAPGAPopulation;
import hr.fer.zemris.optjava.dz5.part1.geneticOperators.crossoverOperators.TPointCrossover;
import hr.fer.zemris.optjava.dz5.part1.geneticOperators.mutationOperators.SimpleBitvectorMutation;
import hr.fer.zemris.optjava.dz5.part1.geneticOperators.selectionOperators.RandomSelection;
import hr.fer.zemris.optjava.dz5.part1.geneticOperators.selectionOperators.TournamentSelection;
import hr.fer.zemris.optjava.dz5.part1.interfaces.ICrossover;
import hr.fer.zemris.optjava.dz5.part1.interfaces.IMutation;
import hr.fer.zemris.optjava.dz5.part1.interfaces.IOptAlgorithm;
import hr.fer.zemris.optjava.dz5.part1.interfaces.ISelection;
import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

/**
 * Relevant Alleles Preserving Genetic Algorithm class. (RAPGA)
 * 
 * @author Viran
 *
 */
public class RAPGA implements IOptAlgorithm {

	// Algorithm parameters
	private Random rand = new Random();
	private int vectorDim;
	private int maxPopSize;
	private int minPopSize;
	private double maxSelectionPressure;
	private double comparisonFactor;
	// private boolean maximalSizeReacher=false;
	// Population
	RAPGAPopulation curPopulation;
	RAPGAPopulation nextPopulation;
	private HashSet<BitvectorSolution> pool;
	// Optimisation function
	ModifiedMaxOnes function = new ModifiedMaxOnes();
	ISelection tourSel = new TournamentSelection(Constants.TOURNAMENT_SIZE_K);
	ISelection randSel = new RandomSelection();
	ICrossover crossover = new TPointCrossover(Constants.NUMBER_OF_CROSSOVER_POINTS_T);
	IMutation mutation = new SimpleBitvectorMutation(Constants.MUTATION_PROBABILITY);

	/**
	 * RAPGA constructor.
	 * 
	 * @param n
	 *            Dimension of the vector used.
	 * @param minPopSize
	 *            Minimal population size.
	 * @param maxPopSize
	 *            Maximal population size.
	 * @param maxSelectionPressure
	 *            Maximal selection pressure.
	 * @param comparisonFactor
	 *            Parent comparison factor.
	 */
	public RAPGA(int n, int minPopSize, int maxPopSize, double maxSelectionPressure, double comparisonFactor) {
		this.vectorDim = n;
		this.maxPopSize = maxPopSize;
		this.minPopSize = minPopSize;
		this.maxSelectionPressure = maxSelectionPressure;
		this.comparisonFactor = comparisonFactor;
		this.curPopulation = new RAPGAPopulation(minPopSize, maxPopSize);
		this.pool = new HashSet<>();
		this.nextPopulation = new RAPGAPopulation(minPopSize, maxPopSize);
	}

	@Override
	public void run() {
		int popSize = minPopSize;
		double successRatio = Constants.SUCCESS_RATIO;
		InitPopulationMaxOnes.initPopulation(curPopulation, vectorDim, function, popSize);
		int effort = 1;

		while (effort <= Constants.MAX_EFFORT && !criticalPressureReached(curPopulation, nextPopulation, pool)) {

			tourSel.setPopulation(curPopulation);
			randSel.setPopulation(curPopulation);
			nextPopulation = new RAPGAPopulation(minPopSize, maxPopSize);
			pool = new HashSet<>();

			while (nextPopulation.currentPopulationSize() < curPopulation.currentPopulationSize() * successRatio || (nextPopulation.currentPopulationSize()+pool.size())<minPopSize) {
				
				generateOneMoreChild();
			}

			LinkedList<BitvectorSolution> poolList = new LinkedList<>(pool);
			while (!nextPopulation.maximalSizeReacher()) {
				if (!poolList.isEmpty())
					nextPopulation.addSolution(poolList.remove(rand.nextInt(poolList.size())));
				else
					break;
			}
			
			comparisonFactor+=Constants.COMP_FACTOR_GROWTH;
			if(comparisonFactor>=1)
				comparisonFactor=1;
			if (checkForBest(effort))
				break;
			
			curPopulation=nextPopulation;
			nextPopulation=new RAPGAPopulation(minPopSize, maxPopSize);
			pool=new HashSet<>();
			
			effort++;
		}
	}

	/**
	 * Add one more child to either the next population or the gene pool.
	 */
	private void generateOneMoreChild() {
		BitvectorSolution parentA;
		BitvectorSolution parentB;
		if (Constants.TASK_CASE_1) {
			parentA = tourSel.select(); // tournament
			parentB = tourSel.select(); // tournament
		} else {
			parentA = tourSel.select(); // tournament
			parentB = randSel.select(); // random
		}
		BitvectorSolution child = crossover.crossover(parentA, parentB);
		child = mutation.mutate(child);
		child.setFitness(function.valueAt(child));

		if (childIsBetter(child, parentA, parentB)) {
			nextPopulation.addSolution(child);
		} else {
			pool.add(child);
		}

	}

	/**
	 * Check if the algorithm is done.
	 * 
	 * @param effort
	 *            Current effort.
	 * @return True if best solution has been found, false otherwise.
	 */
	private boolean checkForBest(int effort) {

		// Check for best
		BitvectorSolution best = nextPopulation.getBestSolution();
		if (best.getFitness() == 1) {
			System.out.println("Global best solution found. ");

		}
		System.out.println("Invested effort/Maximal effort=" + effort + " / " + Constants.MAX_EFFORT);
		System.out.printf("Current comparison factor: %2.10f\n",comparisonFactor);
		System.out.println("|Origin population| = " + curPopulation.currentPopulationSize() + "  |Next population|= "
				+ nextPopulation.currentPopulationSize() + " | Gene pool | = " + pool.size());
		System.out.println();
		System.out.printf("Current best fitness: %5.5f\n\n", best.getFitness());
		for (Boolean b : best.getVector()) {
			System.out.print((b) ? "1" : "0");
		}
		System.out.println("\n");
		return (best.getFitness() == 1);
	}

	/**
	 * Compare actual selection pressure with the maximal one.
	 * 
	 * @param curPopulation
	 *            Population in iteration i.
	 * @param nextPopulation
	 *            Population in iteration i+1.
	 * @param pool
	 *            Solutions worse than its parents.
	 * @return True if critical pressure has been reached, false otherwise.
	 */
	private boolean criticalPressureReached(RAPGAPopulation curPopulation, RAPGAPopulation nextPopulation,
			HashSet<BitvectorSolution> pool) {
		return ((nextPopulation.currentPopulationSize() + pool.size())
				/ curPopulation.currentPopulationSize()) >= maxSelectionPressure;
	}

	/**
	 * For the given pair of parents and child, determinate if the child fitness
	 * is better than the one from its parents.
	 * 
	 * @param child
	 *            Child subject.
	 * @param parentA
	 *            First parent subject.
	 * @param parentB
	 *            Second parent subject.
	 * @return True is the child is acceptable for next generation, false
	 *         otherwise.
	 */
	private boolean childIsBetter(BitvectorSolution child, BitvectorSolution parentA, BitvectorSolution parentB) {
		BitvectorSolution better = null;
		BitvectorSolution worse = null;
		if (parentA.getFitness() > parentB.getFitness()) {
			better = parentA;
			worse = parentB;
		} else {
			better = parentB;
			worse = parentA;
		}
		double decider = worse.getFitness() + comparisonFactor * (better.getFitness() - worse.getFitness());
		return child.getFitness() >= decider;
	}

}
