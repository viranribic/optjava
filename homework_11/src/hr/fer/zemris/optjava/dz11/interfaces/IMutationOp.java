package hr.fer.zemris.optjava.dz11.interfaces;

import hr.fer.zemris.generic.ga.GASolution;

public interface IMutationOp {

	GASolution<int[]> mutation(GASolution<int[]> child);

}
