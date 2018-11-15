package hr.fer.zemris.optjava.dz10.algorithm.selection;


import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Population;
import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Solution;
import hr.fer.zemris.optjava.dz10.interfaces.ISelection;

/**
 * Crowded tournament selection operator.
 * @author Viran
 *
 */
public class CrowdedTournamentSelection  implements ISelection {

	private NSGA2Population population;

	@Override
	public void setPopulation(NSGA2Population population) {
		this.population=population;

	}

	@Override
	public NSGA2Solution selectRandomSubject() {

		
		while(true){

			NSGA2Solution solution1=population.getRandomSubject();
			NSGA2Solution solution2=population.getRandomSubject();
			if(solution1.equals(solution2))
				continue;
			
			if(solution1.getNonDominationRank()<solution2.getNonDominationRank()){
				return solution1;
			}else if(solution1.getNonDominationRank()==solution2.getNonDominationRank() 
					&& solution1.getCrowdingDistance()>solution2.getCrowdingDistance()){
				return solution1;	
			} else if(solution2.getNonDominationRank()<solution1.getNonDominationRank()){
				return solution2;
			}else if(solution2.getNonDominationRank()==solution1.getNonDominationRank() 
					&& solution2.getCrowdingDistance()>solution1.getCrowdingDistance()){
				return solution2;
			}else{
				continue;
			}
		}
	}

}
