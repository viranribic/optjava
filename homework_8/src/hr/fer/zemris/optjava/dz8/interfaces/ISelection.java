package hr.fer.zemris.optjava.dz8.interfaces;

import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;

public interface ISelection {

	public DifEvSolution select(DifEvSolution targetVector, DifEvSolution trialVector);
}
