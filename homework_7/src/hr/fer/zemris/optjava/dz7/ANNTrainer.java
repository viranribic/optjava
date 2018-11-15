package hr.fer.zemris.optjava.dz7;

import java.util.LinkedList;
import java.util.Scanner;

import hr.fer.zemris.optjava.dz7.algorithms.cloneAlg.CloneAlgorithm;
import hr.fer.zemris.optjava.dz7.algorithms.psoAlg.PSOAlgorithm;
import hr.fer.zemris.optjava.dz7.function.FitnessFunction;
import hr.fer.zemris.optjava.dz7.function.neuralNetwork.FFANN;
import hr.fer.zemris.optjava.dz7.function.neuralNetwork.ITransferFunction;
import hr.fer.zemris.optjava.dz7.function.neuralNetwork.SigmoidTransferFunction;
import hr.fer.zemris.optjava.dz7.solution.DoubleArraySolution;
import hr.fer.zemris.optjava.dz7.util.Constants;
import hr.fer.zemris.optjava.dz7.util.IrisData;
import hr.fer.zemris.optjava.dz7.util.IrisDataParser;

/**
 * Starting class for running ANNTrainer problem.
 * 
 * @author Viran
 *
 */
public class ANNTrainer {

	private static String fileSrc;
	private static String alg;
	private static int popSize;
	private static double mErr;
	private static int maxIter;
	private static LinkedList<IrisData> data;
	private static FitnessFunction fFunct;
	private static DoubleArraySolution runSolution;

	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Potrebno je unjeti 5 parametara:\n" + "Putanju do datoteke podataka iris\n"
					+ "Oznaka algoritma{\n" + "\t pso-a \n" + "\t pso-b-d:\n" + "\t\t d is local_neighbourhood/2 \n"
					+ "\t clonalg-[selectedP]-[bornP]-[beta]:\n"
					+ "\t\t selectedP is the percentage of the total population size selected for cloning\n"
					+ "\t\t bornP is the percentage of the total population size selected for cloning \n"
					+ "Velicina populacije\n" + "Prihvatljivo srednje kvadratno odstupanje\n"
					+ "Maksimalni broj generacija");

			return;
		}

		fileSrc = args[0];
		alg = args[1].toLowerCase();
		popSize = Integer.parseInt(args[2]);
		mErr = Double.parseDouble(args[3]);
		maxIter = Integer.parseInt(args[4]);

		data = IrisDataParser.parseSourceData(fileSrc);

		fFunct = new FitnessFunction(new int[] { 4, 5, 3, 3 }, new ITransferFunction[] { new SigmoidTransferFunction(),
				new SigmoidTransferFunction(), new SigmoidTransferFunction() }, data);

		if (alg.startsWith("pso-")) {
			configPso();
		} else {
			configCloneAlg();
		}

		interactivePhase();
	}

	/**
	 * Let user test the generated network configuration on his data.
	 */
	private static void interactivePhase() {
		System.out.println("Do you want to test data on constructed network?");
		Scanner input = new Scanner(System.in);
		String inputLine = input.nextLine();
		if (inputLine.substring(0, 1).toLowerCase().equals("y")) {
			FFANN nNetwork = fFunct.getNeuralNetwork();
			while (true) {
				System.out.println("Set your values:");
				inputLine = input.nextLine();
				try {
					if (inputLine.toLowerCase().equals("stop"))
						break;
					String[] inputArgs = inputLine.split(" ");
					if (inputArgs.length != nNetwork.getInputSize()) {
						System.out.println("Input arguments do not match the network inputs.\nNetwork requires "
								+ nNetwork.getInputSize() + " inputs.");
						continue;
					}

					double[] inputDoubles = new double[inputArgs.length];
					int i = 0;
					for (String s : inputArgs)
						inputDoubles[i++] = Double.parseDouble(s);
					double[] solution = new double[nNetwork.getOutputSize()];
					nNetwork.calcOuptputs(inputDoubles, runSolution.getValues(), solution);

					System.out.println("Generated result: ");
					for (int j = 0; j < solution.length; j++) {
						if (solution[j] < 0.5)
							solution[j] = 0;
						else
							solution[j] = 1;

						System.out.print(solution[j] + " ");
					}
					System.out.println();

				} catch (RuntimeException e) {
					System.out.println("Invalid input. Please repeat your request or type 'stop' to exit.");
					continue;
				}
			}

		}
		input.close();
	}

	/**
	 * Separate method for starting the clone algorithm.
	 */
	private static void configCloneAlg() {
		alg = alg.replace("clonealg-", "");
		String[] cloneArgs = alg.split("-");
		int numSelected;
		int numBorn;
		int beta;
		if (cloneArgs.length != 3) {
			numSelected = (int) popSize;
			numBorn = (int) (0.1 * popSize);
			beta = Constants.BETA;
		} else {
			numSelected = (int) (popSize * Double.parseDouble(cloneArgs[0]));
			numBorn = (int) (popSize * Double.parseDouble(cloneArgs[1]));
			beta = Integer.parseInt(cloneArgs[2]);
		}
		System.out.println("Recived input:"+numSelected+" "+numBorn+" "+beta);
		try {
			Thread.sleep(10_000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CloneAlgorithm cloneAlg = new CloneAlgorithm(fFunct, popSize, mErr, maxIter, numSelected, numBorn, beta);
		runSolution = cloneAlg.run();
	}

	/**
	 * Separate method for starting the particle swarm optimisation algorithm.
	 */
	private static void configPso() {
		alg = alg.replace("pso-", "");

		PSOAlgorithm psoAlg = null;
		if (alg.startsWith("a")) {
			psoAlg = new PSOAlgorithm(fFunct, popSize, mErr, maxIter, true);
		} else if (alg.startsWith("b-")) {
			int neighbourhoodSize;
			if (alg.contains("b-")) {
				alg = alg.replace("b-", "");
				neighbourhoodSize = Integer.parseInt(alg);
			} else {
				neighbourhoodSize = (int) (0.15 * popSize);
			}
			psoAlg = new PSOAlgorithm(fFunct, popSize, mErr, maxIter, neighbourhoodSize, false);
		} else {
			System.out.println("Pso algorithm input format invalid.");
			return;
		}
		runSolution = psoAlg.run();
	}
}
