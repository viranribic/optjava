package hr.fer.zemris.optjava.dz6.algorithm;

import java.util.Arrays;

public class TSPSolution {

	public int[] cityIndexes;
	public double tourLength;
	public TSPSolution next;
	
	public TSPSolution(TSPSolution next) {
		this.next=next;
	}
	
	public TSPSolution() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return Arrays.toString(cityIndexes)+", length= "+tourLength;
	}
	
	
}

