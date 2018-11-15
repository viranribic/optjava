package hr.fer.zemris.optjava.dz12.algorithm.selection;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz12.algorithm.population.CLBSolPopulation;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.interfaces.IRNG;
import hr.fer.zemris.optjava.dz12.interfaces.ISelection;
import hr.fer.zemris.optjava.dz12.util.random.RNG;

public class CLBSelection implements ISelection{
	
	private int tSize;
	
	public CLBSelection(int tSize) {
		this.tSize=tSize;
	}
	@Override
	public CLBSolution select(CLBSolPopulation population) {
		IRNG rand=RNG.getRNG();

		LinkedList<CLBSolution> candidates=new LinkedList<>();
		double bestCandidateFitness=0;
		int bestCandidatePos=0;
		for(int i=0;i<tSize;i++){
			candidates.add(population.get(rand.nextInt(0, population.size())));
			if(i==0){
				bestCandidateFitness=candidates.get(i).getError();
				bestCandidatePos=i;
			}else
				if(bestCandidateFitness>candidates.get(i).getError()){	//TODO check this 
					bestCandidateFitness=candidates.get(i).getError();
					bestCandidatePos=i;
				}
		}
		return candidates.get(bestCandidatePos).duplicate();
		
	}

}
