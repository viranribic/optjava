package hr.fer.zemris.optjava.dz5.part1.geneticOperators.selectionOperators;

import hr.fer.zemris.optjava.dz5.part1.geneticAlgorithms.population.RAPGAPopulation;
import hr.fer.zemris.optjava.dz5.part1.interfaces.ISelection;
import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

/**
 * Random selection class.
 * @author Viran
 *
 */
public class RandomSelection implements ISelection {

	private RAPGAPopulation population;

	@Override
	public void setPopulation(RAPGAPopulation population) {
		this.population=population;
	}

	@Override
	public BitvectorSolution select() {
		return population.getRandomSubject();
	}

}
