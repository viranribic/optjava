package hr.fer.zemris.optjava.dz9.function.fitnessFunction;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz9.algorithm.NSGASolution;
import hr.fer.zemris.optjava.dz9.interfaces.IMOOPOptFunction;
import hr.fer.zemris.optjava.dz9.interfaces.MOOPProblem;
import hr.fer.zemris.optjava.dz9.util.Constants;

/**
 * FitnessFunction for MOOP task.
 * 
 * @author Viran
 *
 */
public abstract class FitnessFunction implements IMOOPOptFunction {

	protected MOOPProblem moopProblem;
	protected LinkedList<LinkedList<Integer>> dominatingOnThis;
	protected int[] dominatedByThis;
	protected LinkedList<LinkedList<Integer>> paretoFronts;

	/**
	 * One matrix representing distances, sharing function values and density
	 * values.
	 */
	protected double[][] subjectRelations;

	/**
	 * FitnessFunction constructor.
	 * 
	 * @param moopProblem
	 *            Problem we are trying to solve.
	 */
	public FitnessFunction(MOOPProblem moopProblem) {
		this.moopProblem = moopProblem;
	}

	@Override
	public int getDecisionSpaceDim() {
		return moopProblem.getDecisionSpaceDim();
	}

	@Override
	public int getObjectiveSpaceDim() {
		return moopProblem.getObjectiveSpaceDim();
	}

	@Override
	public double[] getMinDomainVals() {
		return moopProblem.getMinDomainVals();
	}

	@Override
	public double[] getMaxDomainVals() {
		return moopProblem.getMaxDomainVals();
	}

	@Override
	public void evaluatePopulation(LinkedList<NSGASolution> population, double alpha, double sigma) {
		for (NSGASolution solution : population) {
			double[] objectives = new double[moopProblem.getDecisionSpaceDim()];
			moopProblem.evaluateSolution(solution.getValuesVector(), objectives);
			solution.setObjSolValues(objectives);
		}
		nonDominantSorting(population, alpha, sigma);
	}

	/**
	 * Calculate the niche count for every solution.
	 * 
	 * @param subPopulation
	 *            Population of solutions.
	 * @param alpha
	 *            Alpha parameter.
	 * @param sigma
	 *            Sigma parameter.
	 * @return Array of niche count values per solution.
	 */
	private double[] getNcForSubPop(LinkedList<NSGASolution> subPopulation, double alpha, double sigma) {
		calculateDistance(subPopulation);
		calculateSharingValues(subPopulation.size(), alpha, sigma);

		double[] nc = new double[subPopulation.size()];

		for (int i = 0; i < subPopulation.size(); i++)
			for (int j = 0; j < subPopulation.size(); j++)
				nc[i] += subjectRelations[i][j];

		int i = 0;
		for (NSGASolution solution : subPopulation) {
			solution.setFit(solution.getFit() / nc[i++]);
		}
		return nc;
	}

	/**
	 * Calculate shared values for every population subject.
	 * 
	 * @param populationSize
	 *            Number of subjects in population.
	 * @param alpha
	 *            Alpha parameter.
	 * @param sigma
	 *            Sigma parameter.
	 */
	protected void calculateSharingValues(int populationSize, double alpha, double sigma) {
		for (int i = 0; i < populationSize; i++)
			for (int j = 0; j < populationSize; j++) {
				if (subjectRelations[i][j] < sigma) {
					double tmp = subjectRelations[i][j] / sigma;
					tmp = Math.pow(tmp, alpha);
					subjectRelations[i][j] = 1 - tmp;
				} else {
					subjectRelations[i][j] = 0;
				}
			}
	}

	/**
	 * Execute non dominant sorting algorithm and assign proper values to
	 * population subjects.
	 * 
	 * @param population
	 *            Population subjects.
	 * @param alpha
	 *            Alpha parameter.
	 * @param sigma
	 *            Sigma parameter.
	 */
	public void nonDominantSorting(LinkedList<NSGASolution> population, double alpha, double sigma) {
		paretoFronts = new LinkedList<>();

		dominatedByThis = new int[population.size()];
		dominatingOnThis = new LinkedList<>();
		LinkedList<Integer> currentParetoFront = new LinkedList<>();

		for (int firstSubjectIndex = 0; firstSubjectIndex < population.size(); firstSubjectIndex++) {
			NSGASolution sol1 = population.get(firstSubjectIndex);
			dominatedByThis[firstSubjectIndex] = 0;
			LinkedList<Integer> sol1Dominated = new LinkedList<>();
			dominatingOnThis.add(sol1Dominated);

			for (int secondSubjectIndex = 0; secondSubjectIndex < population.size(); secondSubjectIndex++) {

				if (secondSubjectIndex == firstSubjectIndex) {
					continue;
				}

				NSGASolution sol2 = population.get(secondSubjectIndex);

				if (sol1.dominates(sol2)) {
					sol1Dominated.add(secondSubjectIndex);
				} else if (sol2.dominates(sol1)) {
					dominatedByThis[firstSubjectIndex]++;
				}
			}
			if (dominatedByThis[firstSubjectIndex] == 0) {
				currentParetoFront.add(firstSubjectIndex);
			}
		}
		int frontCounter = 0;
		paretoFronts.add(frontCounter, currentParetoFront);

		while (true) {
			if (currentParetoFront.size() == 0) {
				break;
			}
			LinkedList<Integer> qList = new LinkedList<>();
			for (int i = 0; i < currentParetoFront.size(); i++) {
				LinkedList<Integer> currentsSolutionSet = dominatingOnThis.get(currentParetoFront.get(i));
				for (int j = 0; j < currentsSolutionSet.size(); j++) {
					int workingIndex = currentsSolutionSet.get(j);
					dominatedByThis[workingIndex]--;
					if (dominatedByThis[workingIndex] == 0) {
						qList.add(workingIndex);
					}
				}
			}

			currentParetoFront = qList;
			frontCounter++;
			paretoFronts.add(frontCounter, currentParetoFront);
		}
		paretoFronts.removeLast();

		int frontC = 1;
		if (Constants.PRINT_TO_SCREEN)
			for (LinkedList<Integer> front : paretoFronts) {
				System.out.print("Front " + (frontC++) + " : ");
				for (int i = 0; i < front.size(); i++) {
					System.out.print("#");
				}
				System.out.println();
			}

		double[] niceCount = getNcForSubPop(population, alpha, sigma);

		if (Constants.PRINT_TO_SCREEN) {
			System.out.println("NiceCount values:");
			for (int i = 0; i < niceCount.length; i++) {
				System.out.print(niceCount[i] + " ");
			}
			System.out.println();
		}

		double currN = population.size();

		for (LinkedList<Integer> frontElements : paretoFronts) {
			double minCurrN = 0;
			for (int i = 0; i < frontElements.size(); i++) {
				int pos = frontElements.get(i);
				NSGASolution solution = population.get(pos);
				double fScaled = currN / niceCount[pos];
				if (i == 0)
					minCurrN = fScaled;
				else if (minCurrN > fScaled)
					minCurrN = fScaled;
				solution.setFit(fScaled);
			}
			currN = minCurrN - Constants.SOMETHING_SMALL;
		}

		System.out.println();
	}

	/**
	 * Determinate non dominant set of population subjects
	 * 
	 * @param population
	 *            List od subjects.
	 * @return Non dominated subjects list.
	 */
	public LinkedList<NSGASolution> determinateNonDominantSet(LinkedList<NSGASolution> population) {
		LinkedList<NSGASolution> nonDominantSet = new LinkedList<>();
		for (int i = 0; i < population.size(); i++) {
			NSGASolution solutionOne = population.get(i);
			boolean dominated = false;
			for (int j = 0; j < population.size(); j++) {
				NSGASolution solutionTwo = population.get(j);
				if (solutionTwo.dominates(solutionOne)) {
					dominated = true;
					break;
				}
			}
			if (!dominated)
				nonDominantSet.add(solutionOne);
		}
		return nonDominantSet;
	}

	@Override
	public MOOPProblem getMOOPProblem() {
		return moopProblem;
	}

	@Override
	public LinkedList<LinkedList<NSGASolution>> getParetoFronts(LinkedList<NSGASolution> population) {
		LinkedList<LinkedList<NSGASolution>> paretoFrontsSubjects = new LinkedList<>();

		for (LinkedList<Integer> front : paretoFronts) {
			LinkedList<NSGASolution> subjects = new LinkedList<>();
			for (Integer pos : front) {
				subjects.add(population.get(pos));
			}
			paretoFrontsSubjects.add(subjects);
		}

		return paretoFrontsSubjects;
	}

	/**
	 * Calculate distance between population elements.
	 * 
	 * @param population
	 *            List of elements whose distances we need to find.
	 */
	abstract protected void calculateDistance(LinkedList<NSGASolution> population);
}
