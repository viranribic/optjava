package hr.fer.zemris.optjava.dz11.geneticOperators.selectionOperators;

import java.util.LinkedList;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.interfaces.ISelectionOp;
import hr.fer.zemris.optjava.rng.IRNG;

/**
 * Tournament selection class.
 * @author Viran
 *
 */
public class TournamentSelection implements ISelectionOp{
	private int tournamentSize;
	private LinkedList<GASolution<int[]>> population;
	
	/**
	 * TournamentSelection constructor.
	 * @param n Tournament size.
	 */
	public TournamentSelection(int n) {
		tournamentSize=n;
	}


	@Override
	public void setPopulation(LinkedList<GASolution<int[]>> population) {
		this.population=population;
		
	}

	@Override
	public GASolution<int[]> selectRandomSubject(IRNG rand) {
		LinkedList<GASolution<int[]>> candidates=new LinkedList<>();
		double bestCandidateFitness=0;
		int bestCandidatePos=0;
		for(int i=0;i<tournamentSize;i++){
			candidates.add(population.get(rand.nextInt(0, population.size())));
			if(i==0){
				bestCandidateFitness=candidates.get(i).fitness;
				bestCandidatePos=i;
			}else
				if(bestCandidateFitness<candidates.get(i).fitness){	//TODO check this 
					bestCandidateFitness=candidates.get(i).fitness;
					bestCandidatePos=i;
				}
		}
		return candidates.get(bestCandidatePos).duplicate();
	}

}
