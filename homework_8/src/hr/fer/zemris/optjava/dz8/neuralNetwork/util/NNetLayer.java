package hr.fer.zemris.optjava.dz8.neuralNetwork.util;

import java.util.ArrayList;

import hr.fer.zemris.optjava.dz8.interfaces.ITransferFunction;

/**
 * Neuron network layer model.
 * @author Viran
 *
 */
public class NNetLayer {

	private ArrayList<NNNode> layerNodes;
	private ITransferFunction transFunct;
	
	/**
	 * NNetLayer constructor. 
	 * @param numberOfNodes Number of nodes contained in this layer.
	 * @param transFunct Transfer function of this layer.
	 */
	public NNetLayer(int numberOfNodes, ITransferFunction transFunct) {
		this.layerNodes=new ArrayList<>(numberOfNodes);
		for(int i=0;i<numberOfNodes;i++){
			layerNodes.add(new NNNode());
		}
		
		this.transFunct=transFunct;
	}


	/**
	 * For the given next layer nodes, calculate all edge combinations from this to next layer and return thath number.
	 * @param nextLayer Next layer nodes.
	 * @return Number of edges connecting this and next layer.
	 */
	public int calcDifferentEdges(NNetLayer nextLayer) {
		int nextLayerNodes=nextLayer.nodeCount();
		return this.nodeCount()*nextLayerNodes;
	}
	
	/**
	 * Number of nodes in this layer.
	 * @return
	 */
	public int nodeCount(){
		return layerNodes.size();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("Number of nodes:"+this.nodeCount()+"\n");
		sb.append("Layer nodes values: \n");
		for(NNNode node:layerNodes)
			sb.append(node);
		sb.append("\nwith Transfer function: "+transFunct+"\n");
		return sb.toString();
	}


	/**
	 * Initialise net node values with the given input array values.
	 * @param inputs Initial values of this layer's nodes.
	 */
	public void initNet(double[] inputs) {
		if(inputs.length!=layerNodes.size())
			throw new IllegalArgumentException("Input given must match the network nodes.");
		for(int i=0;i<inputs.length;i++){
			NNNode curNode=layerNodes.get(i);
			curNode.setNet(inputs[i]);
		}
	}


	/**
	 * Calculate the net values of each node in this array passed through the transfer function.
	 * The net values is defined as the sum of all n-1 layer values multiplied by the specific weight.
	 * The final value set in this node is the output value of the node( defined as Node.ner=fTrans(Node.net)).
	 * @param prevLayer Previous layer nodes.
	 * @param weightInThisLayer Edge weight between input and output nodes.
	 */
	public void calcNodeValues(NNetLayer prevLayer, ArrayList<ArrayList<Double>> weightInThisLayer) {
		if(weightInThisLayer.size()!=layerNodes.size())
			throw new RuntimeException("Number of node weights containers and this layer nodes in calcNodevalues do not match.");
		
		//for all layer nodes
		for(int i=0;i<layerNodes.size();i++){
			//check if weightInThisLayer have enough elements for all
			ArrayList<Double> weightsPerNode=weightInThisLayer.get(i);
			if(weightsPerNode.size()-1!=prevLayer.nodeCount())	//-1 for the threshold
				throw new RuntimeException("Number of weights and previous layer nodes in calcNodevalues do not match.");
			
			double net=0;
			for(int j=0;j<prevLayer.nodeCount();j++){
				NNNode preLayerNode=prevLayer.layerNodes.get(j);
				net+=preLayerNode.getNet()*weightsPerNode.get(j);
			}
			int thresholdWeight=weightsPerNode.size()-1;
			net+=weightsPerNode.get(thresholdWeight);
			
			double[] transOutput=transFunct.transFuntcVal(new double[] {net});
			layerNodes.get(i).setNet(transOutput[0]);
		}		
		
	}

	

	/**
	 * Get node net output value.
	 * @param i Node index.
	 * @return Net value.
	 */
	public double getNodeOutput(int i) {
		return layerNodes.get(i).getNet();
	}


	public void setNodeValue(int i, double net) {
		layerNodes.get(i).setNet(net);
	}


	public void calcNodeValues(NNetLayer prevLayer, NNetLayer contextLayer,
			ArrayList<ArrayList<Double>> weightInThisLayer) {
		
		if(weightInThisLayer.size()!=layerNodes.size())
			throw new RuntimeException("Number of node weights containers and this layer nodes in calcNodevalues do not match.");
		
		//for all layer nodes
		for(int i=0;i<layerNodes.size();i++){
			//check if weightInThisLayer have enough elements for all
			ArrayList<Double> weightsPerNode=weightInThisLayer.get(i);
			if(weightsPerNode.size()-1!=prevLayer.nodeCount()+contextLayer.nodeCount())	//-1 for the threshold
				throw new RuntimeException("Number of weights and previous layer nodes in calcNodevalues do not match.");
			
			double net=0;
			int weightPos=0;
			
			for(int j=0;j<prevLayer.nodeCount();j++){
				NNNode preLayerNode=prevLayer.layerNodes.get(j);
				net+=preLayerNode.getNet()*weightsPerNode.get(weightPos++);
			}
			for(int j=0;j<contextLayer.nodeCount();j++){
				NNNode contextLayerNode=contextLayer.layerNodes.get(j);
				net+=contextLayerNode.getNet()*weightsPerNode.get(weightPos++);
			}
			int thresholdWeight=weightsPerNode.size()-1;
			net+=weightsPerNode.get(thresholdWeight);
			
			double[] transOutput=transFunct.transFuntcVal(new double[] {net});
			layerNodes.get(i).setNet(transOutput[0]);
		}		
		
	}
	
	public double[] getNodeValues(){
		double[] values=new double[layerNodes.size()];
		for(int i=0;i<layerNodes.size();i++)
			values[i]=layerNodes.get(i).getNet();
		return values;
	}
}
