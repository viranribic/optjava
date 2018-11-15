package hr.fer.zemris.optjava.dz4.part2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import hr.fer.zemris.optjava.dz4.part2.function.BoxFillFitness2;
import hr.fer.zemris.optjava.dz4.part2.geneticAlgorithms.BoxFillGeneticAlgorithm;

/**
 * Box filling problem.
 * Hint <a href="http://www.codeproject.com/Articles/633133/ga-bin-packing"> Link to solution idea. </a>
 * @author Viran
 *
 */
public class BoxFilling {

	private static final int NUM_OF_COM_LINE_ARGS = 7;

	private static int populationSize;
	private static int n;
	private static int m;
	private static boolean p;
	private static int maxIterations;
	private static int acceptableContainerSize;

	private static BoxFillFitness2 function;
	private static int maxHeight;

	public static void main(String[] args) {

		parseInput(args);
		BoxFillGeneticAlgorithm algorithm = new BoxFillGeneticAlgorithm(function, populationSize, n, m, p,
				maxIterations, acceptableContainerSize);
		algorithm.run();
	}

	/**
	 * Parse input arguments to variables fitted for further processing.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	private static void parseInput(String[] args) {
		if (args.length != NUM_OF_COM_LINE_ARGS)
			throw new IllegalArgumentException("Input arguments are not valid.");
		function = boxFillingFitnessFunction(args[0]);
		populationSize = Integer.parseInt(args[1]);
		n = Integer.parseInt(args[2]);
		m = Integer.parseInt(args[3]);
		p = (args[4].equals("true")) ? true : false;
		maxIterations = Integer.parseInt(args[5]);
		acceptableContainerSize = Integer.parseInt(args[6]);

	}

	/**
	 * Transforms the input file to a optimisation function.
	 * 
	 * @param src
	 *            File source.
	 * @return Optimisation function.
	 */
	public static BoxFillFitness2 boxFillingFitnessFunction(String src) {

		String fileName = src.substring(src.lastIndexOf("problem-")).replace("problem-", "");
		String boxHeightS = fileName.substring(0, fileName.indexOf('-'));
		maxHeight = Integer.parseInt(boxHeightS);
		LinkedList<Double> functionVlaues = new LinkedList<Double>();

		try (BufferedReader input = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(new FileInputStream(src)), "UTF-8"))) {

			String line = input.readLine();
			if (line.startsWith("[")) {
				line = line.replace("[", " ").replace("]", " ").trim();

				String[] coefs = line.split(",");
				for (String s : coefs)
					functionVlaues.add(Double.parseDouble(s));
			}
		} catch (IOException e) {

		}
		BoxFillFitness2 optFunction = new BoxFillFitness2(functionVlaues, maxHeight);
		return optFunction;
	}
}
