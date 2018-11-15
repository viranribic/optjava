package hr.fer.zemris.optjava.dz12.algorithm.population.solution;

import java.util.LinkedList;

public class CLBSolution implements Comparable<CLBSolution>{

	private LinkedList<CLB> clbs = new LinkedList<>();
	private double error;

	public CLBSolution() {
		this.error=Double.MAX_VALUE;
	}
	
	public CLBSolution(int clbInputs, int numberOfCLBs) {
		for (int index = 0; index < numberOfCLBs; index++) {
			clbs.add(new CLB(clbInputs, index));
		}
		this.error = 0;
	}

	public void init(int numOfVars) {
		for (CLB clb : clbs)
			clb.init(numOfVars);
	}

	public LinkedList<Integer> getCLBInputList(int index) {
		return clbs.get(index).getinputIndexList();
	}

	public LinkedList<Integer> getCLBLUT(int index) {
		return clbs.get(index).getLUT();
	}

	public void setCLB(int index, CLB clb) {
		clbs.remove(index);
		clbs.add(index, clb);
	}

	public CLB getCLB(int index) {
		return clbs.get(index);
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public CLBSolution duplicate() {
		CLBSolution clone =new CLBSolution();
		clone.error=this.error;
		clone.clbs=new LinkedList<>();
		for(int i=0;i<this.clbs.size();i++){
			clone.clbs.add(this.clbs.get(i).duplicate());
		}
		return clone;
	}

	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("Solution error: "+this.error+"\n");
		for(CLB clb:clbs)
			sb.append(clb).append("\n");
		return sb.toString();
	}

	public int getCLBInputs() {
		return clbs.getFirst().getClbInputs();
	}

	public int getNumberOfCLBs() {
		return clbs.size();
	}

	@Override
	public int compareTo(CLBSolution o) {
		return Double.compare(this.error, o.error);
	}

	public void copy(CLBSolution personalBest) {
		this.clbs=personalBest.clbs;
		this.error=personalBest.error;
		
	}
	
}
