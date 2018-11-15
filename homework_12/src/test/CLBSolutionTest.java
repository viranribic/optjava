package test;

import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.util.AlgConstants;

public class CLBSolutionTest {

	
	public static void main(String[] args) {
		CLBSolution sol = new CLBSolution(1, 7);
		sol.init(AlgConstants.NUM_OR_VARS_PLACEHOLDER);
		System.out.println(sol);
	}
}
