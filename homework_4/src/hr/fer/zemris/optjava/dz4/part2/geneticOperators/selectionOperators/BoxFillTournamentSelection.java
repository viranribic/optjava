package hr.fer.zemris.optjava.dz4.part2.geneticOperators.selectionOperators;

import hr.fer.zemris.optjava.dz4.part2.geneticAlgorithms.population.BoxFillPopulation;
import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution;

/**
 * BoxFill Tournament Selection class.
 * @author Viran
 *
 */
public class BoxFillTournamentSelection {
	private int tournamentSize;
	private BoxFillPopulation population;
	private boolean maximalFitness;

	/**
	 * BoxFillTournamentSelection constructor.
	 * @param n Tournament size.
	 * @param maximalFitness Flag for deciding if the tournament want to find the maximal value (true) or minimal value (false).  
	 */
	public BoxFillTournamentSelection(int n, boolean maximalFitness) {
		tournamentSize = n;
		this.maximalFitness = maximalFitness;
	}

	/**
	 * Set population on which the tournament takes place.
	 * @param population
	 */
	public void setPopulation(BoxFillPopulation population) {
		this.population = population;
	}

	/**
	 * Selects the best of of n subjects defined in the constructor. If the flag
	 * best is true, selection finds a subject with the greatest value,
	 * otherwise it's the one with the lowest value.
	 * 
	 * @param maximalFitness
	 *            Greatest or lowest value finder flag.
	 * @return Tournament champion subject.
	 */
	public BoxFillSolution selectRandomSubject() {
		BoxFillSolution[] candidates = new BoxFillSolution[tournamentSize];
		double bestCandidateFitness = 0;
		int bestCandidatePos = 0;
		for (int i = 0; i < tournamentSize; i++) {
			candidates[i] = population.getRandomSubject();
			if (i == 0) {
				bestCandidateFitness = candidates[i].fitness;
				bestCandidatePos = i;
			} else {
				if (maximalFitness) {
					// find greatest fitness
					if (bestCandidateFitness < candidates[i].fitness) {
						bestCandidateFitness = candidates[i].fitness;
						bestCandidatePos = i;
					}
				} else {
					// find lowest fitness
					if (bestCandidateFitness > candidates[i].fitness) {
						bestCandidateFitness = candidates[i].fitness;
						bestCandidatePos = i;
					}
				}

			}

		}
		return candidates[bestCandidatePos].duplicate();
	}
}
