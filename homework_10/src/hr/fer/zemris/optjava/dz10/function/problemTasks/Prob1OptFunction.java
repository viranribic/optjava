package hr.fer.zemris.optjava.dz10.function.problemTasks;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz10.function.problem1Functions.ComponentPowFunction;
import hr.fer.zemris.optjava.dz10.function.util.SimpleBoundary;
import hr.fer.zemris.optjava.dz10.interfaces.IBoundary;
import hr.fer.zemris.optjava.dz10.interfaces.IFunction;
import hr.fer.zemris.optjava.dz10.interfaces.MOOPProblem;

/**
 * Homework MOOP task 1.
 * 
 * @author Viran
 *
 */
public class Prob1OptFunction implements MOOPProblem {
	private static LinkedList<IFunction> functions = new LinkedList<>();
	private static LinkedList<IBoundary> boundaries = new LinkedList<>();
	private int solSpaceDim;
	private int objSpaceDim;
	private double[] maxs;
	private double[] mins;
	
	/**
	 * Prob1OptFunction constructor.
	 * 
	 * @param vectorDim
	 *            Dimension of the evaluated vector.
	 * @param lowBound
	 *            Lower function boundary.
	 * @param upBound
	 *            Upper function boundary.
	 */
	public Prob1OptFunction(int vectorDim, double[] lowBound, double[] upBound) {
		if (lowBound.length != upBound.length)
			throw new IllegalArgumentException("Boundaries restriction vectors do not match by length.");
		for (int i = 0; i < vectorDim; i++) {
			functions.add(new ComponentPowFunction(i));
		}

		for (int i = 0; i < lowBound.length; i++) {
			boundaries.add(new SimpleBoundary(lowBound[i], upBound[i], true, true));
		}
		this.solSpaceDim = vectorDim;
		this.objSpaceDim = functions.size();
		
		maxs = new double[solSpaceDim];
		int i = 0;
		for (IBoundary b : boundaries) {
			maxs[i++] = b.getMax();
		}
		i=0;
		mins = new double[solSpaceDim];
		for (IBoundary b : boundaries) {
			mins[i++] = b.getMin();
		}
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
	public int getObjectiveSpaceDim() {
		return objSpaceDim;
	}

	@Override
	public double[] getMinDomainVals() {
		
		return mins;
	}

	@Override
	public double[] getMaxDomainVals() {
		
		return maxs;
	}

}
