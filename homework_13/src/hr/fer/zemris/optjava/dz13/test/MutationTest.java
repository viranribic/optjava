package hr.fer.zemris.optjava.dz13.test;

import hr.fer.zemris.optjava.dz13.algorithm.operators.Mutation;
import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.util.GPPopulation;
import hr.fer.zemris.optjava.dz13.util.InitGPPopulation;

public class MutationTest {

	public static void main(String[] args) {
		GPPopulation population=InitGPPopulation.genInitialPopulation(1, 6, 10);
		GPSolution sol=population.get(0);
		Mutation m= new Mutation();
		//System.out.println(sol.getRoot()+"\n");
		sol=m.mutate(sol);
		//System.out.println(sol.getRoot()+"\n");
	}
}
