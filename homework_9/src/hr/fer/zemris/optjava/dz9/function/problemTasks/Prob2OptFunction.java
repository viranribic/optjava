package hr.fer.zemris.optjava.dz9.function.problemTasks;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz9.function.SimpleBoundary;
import hr.fer.zemris.optjava.dz9.function.problem2Functions.ComponentRatioFunction;
import hr.fer.zemris.optjava.dz9.function.problem2Functions.FirstVectorComponentFunction;
import hr.fer.zemris.optjava.dz9.interfaces.IBoundary;
import hr.fer.zemris.optjava.dz9.interfaces.IFunction;
import hr.fer.zemris.optjava.dz9.interfaces.MOOPProblem;

/**
 * Homework MOOP task 2.
 * 
 * @author Viran
 *
 */
public class Prob2OptFunction implements MOOPProblem {

	private static LinkedList<IFunction> functions = new LinkedList<>();
	private static LinkedList<IBoundary> boundaries = new LinkedList<>();
	private int solSpaceDim;
	private int objSpaceDim;

	/**
	 * Prob1OptFunction constructor.
	 * 
	 * @param problem1DomainDim
	 *            Dimension of the evaluated vector.
	 * @param lowBound
	 *            Lower function boundary.
	 * @param upBound
	 *            Upper function boundary.
	 */
	public Prob2OptFunction(int problem1DomainDim, double[] lowBound, double[] upBound) {
		if (lowBound.length != upBound.length)
			throw new IllegalArgumentException("Boundaries restriction vectors do not match by length.");
		functions.add(new FirstVectorComponentFunction());
		functions.add(new ComponentRatioFunction());

		for (int i = 0; i < lowBound.length; i++) {
			boundaries.add(new SimpleBoundary(lowBound[i], upBound[i], true, true));
		}
		this.solSpaceDim = problem1DomainDim;
		this.objSpaceDim = functions.size();
	}

	@Override
	public int getObjectiveSpaceDim() {
		return objSpaceDim;
	}

	@Override
	public void evaluateSolution(double[] solution, double[] objectives) {
		if (solution.length != functions.size())
			throw new RuntimeException("Given solution vector is too small.");

		for (int i = 0; i < solution.length; i++)
			if (!boundaries.get(i).isContained(solution[i]))
				throw new RuntimeException("Value out of bound.");

		for (int i = 0; i < objectives.length; i++)
			objectives[i] = functions.get(i).f(solution)[0]; // expected value
																// is a scalar
		return;

	}

	@Override
	public int getDecisionSpaceDim() {
		return solSpaceDim;
	}

	@Override
	public double[] getMinDomainVals() {
		double[] mins = new double[solSpaceDim];
		int i = 0;
		for (IBoundary b : boundaries) {
			mins[i++] = b.getMin();
		}
		return mins;
	}

	@Override
	public double[] getMaxDomainVals() {
		double[] maxs = new double[solSpaceDim];
		int i = 0;
		for (IBoundary b : boundaries) {
			maxs[i++] = b.getMax();
		}
		return maxs;
	}

}
