package hr.fer.zemris.optjava.dz7.algorithms.cloneAlg;

import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.function.FitnessFunction;
import hr.fer.zemris.optjava.dz7.solution.DoubleArraySolution;
import hr.fer.zemris.optjava.dz7.util.Constants;

/**
 * Clone selection algorithm.
 * @author Viran
 *
 */
public class CloneAlgorithm {

	private int maxIter;
	private ClonePopulation population;
	private double mErr;
	private FitnessFunction fitFunction;
	private int numSelected;
	private int numBorn;
	private int beta;
	private Random rand = new Random(System.currentTimeMillis());
	private double[] minInitVals;
	private double[] maxInitVals;
	private DoubleArraySolution globalBest;

	/**
	 * CloneAlgorithm algorithm.
	 * @param fitFunction Fitness function we are minimising.
	 * @param popSize population size.
	 * @param mErr Minimal error.
	 * @param maxIter Maximal number of iterations.
	 * @param numSelected Number of antibodies selected for cloning.
	 * @param numBorn Number of newly created antibodies.
	 * @param beta Algorithm coefficient.
	 */
	public CloneAlgorithm(FitnessFunction fitFunction, int popSize, double mErr, int maxIter, int numSelected,
			int numBorn, int beta) {
		this.fitFunction = fitFunction;
		this.numSelected = numSelected;
		this.beta = beta;
		this.numBorn = numBorn;
		this.maxIter = maxIter;
		this.mErr = mErr;

		minInitVals = new double[fitFunction.getWeightsCount()];
		maxInitVals = new double[fitFunction.getWeightsCount()];
		for (int i = 0; i < minInitVals.length; i++) {
			minInitVals[i] = Constants.MIN_INIT_VAL;
			maxInitVals[i] = Constants.MAX_INIT_VAL;
		}
		this.population = new ClonePopulation(popSize, fitFunction.getWeightsCount()); // init
		this.population.initPopulation(minInitVals, maxInitVals);
		evaluate(population, fitFunction, true);
	}

	/**
	 * Run the algorithm.
	 * @return Best found solution.
	 */
	public DoubleArraySolution run() {
		int iter = 0;
		while (iter < maxIter) {
			evaluate(population, fitFunction, true);
			population = select(population, numSelected);
			ClonePopulation clonePopulation = clone(population, beta);
			ClonePopulation hypPopulation = hypermutate(clonePopulation);
			evaluate(hypPopulation, fitFunction, false);
			ClonePopulation hypSelectPopulation = select(hypPopulation, numSelected);
			ClonePopulation bornPopulation = createNew(numBorn);
			population = change(hypSelectPopulation, bornPopulation);
			if (globalBest.getFitness() <= mErr)
				break;
			System.out.println("Best solution found for iteration " + iter + " :");
			System.out.println(globalBest + "\n\n");
			iter++;
		}
		evaluate(population, fitFunction, true);
		System.out.println("Best solution found:");
		System.out.println(globalBest);
		return globalBest;
	}

	/**
	 * Perform the exchange in antigen solutions.
	 * @param hypSelectPopulation Hypermutated population.
	 * @param bornPopulation Newly created population.
	 * @return Resulting population. 
	 */
	private ClonePopulation change(ClonePopulation hypSelectPopulation, ClonePopulation bornPopulation) {
		LinkedList<DoubleArraySolution> nextPopulation = new LinkedList<>();

		for (DoubleArraySolution randantigen : bornPopulation)
			nextPopulation.add(randantigen);

		hypSelectPopulation.sortFitAsc();
		int pos = 0;
		while (nextPopulation.size() < hypSelectPopulation.getPopulationSize() - 1) {
			nextPopulation.addFirst(hypSelectPopulation.getAntibody(pos++));
		}
		nextPopulation.addFirst(globalBest);
		return new ClonePopulation(nextPopulation);
	}

	/**
	 * Create a new population.
	 * @param numBorn Number of newly created antigen.
	 * @return Resulting population.
	 */
	private ClonePopulation createNew(int numBorn) {
		LinkedList<DoubleArraySolution> bornPopulation = new LinkedList<>();

		for (int i = 0; i < numBorn; i++) {
			DoubleArraySolution sol = new DoubleArraySolution(fitFunction.getWeightsCount());
			sol.randomize(rand, minInitVals, maxInitVals);
			sol.setFitness(fitFunction.valueAt(sol.getValues()));
			bornPopulation.add(sol);
		}
		return new ClonePopulation(bornPopulation);
	}

	/**
	 * Hypermutate the given population.
	 * @param clonePopulation Population in need of hypermutation.
	 * @return Resulting population.
	 */
	private ClonePopulation hypermutate(ClonePopulation clonePopulation) {
		double randomNum;
		double n = clonePopulation.getPopulationSize();
		double r = 0;
		for (DoubleArraySolution antigen : clonePopulation) {
			double f = 1. / antigen.getFitness();
			double p;
			if (Constants.EXPRESSION_1) {
				p = Math.exp(-Constants.RO * f);
			} else {
				p = 1 / Constants.RO * Math.exp(-f);
			}
			randomNum = rand.nextDouble();
			if (randomNum < p) {
				mutateantigen(antigen, n, r, Constants.RO);
			}
			r++;
		}
		evaluate(clonePopulation, fitFunction, false);
		return clonePopulation;
	}

	/**
	 * Perform the mutation on the given antigen.
	 * @param antigen Antigen solution.
	 * @param n Number of population subjects.
	 * @param r Rank of the antigen.
	 * @param ro Algorithm coefficient.
	 */
	private void mutateantigen(DoubleArraySolution antigen, double n, double r, double ro) {
		double l = antigen.getDimension();
		double thau = -(n - 1) / Math.log(1 - ro);
		double k = 1 + l * (1 - Math.exp(-r / thau));
		int unusedIndex = 0;

		int[] indexes = new int[antigen.getDimension()];
		for (int i = 0; i < indexes.length; i++)
			indexes[i] = i;

		for (int i = 0; i < k; i++) {
			unusedIndex = rand.nextInt(indexes.length - i - 1);
			int workingIndex = indexes[unusedIndex];
			indexes[unusedIndex] = indexes[indexes.length - i - 1];
			indexes[indexes.length - i - 1] = workingIndex;

			mutate(antigen, workingIndex);
		}

	}

	/**
	 * Mutate one antigen at he position specified by the working index.
	 * @param antigen Antigen in need of mutation.
	 * @param workingIndex Index at which we mutate the gene.
	 */
	private void mutate(DoubleArraySolution antigen, int workingIndex) {
		double[] geneVals = antigen.getValues();
		geneVals[workingIndex] +=  rand.nextDouble() * Constants.MAX_MUTATION_OFFSET * 2
				- Constants.MAX_MUTATION_OFFSET;
	}

	/**
	 * Clone the given population.
	 * @param population Population in need of cloning.
	 * @param beta Algorithm coefficient.
	 * @return Resulting population.
	 */
	private ClonePopulation clone(ClonePopulation population, int beta) {
		LinkedList<DoubleArraySolution> clonedPopulation = new LinkedList<>();
		int n = population.getPopulationSize();
		int i = 1;
		int clones;
		for (DoubleArraySolution antibody : population) {
			clones = (int) Math.floor(beta * n / i++);
			for (int j = 0; j < clones; j++)
				clonedPopulation.add(antibody.duplicate());
		}
		
		return new ClonePopulation(clonedPopulation);
	}

	/**
	 * Select N best antibodies from population.
	 * @param population Population we 
	 * @param numSelected N best antibodies.
	 * @return Resulting population.
	 */
	private ClonePopulation select(ClonePopulation population, int numSelected) {
		LinkedList<DoubleArraySolution> selectedPop = new LinkedList<>();
		population.sortFitAsc();
		int counter = 0;
		for (DoubleArraySolution sol : population) {
			selectedPop.add(sol);
			counter++;
			if (counter >= numSelected)
				break;
		}
	
		return new ClonePopulation(selectedPop);
	}

	/**
	 * Evaluate the given population.
	 * @param population Population in need of evaluation.
	 * @param fitFunction Function which evaluates the population solutions.
	 * @param updateGlobalBest Flag for updating the global best solution found.
	 */
	private void evaluate(ClonePopulation population, FitnessFunction fitFunction, boolean updateGlobalBest) {
		for (DoubleArraySolution solution : population) {
			solution.setFitness(fitFunction.valueAt(solution.getValues()));
			if (updateGlobalBest) {
				if (globalBest == null) {
					globalBest = solution.duplicate();
				} else if (globalBest.getFitness() > solution.getFitness()) {
					globalBest = solution.duplicate();
				}
			}
		}
	}

}
