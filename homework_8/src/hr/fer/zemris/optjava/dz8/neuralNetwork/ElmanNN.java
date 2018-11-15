package hr.fer.zemris.optjava.dz8.neuralNetwork;

import java.util.ArrayList;
import java.util.LinkedList;

import data.DataContainer;
import hr.fer.zemris.optjava.dz8.interfaces.ITransferFunction;
import hr.fer.zemris.optjava.dz8.neuralNetwork.util.LinearTransferFunction;
import hr.fer.zemris.optjava.dz8.neuralNetwork.util.NNetLayer;

public class ElmanNN {

	protected NNetLayer inputLayer;
	protected LinkedList<NNetLayer> hiddenLayerList = new LinkedList<>();
	protected NNetLayer outputLayer;

	protected ArrayList<ArrayList<ArrayList<Double>>> weightsByLayer;

	protected NNetLayer contextLayer;

	public ElmanNN(int[] layerSize, ITransferFunction[] transferFunction) {
		if (layerSize.length < 2 || (layerSize.length - 1) != transferFunction.length)
			throw new IllegalArgumentException("Numer of layers is too low.");

		int numberOfLayers = layerSize.length;

		inputLayer = new NNetLayer(layerSize[0], new LinearTransferFunction());

		if (layerSize.length > 2)
			for (int thisLayerSize = 1, transFunc = 0; thisLayerSize < numberOfLayers
					- 1; thisLayerSize++, transFunc++) {
				NNetLayer hidLay = new NNetLayer(layerSize[thisLayerSize], transferFunction[transFunc]);
				hiddenLayerList.add(hidLay);
			}

		outputLayer = new NNetLayer(layerSize[numberOfLayers - 1], transferFunction[transferFunction.length - 1]);
		contextLayer = new NNetLayer(hiddenLayerList.getFirst().nodeCount(), new LinearTransferFunction());

	}

	/**
	 * For the generated inputs, using the specific weights, calculate the
	 * outputs of this neural network.
	 * 
	 * @param inputs
	 *            Array of input values.
	 * @param weights
	 *            Array of edge weight values.
	 * @param outputs
	 *            Array of output values.
	 */
	public void calcOuptputs(double[] inputs, double[] weights, double[] outputs) {
		refactorWeightList(weights);

		//initContextLayer(inputs);

		NNetLayer workingLayer = inputLayer;
		// 1. initialise the input nets as specified in input array
		workingLayer.initNet(inputs);
		// 1.0.5 break the weight into separated lists

		// 1.1 the that layer as current working layer
		// 2. for all layers in hidden layers
		for (int i = 0; i < hiddenLayerList.size(); i++) {
			// take the current hidden layer
			NNetLayer nextLayer = hiddenLayerList.get(i);
			ArrayList<ArrayList<Double>> weightInThisLayer = weightsByLayer.get(i);

			// calculate net values of each of its nodes by summing up the node
			// outputs and the corresponding weights
			if (i == 0) {
				nextLayer.calcNodeValues(workingLayer, contextLayer, weightInThisLayer);
			} else {
				nextLayer.calcNodeValues(workingLayer, weightInThisLayer);
			}

			// move the current working layer to the one with the calulated nets
			workingLayer = nextLayer;
		}
		// 3. repeat these steps one more time for the outputLayer

		// working layer is set to last hidden or input

		// get the final set of weights
		int lastLayer = weightsByLayer.size() - 1;
		ArrayList<ArrayList<Double>> weightInThisLayer = weightsByLayer.get(lastLayer);

		outputLayer.calcNodeValues(workingLayer, weightInThisLayer);
		// output layer results to output array
		for (int i = 0; i < outputs.length; i++)
			outputs[i] = outputLayer.getNodeOutput(i);
		// copy the hidden layer elements to contextLayer
		copyHiddenToContext();
	}

	/**
	 * Initialise the context layer by copying the values of the first hidden
	 * layer after calculating the input values.
	 * 
	 * @param inputs
	 * @param weights 
	 */
	public void initContextLayer(double[] inputs, double[] weights) {
		refactorWeightList(weights);
		// calculate for hidden layer

		NNetLayer workingLayer = hiddenLayerList.getFirst();
		ArrayList<ArrayList<Double>> weightInThisLayer = weightsByLayer.get(0);

		// calculate net values of each of its nodes by summing up the node
		// outputs and the corresponding weights
		//all initial values of context layer are set to 0
		for (int i = 0; i < contextLayer.nodeCount(); i++) {
			contextLayer.setNodeValue(i, 0);
		}
		workingLayer.calcNodeValues(inputLayer, contextLayer, weightInThisLayer);

		copyHiddenToContext();
	}

	private void copyHiddenToContext() {
		// set the initial values
		for (int i = 0; i < contextLayer.nodeCount(); i++) {
			contextLayer.setNodeValue(i, hiddenLayerList.get(0).getNodeOutput(i));
		}
	}

	/**
	 * Refactor input weight for easier data processing in program.
	 * 
	 * @param weights
	 *            Input weights.
	 * @param contextLayerInitValues
	 */
	protected void refactorWeightList(double[] weights) {

		weightsByLayer = new ArrayList<>(hiddenLayerList.size() + 1); // prepare
																		// list
																		// for
																		// every
																		// layer
																		// +
																		// output
																		// layer
		int weightPosition = 0;

		// set the previous layer to the inputLayer
		NNetLayer prevLayer = inputLayer;

		// for all hidden layers
		for (int i = 0; i < hiddenLayerList.size(); i++) {
			int nextLayerNodesCount = hiddenLayerList.get(i).nodeCount();
			int prevLayerNodeCount = prevLayer.nodeCount();

			ArrayList<ArrayList<Double>> weightByNode = new ArrayList<>(nextLayerNodesCount);
			// for all nodes in the hidden layer
			for (int j = 0; j < nextLayerNodesCount; j++) {
				// for each node in the hidden array copy the input weight data
				ArrayList<Double> prevNodeWeights = new ArrayList<>(prevLayerNodeCount + 1);// one
																							// threshold
				for (int k = 0; k < prevLayerNodeCount; k++) {
					prevNodeWeights.add(weights[weightPosition++]);
				}
				// add the context layer elements if situated in the first
				// hidden layer
				if (i == 0) {
					for (int k = 0; k < contextLayer.nodeCount(); k++) {
						prevNodeWeights.add(weights[weightPosition++]);
					}
				}
				// threshold
				prevNodeWeights.add(weights[weightPosition++]);

				weightByNode.add(prevNodeWeights);
			}

			weightsByLayer.add(weightByNode);
			prevLayer = hiddenLayerList.get(i);

		}

		// now prevLayerList has either the inputLayer as previous or the last
		// hidden layer
		// add one last pas using the outputLayer

		int nextLayerNodesCount = outputLayer.nodeCount();
		int prevLayerNodeCount = prevLayer.nodeCount();

		ArrayList<ArrayList<Double>> weightByNode = new ArrayList<>(nextLayerNodesCount);
		// for all nodes in the hidden layer
		for (int j = 0; j < nextLayerNodesCount; j++) {
			// for each node in the hidden array copy the input weight data
			ArrayList<Double> prevNodeWeights = new ArrayList<>(prevLayerNodeCount + 1);// one
																						// threshold
			for (int k = 0; k < prevLayerNodeCount; k++) {
				prevNodeWeights.add(weights[weightPosition++]);
			}

			prevNodeWeights.add(weights[weightPosition++]);
			weightByNode.add(prevNodeWeights);
		}

		weightsByLayer.add(weightByNode);
		// ADDITION...................
//
//		for (int i = 0; i < contextLayer.nodeCount(); i++) {
//			contextLayer.setNodeValue(i, weights[weightPosition++]);
//		}
	}

	/**
	 * Get number of weights this neural network has.
	 * 
	 * @return Number of weight edges of this network.
	 */
	public int getWeightsCount() {
		int weightsCount = 0;
		// add first to next layers
		if (hiddenLayerList.size() != 0)
			weightsCount += inputLayer.calcDifferentEdges(hiddenLayerList.get(0));
		else
			weightsCount += inputLayer.calcDifferentEdges(outputLayer);

		// add hidden layers
		for (int i = 0; i < hiddenLayerList.size(); i++) {
			if (i < hiddenLayerList.size() - 1) {
				weightsCount += hiddenLayerList.get(i).calcDifferentEdges(hiddenLayerList.get(i + 1));
				weightsCount += hiddenLayerList.get(i).nodeCount();
			} else {// if we're at last hidden layer take into consideration the
					// output layer
				weightsCount += hiddenLayerList.get(i).calcDifferentEdges(outputLayer);
				weightsCount += hiddenLayerList.get(i).nodeCount();
			}
		}

		weightsCount += outputLayer.nodeCount();

		return weightsCount + contextLayer.calcDifferentEdges(hiddenLayerList.get(0));
	}

	/**
	 * Get the number of parameters this neural network needs for the
	 * optimisation algorithm.
	 * 
	 * @return Dimension of optimisation algorithm problem domain.
	 */
	public int getParametersCount() {
		return this.getWeightsCount() + contextLayer.nodeCount();
	}

	/**
	 * TEST METHOD!
	 */
	public void testSetWeights(double[] weights) {
		this.refactorWeightList(weights);
	}

	/**
	 * TEST METHOD!
	 */
	public void printInternalWeightRecord() {
		StringBuilder sb = new StringBuilder();
		sb.append("Input weight \"tree\" :");
		int layerCount = 0;
		for (ArrayList<ArrayList<Double>> weightInLayer : weightsByLayer) {
			layerCount++;
			sb.append("Layer:" + layerCount + "\n");

			int nodeInLayerCount = 0;

			for (ArrayList<Double> weightInNode : weightInLayer) {
				nodeInLayerCount++;
				sb.append("\tNode:" + nodeInLayerCount + "\n");

				for (Double d : weightInNode) {
					sb.append("\t\t" + String.format("%3.2f ", d) + "\n");
				}
			}
		}

		System.out.println(sb.toString());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Neural network:\n\n");
		sb.append("IN: " + inputLayer + "\n");
		sb.append("CONTEXT: " + contextLayer + "\n");
		int i = 0;
		for (NNetLayer layer : hiddenLayerList)
			sb.append((i++) + " " + layer + "\n");
		sb.append("OUT: " + outputLayer + "\n");
		return sb.toString();
	}

	/**
	 * Get layer input node size.
	 * 
	 * @return Layer inputs.
	 */
	public int getInputSize() {
		return inputLayer.nodeCount();
	}

	/**
	 * Get layer output node size.
	 * 
	 * @return Layer outputs.
	 */
	public int getOutputSize() {
		return outputLayer.nodeCount();
	}

	/**
	 * Get the number of nodes in this network.
	 * 
	 * @return Number of nodes.
	 */
	public int getNumberOfNodes() {
		int sum = 0;
		sum += inputLayer.nodeCount() + outputLayer.nodeCount();
		for (NNetLayer layer : hiddenLayerList)
			sum += layer.nodeCount();
		return sum + contextLayer.nodeCount();
	}

	/**
	 * For the given set of weights calculate the 
	 * @param weights
	 * @param data 
	 * @return
	 */
	public double[] getHiddenValuesForInput(double[] weights, DataContainer data) {
		// set the desired weights
		refactorWeightList(weights);
		ArrayList<ArrayList<Double>> weightInThisLayer = weightsByLayer.get(0);

		// clear the context field
		for(int i=0;i<contextLayer.nodeCount();i++)
			contextLayer.setNodeValue(i, 0);
		inputLayer.setNodeValue(0, data.getTuple(0)[0]);//get the first tuple and since it's an Elman's network, extract the first element which is the input and set it to the input layer value 
		hiddenLayerList.get(0).calcNodeValues(inputLayer, contextLayer, weightInThisLayer);
		copyHiddenToContext();

		return contextLayer.getNodeValues();
	}

	public double[] getHiddenValues(){
		return hiddenLayerList.get(0).getNodeValues();
	}
}
