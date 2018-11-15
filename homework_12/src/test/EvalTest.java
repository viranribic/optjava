package test;

import hr.fer.zemris.optjava.dz12.algorithm.errorFunction.CLBEvaluator;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.util.AlgConstants;

public class EvalTest {
	
	public static void main(String[] args) {
		CLBEvaluator evaluator=new CLBEvaluator("a OR b");
		CLBSolution sol=new CLBSolution(2,10);
		sol.init(AlgConstants.NUM_OR_VARS_PLACEHOLDER);
		System.out.println("Solution:\n"+evaluator.evaluate(sol));
		
	}
}
