package hr.fer.zemris.optjava.dz8.neuralNetwork;

import java.util.ArrayList;
import java.util.LinkedList;

import hr.fer.zemris.optjava.dz8.interfaces.ITransferFunction;
import hr.fer.zemris.optjava.dz8.neuralNetwork.util.LinearTransferFunction;
import hr.fer.zemris.optjava.dz8.neuralNetwork.util.NNetLayerFast;

public class TDNNFast {
	 protected NNetLayerFast inputLayer;
	 protected LinkedList<NNetLayerFast> hiddenLayerList=new LinkedList<>();
	 protected NNetLayerFast outputLayer;
	
	protected ArrayList<ArrayList<ArrayList<Double>>> weightsByLayer;
	
	/**
	 * FFANN constructor.
	 * @param layerSize List of numbers of nodes per each layer.
	 * @param transferFunction Transfer function used for each layer.
	 * @param dataset Input data prepared for evaluation.
	 */
	
	public TDNNFast(int[] layerSize, ITransferFunction[] transferFunction){
		if(layerSize.length<2 || (layerSize.length-1)!=transferFunction.length)
			throw new IllegalArgumentException("Numer of layers is too low.");
		
		int numberOfLayers=layerSize.length;
		
		inputLayer=new NNetLayerFast(layerSize[0],new LinearTransferFunction());
		
		if(layerSize.length>2)
			for(int thisLayerSize=1, transFunc=0;thisLayerSize<numberOfLayers-1;thisLayerSize++,transFunc++){
				NNetLayerFast hidLay=new NNetLayerFast(layerSize[thisLayerSize], transferFunction[transFunc]);
				hiddenLayerList.add(hidLay);
			}
		
		outputLayer=new NNetLayerFast(layerSize[numberOfLayers-1],transferFunction[transferFunction.length-1]);
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
	 * Get the number of parameters this neural network needs for the optimisation algorithm.
	 * @return Dimension of optimisation algorithm problem domain.
	 */
	public int getParametersCount(){
		return this.getWeightsCount();
	}
	
	private int weightPos=0;
	
	/**
	 * For the generated inputs, using the specific weights, calculate the outputs of this neural network.
	 * @param inputs Array of input values.
	 * @param weights Array of edge weight values.
	 * @param outputs Array of output values.
	 */
	public void calcOuptputs(double[] inputs, double[] weights, double[] outputs){
		
		NNetLayerFast workingLayer=inputLayer;
		//1. 	initialise the input nets as specified in input array
		workingLayer.initNet(inputs);
		//1.0.5 break the weight into separated lists
		//refactorWeightList(weights);
		weightPos=0;
		//1.1	the that layer as current working layer
		//2. 	for all layers in hidden layers
		for(int i=0;i<hiddenLayerList.size();i++){
			//			take the current hidden layer
			NNetLayerFast nextLayer=hiddenLayerList.get(i);
			//ArrayList<ArrayList<Double>> weightInThisLayer=weightsByLayer.get(i);
			
			//			calculate net values of each of its nodes by summing up the node outputs and the corresponding weights
			weightPos=nextLayer.calcNodeValues(workingLayer,weights,weightPos);
			//			move the current working layer to the one with the calulated nets
			workingLayer=nextLayer;
		}
		//3. repeat these steps one more time for the outputLayer
		
		//working layer is set to last hidden or input

		//get the final set of weights

		outputLayer.calcNodeValues(workingLayer,weights,weightPos);
		
		// output layer results to output array
		for(int i=0;i<outputs.length;i++)
			outputs[i]=outputLayer.getNodeOutput(i);
	}
	
	
	
	/**
	 * Refactor input weight for easier data processing in program.
	 * @param weights Input weights.
	 */
	protected void refactorWeightList(double[] weights) {
		weightsByLayer=new ArrayList<>(hiddenLayerList.size()+1); //prepare list for every layer + output layer
		int weightPosition=0;
	
		//set the previous layer to the inputLayer
		NNetLayerFast prevLayer=inputLayer;
		
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
		for(NNetLayerFast layer:hiddenLayerList)
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
	
	/**
	 * Get the number of nodes in this network.
	 * @return Number of nodes.
	 */
	public int getNumberOfNodes(){
		int sum=0;
		sum+=inputLayer.nodeCount()+outputLayer.nodeCount();
		for(NNetLayerFast layer:hiddenLayerList)
			sum+=layer.nodeCount();
		return sum;
	}

}
