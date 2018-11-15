package hr.fer.zemris.optjava.dz13.algorithm.population.solution;

import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;

public class GPSolution implements Comparable<GPSolution>{
	
	private SolNode root;
	private double fitness;
	private int nodesInTree;
	private int collected;
	
	public SolNode getRoot() {
		return root;
	}
	public void setRoot(SolNode root) {
		this.root = root;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public GPSolution duplicate(){
		GPSolution clone = new GPSolution();
		clone.root=this.root.duplicate();
		clone.fitness=this.fitness;
		clone.nodesInTree=this.nodesInTree;
		clone.collected=this.collected;
		return clone;
		
	}	
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append(String.format("F:%.4f ", fitness));
		//sb.append(root.labelToString(root.label)+" D: [");
		sb.append("D: [");
		for(int i=0;i<root.depths.length;i++){
			sb.append(root.depths[i]+" ");
		}
		sb.append("] Nodes: "+this.nodesInTree+"\n");
		return sb.toString();
	}
	public int getNodesInTree() {
		return nodesInTree;
	}
	public void setNodesInTree(int nodesInTree) {
		this.nodesInTree = nodesInTree;
	}
	@Override
	public int compareTo(GPSolution o) {
		return Double.compare(this.fitness, o.fitness);
	}
	public void setFoodCollected(int collected) {
		this.collected=collected;
		
	}
	public int  getFoodCollected() {
		return collected;
		
	}
}
