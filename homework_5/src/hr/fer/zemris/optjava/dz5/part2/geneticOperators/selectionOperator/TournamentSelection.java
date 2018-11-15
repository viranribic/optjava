package hr.fer.zemris.optjava.dz5.part2.geneticOperators.selectionOperator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part2.interfaces.ISelection;
import hr.fer.zemris.optjava.dz5.part2.solution.QAPSolution;

/**
 * Tournament selection class.
 * @author Viran
 *
 */
public class TournamentSelection implements ISelection{
	private int tournamentSize;
	private LinkedList<QAPSolution> population;
	private static Random rand=new Random(System.currentTimeMillis());
	/**
	 * TournamentSelection constructor.
	 * @param n Tournament size.
	 */
	public TournamentSelection(int n) {
		tournamentSize=n;
	}

	@Override
	public void setPopulation(Set<QAPSolution> population) {
		this.population=new LinkedList<QAPSolution>(population);
	}

	@Override
	public QAPSolution select() {
		QAPSolution[] candidates=new QAPSolution[tournamentSize];
		double bestCandidateFitness=0;
		
		int bestCandidatePos=0;
		int elementBound=population.size();
		Set<Integer> selectedPositions=new HashSet<>();
		
		for(int i=0;i<tournamentSize;i++){
			int nextRandPos=rand.nextInt(elementBound);
			if(selectedPositions.contains(nextRandPos))
				while(selectedPositions.contains(nextRandPos))
					nextRandPos=rand.nextInt(elementBound);
			
			candidates[i]=population.get(nextRandPos);
			selectedPositions.add(nextRandPos);
			
			if(i==0){
				bestCandidateFitness=candidates[i].getFitness();
				bestCandidatePos=i;
			}else
				if(bestCandidateFitness>candidates[i].getFitness()){ //we want to minimise func
					bestCandidateFitness=candidates[i].getFitness();
					bestCandidatePos=i;
				}
		}
		return candidates[bestCandidatePos].duplicate();
	}

}
