package hr.fer.zemris.optjava.dz13.algorithm.operators;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.util.GPPopulation;


public class Selection {

	private int tSize;
	private Random rand=new Random();
	
	public Selection(int tSize) {
		this.tSize=tSize;
	}
	
	public GPSolution select(GPPopulation population){

		int size = population.size();
		Set<GPSolution> tournament = new HashSet<>(tSize);
		while(tournament.size() < tSize) {
			tournament.add(population.get(rand.nextInt( size - 1)));
		}

		LinkedList<GPSolution> tList=new LinkedList<>(tournament);
		Collections.sort(tList);
		return tList.getLast();
	}
	
}
