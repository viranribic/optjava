package hr.fer.zemris.optjava.dz10.function.fitnessFunction;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Solution;
import hr.fer.zemris.optjava.dz10.interfaces.MOOPProblem;

/**
 * DecSpaceFitFunc implements the solution distance in the solution (decision)
 * space domain.
 * 
 * @author Viran
 *
 */
public class DecSpaceFitFunc extends FitnessFunction {

	/**
	 * FitnessFunction constructor.
	 * 
	 * @param moopProblem
	 *            Problem we are trying to solve.
	 */
	public DecSpaceFitFunc(MOOPProblem moopProblem) {
		super(moopProblem);
	}

	@Override
	protected void calculateDistance(LinkedList<NSGA2Solution> population) {
		subjectRelations = new double[population.size()][population.size()];

		double[] minValVect = new double[moopProblem.getDecisionSpaceDim()];
		double[] maxValVect = new double[moopProblem.getDecisionSpaceDim()];

		for (int popSubjIndex = 0; popSubjIndex < population.size(); popSubjIndex++) {
			NSGA2Solution solution = population.get(popSubjIndex);
			double[] vector = solution.getValuesVector();

			if (popSubjIndex == 0) {
				for (int vecIndex = 0; vecIndex < vector.length; vecIndex++) {
					minValVect[vecIndex] = vector[vecIndex];
					maxValVect[vecIndex] = vector[vecIndex];
				}
			} else {
				for (int vecIndex = 0; vecIndex < vector.length; vecIndex++) {
					if (minValVect[vecIndex] > vector[vecIndex]) {
						minValVect[vecIndex] = vector[vecIndex];
					}
					if (maxValVect[vecIndex] < vector[vecIndex]) {
						maxValVect[vecIndex] = vector[vecIndex];
					}
				}
			}
		}

		for (int firstSubjectIndex = 0; firstSubjectIndex < population.size(); firstSubjectIndex++) {
			for (int secondSubjectIndex = firstSubjectIndex + 1; secondSubjectIndex < population
					.size(); secondSubjectIndex++) {

				NSGA2Solution firstSolution = population.get(firstSubjectIndex);
				NSGA2Solution secondSolution = population.get(secondSubjectIndex);

				double[] firstValues = firstSolution.getValuesVector();
				double[] secondValues = secondSolution.getValuesVector();

				double d = 0;

				for (int dim = 0; dim < firstValues.length; dim++) {
					double tmp = (firstValues[dim] - secondValues[dim]) / (maxValVect[dim] - minValVect[dim]);
					tmp = Math.pow(tmp, 2);
					d += tmp;
				}
				subjectRelations[firstSubjectIndex][secondSubjectIndex] = subjectRelations[secondSubjectIndex][firstSubjectIndex] = Math
						.sqrt(d);

			}
		}

	}

}
