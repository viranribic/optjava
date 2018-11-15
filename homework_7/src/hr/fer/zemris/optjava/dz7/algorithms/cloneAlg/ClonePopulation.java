package hr.fer.zemris.optjava.dz7.algorithms.cloneAlg;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.solution.DoubleArraySolution;

/**
 * Clone population class for containing antibodies.
 * 
 * @author Viran
 *
 */
public class ClonePopulation implements Iterable<DoubleArraySolution> {

	private LinkedList<DoubleArraySolution> antibodies = new LinkedList<>();
	private Random rand = new Random(System.currentTimeMillis());

	/**
	 * ClonePopulation constructor.
	 * 
	 * @param popSize
	 *            Population size.
	 * @param solutionDim
	 *            Size of the solution values vector.
	 */
	public ClonePopulation(int popSize, int solutionDim) {
		for (int i = 0; i < popSize; i++) {
			antibodies.add(new DoubleArraySolution(solutionDim));
		}
	}

	/**
	 * ClonePopulation constructor.
	 * 
	 * @param selectedPop
	 *            Existing solution array.
	 */
	// @SuppressWarnings("unchecked")
	public ClonePopulation(LinkedList<DoubleArraySolution> selectedPop) {
		// this.population=(LinkedList<DoubleArraySolution>)
		// selectedPop.clone();
		this.antibodies = selectedPop;
	}

	/**
	 * Get this population size
	 * 
	 * @return Population size.
	 */
	public int getPopulationSize() {
		return antibodies.size();
	}

	/**
	 * Initialise population.
	 * 
	 * @param minInitVals
	 *            Minimal element value.
	 * @param maxInitVals
	 *            Maximal element value.
	 */
	public void initPopulation(double[] minInitVals, double[] maxInitVals) {
		for (DoubleArraySolution s : antibodies)
			s.randomize(rand, minInitVals, maxInitVals);
	}

	@Override
	public Iterator<DoubleArraySolution> iterator() {
		return new Iterator<DoubleArraySolution>() {
			private int pos = 0;

			@Override
			public boolean hasNext() {
				return pos < antibodies.size();
			}

			@Override
			public DoubleArraySolution next() {
				return antibodies.get(pos++);
			}

		};
	}

	/**
	 * Sort this population by fitness in ascending order.
	 */
	public void sortFitAsc() {
		Collections.sort(antibodies);
	}

	/**
	 * Sort this population by fitness in descending order.
	 */
	public void sortFitDesc() {
		sortFitAsc();
		Collections.reverse(antibodies);
	}

	/**
	 * Get antibody at index.
	 * 
	 * @param atIndex
	 *            Index of the antibody requested.
	 * @return Antibody.
	 */
	public DoubleArraySolution getAntibody(int atIndex) {
		return antibodies.get(atIndex);
	}

}
