package hr.fer.zemris.optjava.dz7.function.neuralNetwork;

import java.util.ArrayList;
import java.util.LinkedList;


import hr.fer.zemris.optjava.dz7.util.IrisData;

/**
 * Neural network class for iris data classification.
 * @author Viran
 *
 */
public class FFANN {

	private NNetLayer inputLayer;
	private LinkedList<NNetLayer> hiddenLayerList=new LinkedList<>();
	private NNetLayer outputLayer;
	
	private LinkedList<IrisData> dataset;
	private ArrayList<ArrayList<ArrayList<Double>>> weightsByLayer;
	/**
	 * FFANN constructor.
	 * @param layerSize List of numbers of nodes per each layer.
	 * @param transferFunction Transfer function used for each layer.
	 * @param dataset Input data prepared for evaluation.
	 */
	@SuppressWarnings("unchecked")
	public FFANN(int[] layerSize, ITransferFunction[] transferFunction, LinkedList<IrisData> dataset){
		if(layerSize.length<2 || (layerSize.length-1)!=transferFunction.length)
			throw new IllegalArgumentException("Numer of layers is too low.");
		
		this.dataset=(LinkedList<IrisData>)dataset.clone();
		
		int numberOfLayers=layerSize.length;
		
		inputLayer=new NNetLayer(layerSize[0],new LinearTransferFunction());
		
		if(layerSize.length>2)
			for(int thisLayerSize=1, transFunc=0;thisLayerSize<numberOfLayers-1;thisLayerSize++,transFunc++){
				NNetLayer hidLay=new NNetLayer(layerSize[thisLayerSize], transferFunction[transFunc]);
				hiddenLayerList.add(hidLay);
			}
		
		outputLayer=new NNetLayer(layerSize[numberOfLayers-1],transferFunction[transferFunction.length-1]);
	}
	
	/**
	 * Get number of weights this neural network has.
	 * @return Number of weight edges of this network.
	 */
	public int getWeightsCount(){
		int weightsCount=0;
		//add first to next layers
		if(hiddenLayerList.size()!=0)
			weightsCount+=inputLayer.calcDifferentEdges(hiddenLayerList.get(0));
		else
			weightsCount+=inputLayer.calcDifferentEdges(outputLayer);
		
		//add hidden layers
		for(int i=0;i<hiddenLayerList.size();i++){
			if(i<hiddenLayerList.size()-1){
				weightsCount+=hiddenLayerList.get(i).calcDifferentEdges(hiddenLayerList.get(i+1));
				weightsCount+=hiddenLayerList.get(i).nodeCount();
			}else{// if we're at last hidden layer take into consideration the output layer
				weightsCount+=hiddenLayerList.get(i).calcDifferentEdges(outputLayer);	
				weightsCount+=hiddenLayerList.get(i).nodeCount();
			}
		}
	
		weightsCount+=outputLayer.nodeCount();
		
		return weightsCount;
	}
	
	/**
	 * For the generated inputs, using the specific weights, calculate the outputs of this neural network.
	 * @param inputs Array of input values.
	 * @param weights Array of edge weight values.
	 * @param outputs Array of output values.
	 */
	public void calcOuptputs(double[] inputs, double[] weights, double[] outputs){
		
		NNetLayer workingLayer=inputLayer;
		//1. 	initialise the input nets as specified in input array
		workingLayer.initNet(inputs);
		//1.0.5 break the weight into separated lists
		refactorWeightList(weights);
		//1.1	the that layer as current working layer
		//2. 	for all layers in hidden layers
		for(int i=0;i<hiddenLayerList.size();i++){
			//			take the current hidden layer
			NNetLayer nextLayer=hiddenLayerList.get(i);
			ArrayList<ArrayList<Double>> weightInThisLayer=weightsByLayer.get(i);
			
			//			calculate net values of each of its nodes by summing up the node outputs and the corresponding weights
			nextLayer.calcNodeValues(workingLayer,weightInThisLayer);
			//			move the current working layer to the one with the calulated nets
			workingLayer=nextLayer;
		}
		//3. repeat these steps one more time for the outputLayer
		
		//working layer is set to last hidden or input

		//get the final set of weights
		int lastLayer=weightsByLayer.size()-1;
		ArrayList<ArrayList<Double>> weightInThisLayer=weightsByLayer.get(lastLayer);

		outputLayer.calcNodeValues(workingLayer,weightInThisLayer);
		
		// output layer results to output array
		for(int i=0;i<outputs.length;i++)
			outputs[i]=outputLayer.getNodeOutput(i);
	}
	
	/**
	 * Get the data set for this network.
	 * @return Data set contained.
	 */
	public LinkedList<IrisData> getDataSet(){
		return this.dataset;
	}
	
	/**
	 * Refactor input weight for easier data processing in program.
	 * @param weights Input weights.
	 */
	private void refactorWeightList(double[] weights) {
		weightsByLayer=new ArrayList<>(hiddenLayerList.size()+1); //prepare list for every layer + output layer
		int weightPosition=0;
	
		//set the previous layer to the inputLayer
		NNetLayer prevLayer=inputLayer;
		
		//for all hidden layers
		for(int i=0;i<hiddenLayerList.size();i++){
			int nextLayerNodesCount=hiddenLayerList.get(i).nodeCount();
			int prevLayerNodeCount=prevLayer.nodeCount();
			
			ArrayList<ArrayList<Double>> weightByNode=new ArrayList<>(nextLayerNodesCount);
			//for all nodes in the hidden layer
			for(int j=0;j<nextLayerNodesCount;j++){
				//for each node in the hidden array copy the input weight data
				ArrayList<Double> prevNodeWeights=new ArrayList<>(prevLayerNodeCount+1);//one threshold 
				for(int k=0;k<prevLayerNodeCount;k++){
					prevNodeWeights.add(weights[weightPosition++]);
				}
				
				prevNodeWeights.add(weights[weightPosition++]);
				
				weightByNode.add(prevNodeWeights);
			}
			
			weightsByLayer.add(weightByNode);
			prevLayer=hiddenLayerList.get(i);
		}
		
		//now prevLayerList has either the inputLayer as previous or the last hidden layer
		//add one last pas using the outputLayer
		
		int nextLayerNodesCount=outputLayer.nodeCount();
		int prevLayerNodeCount=prevLayer.nodeCount();
		
		ArrayList<ArrayList<Double>> weightByNode=new ArrayList<>(nextLayerNodesCount);
		//for all nodes in the hidden layer
		for(int j=0;j<nextLayerNodesCount;j++){
			//for each node in the hidden array copy the input weight data
			ArrayList<Double> prevNodeWeights=new ArrayList<>(prevLayerNodeCount+1);//one threshold 
			for(int k=0;k<prevLayerNodeCount;k++){
				prevNodeWeights.add(weights[weightPosition++]);
			}
			
			prevNodeWeights.add(weights[weightPosition++]);
			weightByNode.add(prevNodeWeights);
		}
		
		weightsByLayer.add(weightByNode);
	}
	
	/**
	 * TEST METHOD!
	 */
	public void testSetWeights(double[] weights){
		this.refactorWeightList(weights);
	}
	
	/**
	 * TEST METHOD!
	 */
	public void printInternalWeightRecord(){
		StringBuilder sb=new StringBuilder();
		sb.append("Input weight \"tree\" :");
		int layerCount=0;
		for(ArrayList<ArrayList<Double>> weightInLayer:weightsByLayer){
			layerCount++;
			sb.append("Layer:"+layerCount+"\n");
			
			int nodeInLayerCount=0;
			
			for(ArrayList<Double> weightInNode:weightInLayer){
				nodeInLayerCount++;
				sb.append("\tNode:"+nodeInLayerCount+"\n");
				
				for(Double d:weightInNode){
					sb.append("\t\t"+String.format("%3.2f ", d)+"\n");
				}
			}
		}
		
		System.out.println(sb.toString());
	}

	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append("Neural network:\n\n");
		sb.append("IN: "+inputLayer +"\n");
		int i=0;
		for(NNetLayer layer:hiddenLayerList)
			sb.append((i++)+" "+layer+"\n");
		sb.append("OUT: "+outputLayer+"\n");
		return sb.toString();
	}
	
	/**
	 * Get layer input node size.
	 * @return Layer inputs.
	 */
	public int getInputSize(){
		return inputLayer.nodeCount();
	}
	
	/**
	 * Get layer output node size.
	 * @return Layer outputs.
	 */
	public int getOutputSize(){
		return outputLayer.nodeCount();
	}
}
