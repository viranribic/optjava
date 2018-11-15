package hr.fer.zemris.optjava.dz10;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import hr.fer.zemris.optjava.dz10.algorithm.NSGA2;
import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Solution;
import hr.fer.zemris.optjava.dz10.algorithm.selection.CrowdedTournamentSelection;
import hr.fer.zemris.optjava.dz10.function.fitnessFunction.DecSpaceFitFunc;
import hr.fer.zemris.optjava.dz10.function.fitnessFunction.ObjSpaceFitFunc;
import hr.fer.zemris.optjava.dz10.function.problemTasks.Prob1OptFunction;
import hr.fer.zemris.optjava.dz10.function.problemTasks.Prob2OptFunction;
import hr.fer.zemris.optjava.dz10.interfaces.IMOOPOptFunction;
import hr.fer.zemris.optjava.dz10.interfaces.MOOPProblem;
import hr.fer.zemris.optjava.dz10.util.Constants;

/**
 * 10th OPTJAVA homework assignment.
 * 
 * @author Viran
 *
 */
@SuppressWarnings("unused")
public class MOOP {

	private static int problemLabel;
	private static int popSize;
	private static int maxIter;
	private static double sigma;

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Expected parameters are as follows:\n"
					+ "\t Integer 1 or 2, for first or second problem.\n" + "\t Population size.\n"
					+ "\t Maximal number of iterations.\n");
			return;
		}
		problemLabel = Integer.parseInt(args[0]);
		popSize = Integer.parseInt(args[1]);
		maxIter = Integer.parseInt(args[2]);
		sigma = Constants.SIGMA_DISTANCE;
		IMOOPOptFunction function = new ObjSpaceFitFunc(problemType(problemLabel));
		//IMOOPOptFunction function = new DecSpaceFitFunc(problemType(problemLabel));
		
		NSGA2 algorithm = new NSGA2(popSize, maxIter, new CrowdedTournamentSelection(), sigma, function);
		algorithm.run();
		saveToFile(algorithm);
		System.out.println(
				"Algorithm is done. If you want to disable algorithm status monitoring during run time, set the Constants flag PRINT_TO_SCREEN to false.");

	}

	/**
	 * Save the front elements to files. Keep different files for decision space
	 * values and objective space values.
	 * 
	 * @param algorithm
	 *            Optimisation algorithm.
	 */
	private static void saveToFile(NSGA2 algorithm) {

		BufferedWriter outDec = null;
		BufferedWriter outObj = null;

		try {
			FileWriter decStream = new FileWriter("izlaz-dec.txt", false);
			FileWriter objStream = new FileWriter("izlaz-obj.txt", false);

			outDec = new BufferedWriter(decStream);
			outObj = new BufferedWriter(objStream);

			LinkedList<LinkedList<NSGA2Solution>> paretoFronts = algorithm.getParetoFronts();
			int frontNum = 1;
			for (LinkedList<NSGA2Solution> front : paretoFronts) {
				System.out.println("Front " + (frontNum++)+"\n");
				//descending front sort
				Collections.sort(front);
				Collections.reverse(front);
				
				for (NSGA2Solution solution : front) {
					System.out.println(solution + "\n");
					outDec.write(solution.valToString() + "\n");
					outObj.write(solution.objToString() + "\n");
				}
				outDec.write("\n");
				outObj.write("\n");
			}
		} catch (IOException ignorable) {
		} finally {
			if (outDec != null) {
				try {
					outDec.close();
				} catch (IOException e) {
				}
			}
			if (outObj != null) {
				try {
					outObj.close();
				} catch (IOException e) {
				}
			}
		}

	}

	/**
	 * For the given label return the optimisation problem.
	 * 
	 * @param label
	 *            Optimisation label.
	 * @return Optimisation problem.
	 */
	private static MOOPProblem problemType(int label) {
		if (label == 1) {
			double[] prob1Min = new double[Constants.PROBLEM1_DOMAIN_DIM];
			double[] prob1Max = new double[Constants.PROBLEM1_DOMAIN_DIM];

			for (int i = 0; i < prob1Min.length; i++) {
				prob1Max[i] = Constants.UPPER_BOUNDARY_PROB1;
				prob1Min[i] = Constants.LOWER_BOUNDARY_PROB1;
			}

			return new Prob1OptFunction(Constants.PROBLEM1_DOMAIN_DIM, prob1Min, prob1Max);

		} else if (label == 2) {

			double[] prob2Min = new double[] { Constants.LOWER_BOUNDARY_PROB2_X1, Constants.LOWER_BOUNDARY_PROB2_X2 };
			double[] prob2Max = new double[] { Constants.UPPER_BOUNDARY_PROB2_X1, Constants.UPPER_BOUNDARY_PROB2_X2 };

			return new Prob2OptFunction(Constants.PROBLEM2_DOMAIN_DIM, prob2Min, prob2Max);
		} else
			throw new IllegalArgumentException("Unsupported type label.");
	}
}
