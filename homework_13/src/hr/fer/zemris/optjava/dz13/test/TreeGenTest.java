package hr.fer.zemris.optjava.dz13.test;

import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;
import hr.fer.zemris.optjava.dz13.util.AlgConst;
import hr.fer.zemris.optjava.dz13.util.SolTreeGenerator;

public class TreeGenTest {

	public static void main(String[] args) {
		SolNode node=SolTreeGenerator.genRandTree(4,false,AlgConst.MAX_NODE_COUNT);
		System.out.println(node);
	}
	
}
