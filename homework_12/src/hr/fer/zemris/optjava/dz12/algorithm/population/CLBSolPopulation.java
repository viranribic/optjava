package hr.fer.zemris.optjava.dz12.algorithm.population;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolutionPack;
import hr.fer.zemris.optjava.dz12.util.AlgConstants;

public class CLBSolPopulation implements Iterable<CLBSolution>{

	LinkedList<CLBSolution> population=new LinkedList<>();	
	
	public CLBSolPopulation(int popSize, int clbInputs,int numberOfCLBs, int numOfVars) {
		for(int i=0;i<popSize;i++){
			CLBSolution sol=new CLBSolution(clbInputs, numberOfCLBs);
			sol.init(numOfVars);
			population.add(sol);	
		}
	}

	public CLBSolPopulation() {
		
	}

	public int size() {
		return population.size();
	}

	public CLBSolution get(int nextInt) {
		return population.get(nextInt);
	}

	public void add(CLBSolution child) {
		population.add(child);
	}

	public void sort() {
		Collections.sort(population);
	}

	@Override
	public Iterator<CLBSolution> iterator() {
		return population.iterator();
	}

	public CLBSolutionPack preparePack(int packSize) {
		CLBSolutionPack pack =new CLBSolutionPack();
		this.sort();
		for(int i=0;i<AlgConstants.PACK_SIZE;i++)
			pack.add(population.get(i).duplicate());
		return pack;
	}

	public void remove(int index) {
		population.remove(index);
		
	}

	public void add(int index, CLBSolution solution) {
		population.add(index, solution);
		
	}
	
	
}
