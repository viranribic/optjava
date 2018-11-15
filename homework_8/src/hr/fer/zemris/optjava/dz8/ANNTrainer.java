package hr.fer.zemris.optjava.dz8;

import data.DataContainer;
import data.InputDataParser;
import hr.fer.zemris.optjava.dz8.algorithm.DifEvolAlgorithm;
import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;
import hr.fer.zemris.optjava.dz8.function.ElmanFitnessFunction;
import hr.fer.zemris.optjava.dz8.function.TDNNFitnessFunction;
import hr.fer.zemris.optjava.dz8.function.TanHTransferFuncition;
import hr.fer.zemris.optjava.dz8.interfaces.ITransferFunction;
import hr.fer.zemris.optjava.dz8.neuralNetwork.ElmanNN;
import hr.fer.zemris.optjava.dz8.neuralNetwork.TDNN;
import hr.fer.zemris.optjava.dz8.util.Constants;

public class ANNTrainer {

	public static void main(String[] args) {
		if(args.length!=5){
			System.out.println(
					"Expected parameters are as follows:\n"
						+"\t Path to file.\n"
							+"\t Network label.\n"
								+"\t Population size.\n"
									+"\t Minimal error.\n"
										+"\t Maximal iterations.\n"
						
							
					);
			return;
		}
		String fileSrc=args[0];
		String net=args[1];
		int populationSize=Integer.parseInt(args[2]);
		double mErr=Double.parseDouble(args[3]);
		int maxIter=Integer.parseInt(args[4]);
		int l=0;
		
		DifEvSolution resultingSolution=null;
		
		if(net.startsWith("tdnn")){
			net=net.replace("tdnn-", "");
			String[] dimString=net.split("x");
			l=Integer.parseInt(dimString[0]);
			
			int[] dimInt=new int[dimString.length];
			ITransferFunction[] transferFunctions=new ITransferFunction[dimString.length-1];
			
			for(int i=0;i<dimString.length;i++){
				dimInt[i]=Integer.parseInt(dimString[i]);
			}
			
			for(int i=0;i<dimString.length-1;i++){
				transferFunctions[i]=new TanHTransferFuncition();
			}
			DataContainer data=InputDataParser.parseFile(fileSrc, l,Constants.DATA_SIZE);
			
			TDNN nNetwork=new TDNN(dimInt,transferFunctions);
			
			TDNNFitnessFunction function=new TDNNFitnessFunction(nNetwork, data);
			DifEvolAlgorithm<TDNN> algorithm=new DifEvolAlgorithm<TDNN>(function,populationSize,mErr,maxIter);
			resultingSolution=algorithm.run();
			statistics(nNetwork,data,resultingSolution);
			
		}else if(net.startsWith("elman")){
			net=net.replace("elman-", "");
			String[] dimString=net.split("x");
			l=Integer.parseInt(dimString[0]);
			
			int[] dimInt=new int[dimString.length];
			ITransferFunction[] transferFunctions=new ITransferFunction[dimString.length-1];
			
			for(int i=0;i<dimString.length;i++){
				dimInt[i]=Integer.parseInt(dimString[i]);
			}
			
			for(int i=0;i<dimString.length-1;i++){
				transferFunctions[i]=new TanHTransferFuncition();
			}
			DataContainer data=InputDataParser.parseFile(fileSrc, l,Constants.DATA_SIZE);
	
			ElmanNN nNetwork=new ElmanNN(dimInt,transferFunctions);
			
			ElmanFitnessFunction function=new ElmanFitnessFunction(nNetwork, data);

			DifEvolAlgorithm<ElmanNN> algorithm=new DifEvolAlgorithm<ElmanNN>(function,populationSize,mErr,maxIter);
			resultingSolution=algorithm.run();
			statistics(nNetwork,data,resultingSolution);
		}else{
			System.out.println("Network type not supported.\nUse tdnn-ARH or elman-ARH, where ARH-> [number]x([nzmber]x)*[number]");
		}
	
	}

	private static void statistics(ElmanNN nNetwork, DataContainer data, DifEvSolution resultingSolution) {
		int numOfEqual=0;

		System.out.format("%10s %10s %10s\n","Table","Data","Result");
		
		for (int i = 0; i < data.numberOfTuples(); i++) {
			double[] iterationTuple = data.getTuple(i);

			double[] inputs = new double[data.tupleInputNum()];
			double[] predefinedOutput = new double[data.tupleOutputNum()];

			for (int j = 0; j < data.tupleInputNum(); j++)
				inputs[j] = iterationTuple[j];

			for (int j = 0; j < data.tupleOutputNum(); j++)
				predefinedOutput[j] = iterationTuple[data.tupleInputNum() + j];

			double[] outputs = new double[data.tupleOutputNum()];
			nNetwork.calcOuptputs(inputs, resultingSolution.getSolution(), outputs);

			boolean equal=true;
			for (int outputElement = 0; outputElement < predefinedOutput.length; outputElement++) {
				if(equal && !doubleEquals(predefinedOutput[outputElement],outputs[outputElement]))
					equal=false;
				System.out.format("%10d %10.5f %10.5f\n",i,predefinedOutput[outputElement],outputs[outputElement]);
			}
			System.out.println();
			if(equal)
				numOfEqual++;

			System.out.println("Number of equal="+numOfEqual);

		}
	}

	private static void statistics(TDNN nNetwork, DataContainer data, DifEvSolution resultingSolution) {
		int numOfEqual=0;

		System.out.format("%10s %10s %10s\n","Table","Data","Result");
		
		for (int i = 0; i < data.numberOfTuples(); i++) {
			double[] iterationTuple = data.getTuple(i);

			double[] inputs = new double[data.tupleInputNum()];
			double[] predefinedOutput = new double[data.tupleOutputNum()];

			for (int j = 0; j < data.tupleInputNum(); j++)
				inputs[j] = iterationTuple[j];

			for (int j = 0; j < data.tupleOutputNum(); j++)
				predefinedOutput[j] = iterationTuple[data.tupleInputNum() + j];

			double[] outputs = new double[data.tupleOutputNum()];
			nNetwork.calcOuptputs(inputs, resultingSolution.getSolution(), outputs);

			boolean equal=true;
			for (int outputElement = 0; outputElement < predefinedOutput.length; outputElement++) {
				if(equal && !doubleEquals(predefinedOutput[outputElement],outputs[outputElement]))
					equal=false;
				System.out.format("%10d %10.5f %10.5f\n",i,predefinedOutput[outputElement],outputs[outputElement]);
			}
			System.out.println();
			if(equal)
				numOfEqual++;
		}

		System.out.println("Number of equal="+numOfEqual);

		
	}

	private static boolean doubleEquals(double d1, double d2) {
		double diff=Math.abs(Math.abs(d1)-Math.abs(d2));
		return (diff<1E-5)?true:false;
	}
}
