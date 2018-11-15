package hr.fer.zemris.optjava.dz8.function;

import data.DataContainer;
import hr.fer.zemris.optjava.dz8.interfaces.IFitnessFunction;
import hr.fer.zemris.optjava.dz8.neuralNetwork.TDNN;

/**
 * FitnessFunction evaluator for Iris Data classification problem.
 * 
 * @author Viran
 *
 */
public class TDNNFitnessFunction implements IFitnessFunction<TDNN> {

	private TDNN neuralNetwork;
	private DataContainer dataset;

	/**
	 * FitnessFunction constructor.
	 * 
	 * @param networkLayerNodes
	 *            Neural network design represented as list of layers, with
	 *            number being the nodes in each layer.
	 * @param transferFunctions
	 *            Transfer functions used in each layer.
	 * @param dataset
	 *            Data from which we train our neural network.
	 */
	public TDNNFitnessFunction(TDNN inputNetwork, DataContainer dataset) {
		this.dataset = dataset;
		this.neuralNetwork = inputNetwork;
	}

	@Override
	public double valueAt(double[] weights) {
		double error = 0;
		
		for (int i = 0; i < dataset.numberOfTuples(); i++) {
			double[] iterationTuple = dataset.getTuple(i);

			double[] inputs = new double[dataset.tupleInputNum()];
			double[] predefinedOutput = new double[dataset.tupleOutputNum()];

			for (int j = 0; j < dataset.tupleInputNum(); j++){
				inputs[j] = iterationTuple[j];
			}
			

			for (int j = 0; j < dataset.tupleOutputNum(); j++){
				predefinedOutput[j] = iterationTuple[dataset.tupleInputNum() + j];
			}
			
			
			double[] outputs = new double[dataset.tupleOutputNum()];
			neuralNetwork.calcOuptputs(inputs, weights, outputs);

//			for (int j = 0; j < dataset.tupleOutputNum(); j++){
//				System.out.println("Should be: "+predefinedOutput[j]+" Is : "+outputs[j]+" ");
//			}
			
			double innerSum = 0;
			for (int outputElement = 0; outputElement < predefinedOutput.length; outputElement++) {
				double t = predefinedOutput[outputElement];
				double y = outputs[outputElement];
				innerSum += (t - y) * (t - y);
			}
			error += innerSum;
		}
		error /= dataset.numberOfTuples();

//		System.out.println("Error:"+error );
//		System.out.println("\n\n");
		return error;
	}

	@Override
	public int getParametersCount() {
		return neuralNetwork.getParametersCount();
	}

	/**
	 * Get the neural network used.
	 * 
	 * @return Neural network.
	 */
	public TDNN getNeuralNetwork() {
		return this.neuralNetwork;
	}

	public int numOfNodes() {
		return neuralNetwork.getNumberOfNodes();
	}

	@Override
	public DataContainer getData() {
		return dataset;
	}
}
