package hr.fer.zemris.optjava.dz8.neuralNetwork.util;


/**
 * Neural network node representation.
 * @author Viran
 *
 */
public class NNNode {
	
	private double net;
	
	/**
	 * Make a node with random value. Used in debugging only.
	 */
	public NNNode() {
		this.net=0;

	}
	
	/**
	 * Get the net value of this node.
	 * @return Net value.
	 */
	public double getNet(){
		return net;
	}
	
	/**
	 * Set net value of this node.
	 * @param net New net value.
	 */
	public void setNet(double net){
		this.net=net;
	}
	
	@Override
	public String toString() {
		return String.format("%2.1f ", net);
	}
	
}
