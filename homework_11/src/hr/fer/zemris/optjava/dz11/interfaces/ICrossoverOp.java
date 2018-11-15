package hr.fer.zemris.optjava.dz11.interfaces;

import hr.fer.zemris.generic.ga.GASolution;

public interface ICrossoverOp {

	GASolution<int[]> crossover(GASolution<int[]> parentA, GASolution<int[]> parentB);

}
