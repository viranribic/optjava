package hr.fer.zemris.optjava.dz9.algorithm;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz9.algorithm.crossover.BLXAlphaOperator;
import hr.fer.zemris.optjava.dz9.algorithm.mutation.GaussDistMutation;
import hr.fer.zemris.optjava.dz9.interfaces.IMOOPOptFunction;
import hr.fer.zemris.optjava.dz9.interfaces.IOptAlgorithm;
import hr.fer.zemris.optjava.dz9.interfaces.ISelection;
import hr.fer.zemris.optjava.dz9.util.Constants;

/**
 * Non-dominated Sorting Genetic Algorithm.
 * 
 * @author Viran
 *
 */
public class NSGA implements IOptAlgorithm {

	private int populationSize;
	private int maxIterations;
	private double sigma;
	private IMOOPOptFunction function;

	// Population attributes
	private NSGAPopulation population;

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
	public NSGA(int populationSize, int maxIterations, ISelection selection, double sigma, IMOOPOptFunction function) {
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

		population = new NSGAPopulation(populationSize, function.getDecisionSpaceDim(), function.getObjectiveSpaceDim(),
				function.getMinDomainVals(), function.getMaxDomainVals());
		function.evaluatePopulation(population.getPopulation(), Constants.ALPHA_SHARING_FUNCTION, sigma);
		population.sortThisPopulation();
		int iteration = maxIterations;
		NSGASolution[] nextPopulation;
		int nextPopulationSubject; // index of next population element

		NSGASolution parentA;
		NSGASolution parentB;
		NSGASolution child;

		while (iteration-- > 0) {
			if (Constants.PRINT_TO_SCREEN)
				System.out.println("Iteration " + iteration);
			nextPopulation = new NSGASolution[populationSize];
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
				// TODO choose which of the parents will be replaced
				if (child.dominates(parentA) || child.dominates(parentB)) {
					nextPopulation[nextPopulationSubject++] = child.duplicate();
				} else if (parentA.dominates(parentB)) {
					nextPopulation[nextPopulationSubject++] = parentA.duplicate();
				} else if (parentB.dominates(parentA)) {// if(parentB.dominates(parentA,
					// function.getMOOPProblem())){
					nextPopulation[nextPopulationSubject++] = parentB.duplicate();
				} else {
					nextPopulation[nextPopulationSubject++] = child.duplicate();
				}
			}
			population = new NSGAPopulation(nextPopulation);
			function.evaluatePopulation(population.getPopulation(), Constants.ALPHA_SHARING_FUNCTION, sigma);
			population.sortThisPopulation();

		}
	}

	public LinkedList<LinkedList<NSGASolution>> getParetoFronts() {
		return this.function.getParetoFronts(population.getPopulation());
	}
}
