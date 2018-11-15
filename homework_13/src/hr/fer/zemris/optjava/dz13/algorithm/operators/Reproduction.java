package hr.fer.zemris.optjava.dz13.algorithm.operators;

import java.util.Random;

import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.util.GPPopulation;

public class Reproduction {

	private Random rand=new Random();
	
	public GPSolution reproduction(GPPopulation population){
		return population.get(rand.nextInt(population.size()));
	}
}
