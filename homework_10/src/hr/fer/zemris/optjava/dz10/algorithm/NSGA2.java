package hr.fer.zemris.optjava.dz10.algorithm;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz10.algorithm.crossover.BLXAlphaOperator;
import hr.fer.zemris.optjava.dz10.algorithm.mutation.GaussDistMutation;
import hr.fer.zemris.optjava.dz10.interfaces.IMOOPOptFunction;
import hr.fer.zemris.optjava.dz10.interfaces.IOptAlgorithm;
import hr.fer.zemris.optjava.dz10.interfaces.ISelection;
import hr.fer.zemris.optjava.dz10.util.Constants;

/**
 * Non-dominated Sorting Genetic Algorithm.
 * 
 * @author Viran
 *
 */
public class NSGA2 implements IOptAlgorithm {

	private int populationSize;
	private int maxIterations;
	private double sigma;
	private IMOOPOptFunction function;

	// Population attributes
	private NSGA2Population population;

	// Genetic operators
	private ISelection selectionOp;
	private BLXAlphaOperator crossoverOp;
	private GaussDistMutation mutationOp;

	/**
	 * GenerationElitisticGA constructor.
	 * 
	 * @param populationSize
	 *            Size of population.
	 * @param minError
	 *            Minimal error at which the algorithm can stop.
	 * @param maxIterations
	 *            Maximal number of iterations.
	 * @param selection
	 *            Selection operator.
	 * @param sigma
	 *            Mutation parameter.
	 * @param function
	 *            Function we optimise.
	 */
	public NSGA2(int populationSize, int maxIterations, ISelection selection, double sigma, IMOOPOptFunction function) {
		this.populationSize = populationSize;
		this.maxIterations = maxIterations;
		this.selectionOp = selection;
		this.sigma = sigma;
		this.function = function;

		// specific classes:
		this.crossoverOp = new BLXAlphaOperator(function.getMinDomainVals(), function.getMaxDomainVals());
		this.mutationOp = new GaussDistMutation(function.getMinDomainVals(), function.getMaxDomainVals());
	}

	@Override
	public void run() {

		population = new NSGA2Population(populationSize, function.getDecisionSpaceDim(), function.getObjectiveSpaceDim(),
				function.getMinDomainVals(), function.getMaxDomainVals());
		function.evaluatePopulation(population.getPopulation(), Constants.ALPHA_SHARING_FUNCTION, sigma);
		int iteration = maxIterations;
		LinkedList<NSGA2Solution> nextPopulation=new LinkedList<>();
		int nextPopulationSubject; // index of next population element

		NSGA2Solution parentA;
		NSGA2Solution parentB;
		NSGA2Solution child;

		while (iteration-- > 0) {
			if (Constants.PRINT_TO_SCREEN)
				System.out.println("Iteration " + iteration);
			nextPopulation=new LinkedList<>();
			nextPopulationSubject = 0;
			selectionOp.setPopulation(population);

			while (nextPopulationSubject < populationSize) {
				parentA = selectionOp.selectRandomSubject();
				parentB = selectionOp.selectRandomSubject();
				child = crossoverOp.crossover(parentA, parentB, Constants.ALPHA_BLX);
				child = mutationOp.mutation(child, Constants.GAUSS_DIST_MUTAT_SIGMA);
				double[] objectives = new double[function.getMOOPProblem().getObjectiveSpaceDim()];
				function.getMOOPProblem().evaluateSolution(child.getValuesVector(), objectives);
				child.setObjSolValues(objectives);
				nextPopulation.add(child);
				nextPopulationSubject++;
			}
			LinkedList<NSGA2Solution> combinedPopulations=population.getPopulation();
			combinedPopulations.addAll(nextPopulation);
			
			population = function.generateNextPopulation(combinedPopulations, Constants.ALPHA_SHARING_FUNCTION, sigma);
			//function.evaluatePopulation(population.getPopulation(), Constants.ALPHA_SHARING_FUNCTION, sigma);

		}
		function.evaluatePopulation(population.getPopulation(), Constants.ALPHA_SHARING_FUNCTION, sigma);
	}

	public LinkedList<LinkedList<NSGA2Solution>> getParetoFronts() {
		return this.function.getParetoFronts(population.getPopulation());
	}
}
