package hr.fer.zemris.optjava.dz12.algorithm.population.solution;

import java.util.Iterator;
import java.util.LinkedList;

public class CLBSolutionPack implements Iterable<CLBSolution>{

	private LinkedList<CLBSolution> solutionPack=new LinkedList<>();
	
	public boolean add(CLBSolution solution){
		return solutionPack.add(solution);
	}

	public CLBSolution get(int index){
		return solutionPack.get(index);
	}
	
	public int size(){
		return solutionPack.size();
	}
	
	@Override
	public Iterator<CLBSolution> iterator() {
		return solutionPack.iterator();
	}
	
	
	
}
