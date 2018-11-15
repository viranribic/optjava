package hr.fer.zemris.optjava.dz9.algorithm;

import java.util.Random;

import hr.fer.zemris.optjava.dz9.interfaces.ISelection;

/**
 * Roulette wheel selection class.
 * 
 * @author Viran
 *
 */
public class RouletteWheelSelection implements ISelection {

	private Random rand = new Random();
	private NSGAPopulation population;
	private double[] subjectProbabilities;
	private double[] scaledFitness;

	@Override
	public void setPopulation(NSGAPopulation population) {
		this.population = population;
		resolveScaleProblem();
		calculateProbabilities();
	}

	@Override
	public NSGASolution selectRandomSubject() {
		double x = rand.nextDouble();
		double lowerBound = 0;
		double upperBound = 0;

		int size = population.getPopulationSize();
		for (int i = 0; i < size; i++) {
			upperBound += subjectProbabilities[i];
			if (lowerBound < x && x < upperBound) {
				return population.getSubjectAtIndex(i).duplicate();
			} else {
				lowerBound = upperBound;
			}
		}
		return null;
	}

	/**
	 * Calculate the probabilities of a particular subject being selected.
	 */
	private void calculateProbabilities() {
		int size = population.getPopulationSize();
		subjectProbabilities = new double[size];
		double totalFitness = 0;
		for (int i = 0; i < size - 1; i++) {
			totalFitness += scaledFitness[i];
		}
		for (int i = 0; i < size; i++) {
			subjectProbabilities[i] = scaledFitness[i] / totalFitness;
		}
	}

	/**
	 * Resolves the scaling problem, decreasing each fitness with the minimal
	 * population fitness.
	 */
	private void resolveScaleProblem() {
		population.sortThisPopulation();
		int size = population.getPopulationSize();
		NSGASolution worstSubject = population.getSubjectAtIndex(size - 1);

		double fWorst = worstSubject.getFit();

		scaledFitness = new double[size];

		// rescale for the whole population
		for (int i = 0; i < size; i++) {
			NSGASolution subj = population.getSubjectAtIndex(i);
			if (subj.getFit() > 1)
				scaledFitness[i] = 1 / subj.getFit();
			else
				scaledFitness[i] = Math.abs(fWorst - subj.getFit());
		}
	}
}
