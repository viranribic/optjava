package hr.fer.zemris.optjava.dz4.part1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import hr.fer.zemris.optjava.dz4.part1.constants.ConstantsTask1;
import hr.fer.zemris.optjava.dz4.part1.function.TransferFunction;
import hr.fer.zemris.optjava.dz4.part1.geneticAlgorithms.GenerationElitisticGA;
import hr.fer.zemris.optjava.dz4.part1.geneticOperators.selectionOperators.RouletteWheelSelection;
import hr.fer.zemris.optjava.dz4.part1.geneticOperators.selectionOperators.TournamentSelection;
import hr.fer.zemris.optjava.dz4.part1.interfaces.*;
/**
 * Genetic algorithm class. Start parameters: rouletteWheel / tournament:xx .
 * @author Viran
 *
 */
public class GeneticAlgorithm {

	
	private static final int NUM_OF_COM_LINE_ARGS=5;
	private static int populationSize;
	private static double minErrorValue;
	private static int maxIterationsAllowed;
	private static ISelection selection;
	private static double sigma;
	
	
	public static void main(String[] args) {
		
		parseInputArgs(args);
		IFunction function=parseInputFile2Function("02-zad-prijenosna.txt");

		IOptAlgorithm algorithm=new GenerationElitisticGA(populationSize, minErrorValue, maxIterationsAllowed, selection, sigma, function, ConstantsTask1.DOMAIN_DIM);
		algorithm.run();
	}

	/**
	 * Parse given input arguments.
	 * @param args Optimisation input  arguments.
	 */
	private static void parseInputArgs(String[] args) {
		if(args.length!=NUM_OF_COM_LINE_ARGS)
			throw new IllegalArgumentException("Input arguments are not valid.");
		populationSize=Integer.parseInt(args[0]);
		minErrorValue=Double.parseDouble(args[1]);
		maxIterationsAllowed=Integer.parseInt(args[2]);
		selection=assignSelection(args[3]);
		sigma=Double.parseDouble(args[4]);
	}

	/**
	 * For a given selection description, return the corresponding object.
	 * @param string Selection token.
	 * @return Selection object.
	 */
	private static ISelection assignSelection(String string) {
		ISelection option;
		if(string.equals("rouletteWheel"))
			option=new RouletteWheelSelection();
		else if(string.startsWith("tournament:")){
			//Decompose selection parameters
			String[] subArgs=string.split(":");
			if(subArgs.length!=2)
				throw new RuntimeException("Tournament selection args not valid.");		
			int n=Integer.parseInt(subArgs[1]);
			
			//minimal tournament size
			if(n<=2)						
				throw new RuntimeException("Tournament selection args not valid.");		
			option=new TournamentSelection(n);
		
		}else 
			throw new RuntimeException("Selection mode not supported.");
		return option;
	}

	/**
	 * Transforms the input file to a optimisation function.
	 * @param src File source.
	 * @return Optimisation function.
	 */
	public static IFunction parseInputFile2Function(String src){
		
		int numberOfVariables=5;
		
		LinkedList<LinkedList<Double>> functionsList=new LinkedList<>();
		LinkedList<Double> functionVlaues=new LinkedList<Double>();
		
		try (BufferedReader input = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(src)), "UTF-8"))) {
			
			
			while(true){
				String line=input.readLine();
				if(line==null)
					break;
				if(line.startsWith("#"))
					continue;
				if(line.startsWith("[")){
					line=line.replace("[", " ").replace("]", " ").trim();
					
					String[] coefs=line.split(",");
					LinkedList<Double> coefficients=new LinkedList<>();
					
					for(int i=0;i<numberOfVariables;i++){
						coefficients.add(Double.parseDouble(coefs[i]));
					}
					
					functionVlaues.add(Double.parseDouble(coefs[coefs.length-1]));
					functionsList.add(coefficients);
				}
			}
		} catch (IOException e) {

		} 
		
		TransferFunction optFunction= new TransferFunction(functionsList,functionVlaues);
		return optFunction;
		
	}
}
