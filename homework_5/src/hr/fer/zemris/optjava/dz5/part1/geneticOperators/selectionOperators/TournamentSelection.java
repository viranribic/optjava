package hr.fer.zemris.optjava.dz5.part1.geneticOperators.selectionOperators;

import hr.fer.zemris.optjava.dz5.part1.geneticAlgorithms.population.RAPGAPopulation;
import hr.fer.zemris.optjava.dz5.part1.interfaces.ISelection;
import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

/**
 * Tournament selection class.
 * @author Viran
 *
 */
public class TournamentSelection implements ISelection{
	private int tournamentSize;
	private RAPGAPopulation population;
	/**
	 * TournamentSelection constructor.
	 * @param n Tournament size.
	 */
	public TournamentSelection(int n) {
		tournamentSize=n;
	}

	@Override
	public void setPopulation(RAPGAPopulation population) {
		this.population=population;
	}

	@Override
	public BitvectorSolution select() {
		BitvectorSolution[] candidates=new BitvectorSolution[tournamentSize];
		double bestCandidateFitness=0;
		int bestCandidatePos=0;
		for(int i=0;i<tournamentSize;i++){
			candidates[i]=population.getRandomSubject();
			if(i==0){
				bestCandidateFitness=candidates[i].getFitness();
				bestCandidatePos=i;
			}else
				if(bestCandidateFitness<candidates[i].getFitness()){
					bestCandidateFitness=candidates[i].getFitness();
					bestCandidatePos=i;
				}
		}
		return candidates[bestCandidatePos].duplicate();
	}

}
