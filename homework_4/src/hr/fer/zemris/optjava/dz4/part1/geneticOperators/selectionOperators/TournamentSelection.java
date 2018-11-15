package hr.fer.zemris.optjava.dz4.part1.geneticOperators.selectionOperators;

import hr.fer.zemris.optjava.dz4.part1.geneticAlgorithms.population.ElitePopulationGA;
import hr.fer.zemris.optjava.dz4.part1.interfaces.ISelection;
import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

/**
 * Tournament selection class.
 * @author Viran
 *
 */
public class TournamentSelection implements ISelection{
	private int tournamentSize;
	private ElitePopulationGA population;
	/**
	 * TournamentSelection constructor.
	 * @param n Tournament size.
	 */
	public TournamentSelection(int n) {
		tournamentSize=n;
	}

	@Override
	public void setPopulation(ElitePopulationGA population) {
		this.population=population;
	}

	@Override
	public DoubleArraySolution selectRandomSubject() {
		DoubleArraySolution[] candidates=new DoubleArraySolution[tournamentSize];
		double bestCandidateFitness=0;
		int bestCandidatePos=0;
		for(int i=0;i<tournamentSize;i++){
			candidates[i]=population.getRandomSubject();
			if(i==0){
				bestCandidateFitness=candidates[i].fitness;
				bestCandidatePos=i;
			}else
				if(bestCandidateFitness>candidates[i].fitness){
					bestCandidateFitness=candidates[i].fitness;
					bestCandidatePos=i;
				}
		}
		return candidates[bestCandidatePos].duplicate();
	}

}
