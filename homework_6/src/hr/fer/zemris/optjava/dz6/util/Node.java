package hr.fer.zemris.optjava.dz6.util;

/**
 * A representation of a graph node.
 * @author Viran 
 *
 */
public class Node implements Comparable<Node>{

	public int label;
	public double y;
	public double x;

	/**
	 * Node constructor represented as a point in space.
	 * @param label Unique node label.
	 * @param x X position coordinate for this node.
	 * @param y Y position coordinate for this node.
	 */
	public Node(int label, double x, double y) {
		this.label=label;
		this.x=x;
		this.y=y;
	}
	
	@Override
	public String toString() {
		return String.format("%d %5.2f %f5.2", label,x,y);
	}

	@Override
	public int compareTo(Node o) {
		return this.label-o.label;
	}
}
