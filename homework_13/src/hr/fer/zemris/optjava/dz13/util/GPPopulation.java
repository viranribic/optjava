package hr.fer.zemris.optjava.dz13.util;

import java.util.Iterator;
import java.util.LinkedList;

import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;

public class GPPopulation implements Iterable<GPSolution>{

	private LinkedList<GPSolution> population=new LinkedList<>();
	private GPSolution globalBest;
	
	public int size() {
		return population.size();
	}

	public void add(GPSolution solGenerated) {
		if(globalBest==null)
			globalBest=solGenerated;
		else 
			if(globalBest.getFitness()<solGenerated.getFitness())
				globalBest=solGenerated;
		population.add(solGenerated);
		
	}

	@Override
	public Iterator<GPSolution> iterator() {
		return population.iterator();
	}

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		int pos=0;
		for(GPSolution sol:population)
			sb.append((pos++)+" "+sol+"\n");
		return sb.toString();
	}

	public GPSolution get(int nextInt) {
		return population.get(nextInt);
	}

	public GPSolution getGlobalBest() {
		return globalBest;
	}

	public void remove(GPSolution globalBest) {
		this.globalBest=null;
		population.remove(population.indexOf(globalBest));
	}
}
