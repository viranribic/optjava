package hr.fer.zemris.optjava.dz7.function.neuralNetwork;

import java.util.Random;

/**
 * Neural network node representation.
 * @author Viran
 *
 */
public class NNNode {
	private static Random rand=new Random();
	
	private double net;
	
	/**
	 * Make a node with random value. Used in debugging only.
	 */
	public NNNode() {
		this.net=rand.nextDouble();
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
