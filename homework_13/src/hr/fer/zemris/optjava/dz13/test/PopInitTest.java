package hr.fer.zemris.optjava.dz13.test;

import hr.fer.zemris.optjava.dz13.util.AlgConst;
import hr.fer.zemris.optjava.dz13.util.GPPopulation;
import hr.fer.zemris.optjava.dz13.util.InitGPPopulation;

public class PopInitTest {

	public static void main(String[] args) {
		GPPopulation population = InitGPPopulation.genInitialPopulation(4, AlgConst.INIT_MAX_DEPTH, AlgConst.MAX_NODE_COUNT);
		System.out.println(population);
	}
}
