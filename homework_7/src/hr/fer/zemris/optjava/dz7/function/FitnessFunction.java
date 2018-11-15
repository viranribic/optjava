package hr.fer.zemris.optjava.dz7.function;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz7.function.neuralNetwork.FFANN;
import hr.fer.zemris.optjava.dz7.function.neuralNetwork.ITransferFunction;
import hr.fer.zemris.optjava.dz7.util.IrisData;

/**
 * FitnessFunction evaluator for Iris Data classification problem.
 * @author Viran
 *
 */
public class FitnessFunction {

	private FFANN neuralNetwork;
	
	/**
	 * FitnessFunction constructor.
	 * @param networkLayerNodes Neural network design represented as list of layers, with number being the nodes in each layer.
	 * @param transferFunctions Transfer functions used in each layer.
	 * @param dataset Data from which we train our neural network.
	 */
	public FitnessFunction(int[] networkLayerNodes, ITransferFunction[] transferFunctions,LinkedList<IrisData> dataset) {
		this.neuralNetwork = new FFANN(networkLayerNodes, transferFunctions, dataset);
	}
	
	/**
	 * Calculate error value.
	 * @param weights Weights in need of evaluation.
	 * @return Error value.
	 */
	public double valueAt(double[] weights){
		double error=0;
		LinkedList<IrisData> dataset=neuralNetwork.getDataSet();
		for(IrisData dataElement:dataset){
			
			double[] outputs=new double[dataElement.outputDataSize()];
			neuralNetwork.calcOuptputs(dataElement.getInputValues(), weights, outputs);
			
			double innerSum=0;
			for(int outputElement=0;outputElement<dataElement.outputDataSize();outputElement++){
				double t=dataElement.getOutputValue(outputElement);
				double y=outputs[outputElement];
				innerSum+=(t-y)*(t-y);
			}
			error+=innerSum;
		}
		error/=dataset.size();
		return error;
	}

	/**
	 * Get number of weights for neural network used.
	 * @return Neural network weights.
	 */
	
	public int getWeightsCount() {
		return neuralNetwork.getWeightsCount();
	}
	
	/**
	 * Get the neural network used.
	 * @return Neural network.
	 */
	public FFANN getNeuralNetwork(){
		return this.neuralNetwork;
	}
}
