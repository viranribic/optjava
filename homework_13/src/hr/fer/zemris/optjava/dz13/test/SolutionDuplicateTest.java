package hr.fer.zemris.optjava.dz13.test;

import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.util.AlgConst;
import hr.fer.zemris.optjava.dz13.util.SolTreeGenerator;

public class SolutionDuplicateTest {

	public static void main(String[] args) {
		GPSolution s= new GPSolution();
		s.setRoot(SolTreeGenerator.genRandTree(4, true, AlgConst.MAX_NODE_COUNT));
		GPSolution sC=s.duplicate();
		System.out.println(sC);
		
	}
}
