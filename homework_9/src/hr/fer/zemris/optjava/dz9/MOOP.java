package hr.fer.zemris.optjava.dz9;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import hr.fer.zemris.optjava.dz9.algorithm.NSGA;
import hr.fer.zemris.optjava.dz9.algorithm.NSGASolution;
import hr.fer.zemris.optjava.dz9.algorithm.RouletteWheelSelection;
import hr.fer.zemris.optjava.dz9.function.fitnessFunction.DecSpaceFitFunc;
import hr.fer.zemris.optjava.dz9.function.fitnessFunction.ObjSpaceFitFunc;
import hr.fer.zemris.optjava.dz9.function.problemTasks.Prob1OptFunction;
import hr.fer.zemris.optjava.dz9.function.problemTasks.Prob2OptFunction;
import hr.fer.zemris.optjava.dz9.interfaces.IMOOPOptFunction;
import hr.fer.zemris.optjava.dz9.interfaces.MOOPProblem;
import hr.fer.zemris.optjava.dz9.util.Constants;

/**
 * 9th OPTJAVA homework assignment.
 * @author Viran
 *
 */
public class MOOP {

	private static int problemLabel;
	private static int popSize;
	private static String type;
	private static int maxIter;
	private static double sigma;

	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println(
					"Expected parameters are as follows:\n" + "\t Integer 1 or 2, for first or second problem.\n"
							+ "\t Population size.\n" + "\t Domain type: \"decision-space\" or \"objective-space\".\n"
							+ "\t Maximal number of iterations.\n" + "\t Mutation sigma parameter.\n");
			return;
		}
		problemLabel = Integer.parseInt(args[0]);
		popSize = Integer.parseInt(args[1]);
		type = args[2];
		maxIter = Integer.parseInt(args[3]);
		sigma = Double.parseDouble(args[4]);
		IMOOPOptFunction function = null;
		if (type.equals("decision-space")) {
			function = new DecSpaceFitFunc(problemType(problemLabel));
		} else if (type.equals("objective-space")) {
			function = new ObjSpaceFitFunc(problemType(problemLabel));
		}
		NSGA algorithm = new NSGA(popSize, maxIter, new RouletteWheelSelection(), sigma, function);
		algorithm.run();
		saveToFile(algorithm);
		System.out.println("Algorithm is done. If you want to disable algorithm status monitoring during run time, set the Constants flag PRINT_TO_SCREEN to false.");
		
	}

	/**
	 * Save the front elements to files. Keep different files for decision space
	 * values and objective space values.
	 * 
	 * @param algorithm
	 *            Optimisation algorithm.
	 */
	private static void saveToFile(NSGA algorithm) {

		BufferedWriter outDec = null;
		BufferedWriter outObj = null;

		try {
			FileWriter decStream = new FileWriter("izlaz-dec.txt", false);
			FileWriter objStream = new FileWriter("izlaz-obj.txt", false);

			outDec = new BufferedWriter(decStream);
			outObj = new BufferedWriter(objStream);

			LinkedList<LinkedList<NSGASolution>> paretoFronts = algorithm.getParetoFronts();
			int frontNum = 1;
			for (LinkedList<NSGASolution> front : paretoFronts) {
				System.out.println("Front " + (frontNum++));
				for (NSGASolution solution : front) {
					System.out.println(solution+"\n");
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
