package hr.fer.zemris.optjava.dz13.test;

import hr.fer.zemris.optjava.dz13.algorithm.operators.Crossover;
import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.util.AlgConst;
import hr.fer.zemris.optjava.dz13.util.GPPopulation;
import hr.fer.zemris.optjava.dz13.util.InitGPPopulation;

public class CrossoverTest {

	public static void main(String[] args) {
		GPPopulation pop= InitGPPopulation.genInitialPopulation(2, AlgConst.INIT_MAX_DEPTH,AlgConst.MAX_NODE_COUNT);
		GPSolution parentA=pop.get(0);
		GPSolution parentB=pop.get(1);
		Crossover crossover=new Crossover();
		GPSolution child=crossover.crossover(parentA, parentB);
		//System.out.println("Parent A\n");
		//System.out.println(parentA.getRoot());
		//System.out.println("\n\nParent B\n");
		//System.out.println(parentB.getRoot());
		//System.out.println("\n\nChild\n");
		System.out.println("Final child tree:\n"+child.getRoot());
		
	}
}
