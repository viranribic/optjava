package test;

import hr.fer.zemris.optjava.dz12.algorithm.crossover.CLBCrossover;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.interfaces.ICrossover;
import hr.fer.zemris.optjava.dz12.util.AlgConstants;

public class CrossoverTest {

	public static void main(String[] args) {
		ICrossover xover=new CLBCrossover();
		CLBSolution parentA=new CLBSolution(2, 6);
		CLBSolution parentB=new CLBSolution(2, 6);
		parentA.init(AlgConstants.NUM_OR_VARS_PLACEHOLDER);
		parentB.init(AlgConstants.NUM_OR_VARS_PLACEHOLDER);
		System.out.println("ParentaA\n"+parentA);
		System.out.println("ParentaB\n"+parentB);
		CLBSolution child=xover.crossover(parentA, parentB);
		System.out.println("Child\n"+child);

		
	}
}
