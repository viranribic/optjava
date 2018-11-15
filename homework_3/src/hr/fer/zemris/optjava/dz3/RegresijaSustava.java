package hr.fer.zemris.optjava.dz3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;

/**
 * Treca domaca zadaca iz OPTJAVA.
 * @author Viran
 *
 */
public class RegresijaSustava {



	private static final int solutionSpaceDomainDim=6;
	private static final double[] deltas=new double[] {5,5,5,5,5,5};

	private static final double mins = -5;
	private static final double maxs = 5;
	
	
	private static final double[] minStartVal = new double[] {-1,-1,-1,-1,-1,-1};
	private static final double[] maxStartVal = new double[] {1,1,1,1,1,1};
	
	private static final int outerLimit = 1000;
	private static final int innerLimit = 1000;
	private static final double alpha = 0.9999; //[0.5,0.99]
	private static final double tInitial = 10000;
	
	private static final ITempSchedule schedule=new GeomtericTempSchedule(alpha, tInitial, innerLimit, outerLimit);
	private static final boolean minimize = true;
	
	public static void main(String[] args) {
		//Check arguments
		if(args.length!=3){
			System.out.println("Neispravan broj varijabli.");
			return;
		}
		//Set input arguments
		String src=args[0];
		String taskType=args[1];
		
		//Set function
		IFunction function=parseInputFile2Function(src);
		
		//Algorithm approach: 
		if(taskType.startsWith("decimal")){
			
			IDecoder<DoubleArraySolution> decoder=new PassTroughDecoder();
			INeighborhood<DoubleArraySolution> neighborhood=new DoubleArrayNormNeighbourhood(deltas);
			DoubleArraySolution startsWith=new DoubleArraySolution(solutionSpaceDomainDim);
			startsWith.randomize(new Random(System.currentTimeMillis()), minStartVal, maxStartVal);
			if(args[2].equals("greedy")){
				IOptAlgorithm<DoubleArraySolution> optimisation=new GreedyAlgorithm<DoubleArraySolution>(decoder, neighborhood, startsWith,function, minimize);
				optimisation.run();
			}else if(args[2].equals("annealing")){
				IOptAlgorithm<DoubleArraySolution> optimisation=new SimulatedAnneling<DoubleArraySolution>(decoder, neighborhood, startsWith,function, schedule, minimize);
				optimisation.run();
			}
		}else if(taskType.startsWith("binary")){
			//determinate bits per vector
			String[] subArgs=taskType.split(":");
			int bitsPerVariable=Integer.parseInt(subArgs[1]);
			if(bitsPerVariable<5 ){
				System.out.println("Ogranicite broj bitova po varijabli na 5 jedinica.");
				return;
			}
			//set decoder
			double[] minsArray=new double[solutionSpaceDomainDim*bitsPerVariable];
			double[] maxsArray=new double[solutionSpaceDomainDim*bitsPerVariable];
			for(int i=0;i<minsArray.length;i++){
				minsArray[i]=mins;
				maxsArray[i]=maxs;
				
			}
			int[] initialBits=new int[solutionSpaceDomainDim*bitsPerVariable];
			BitvectorDecoder decoder=new GreyBinaryDecoder(minsArray, maxsArray,initialBits , solutionSpaceDomainDim);
			//set neighbourhood
			INeighborhood<BitvectorSolution> neighborhood= new BitvectorNeighborhood(bitsPerVariable, bitsPerVariable*solutionSpaceDomainDim);
			//set starting point
			BitvectorSolution startsWith=new BitvectorSolution(bitsPerVariable*solutionSpaceDomainDim);
			startsWith.randomize(new Random(System.currentTimeMillis()));  //startsWith.randomize(random);
			if(args[2].equals("greedy")){
				IOptAlgorithm<BitvectorSolution> optimisation=new GreedyAlgorithm<BitvectorSolution>(decoder, neighborhood, startsWith,function, minimize);
				optimisation.run();
				
			}else if(args[2].equals("annealing")){
				IOptAlgorithm<BitvectorSolution> optimisation=new SimulatedAnneling<BitvectorSolution>(decoder, neighborhood, startsWith,function, schedule, minimize);
				optimisation.run();
			}
		}else{
			System.out.println("Predani nacin rjesavanj nije podrzan.");
		}
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
