package test;

import hr.fer.zemris.optjava.dz12.algorithm.mutation.CLBMutation;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.interfaces.IMutation;
import hr.fer.zemris.optjava.dz12.util.AlgConstants;

public class CLBMutationTest {

	
	public static void main(String[] args) {
		CLBSolution child=new CLBSolution(2, 6);
		IMutation mut=new CLBMutation();
		child.init(AlgConstants.NUM_OR_VARS_PLACEHOLDER);
		System.out.println("Child\n"+child);
		System.out.println("Offspring\n"+mut.mutation(child));
		
		
	}
}
