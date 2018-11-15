package hr.fer.zemris.optjava.dz13.util;

import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;

public class InitGPPopulation {
	
	public static GPPopulation genInitialPopulation(int populationSize, int maxDepth, int maxNodeCount) {
		int treeDepths = maxDepth - 2 + 1; // minimal depth is 2
		double depthPercentage = 1. / treeDepths; // every population evenly and
													// for
		// each get the equal number of
		// full/grow
		int solsPerDepth = (int) (populationSize * depthPercentage/2);

		GPPopulation population = new GPPopulation();

		//foe every depth generate half full and half grow trees
		for (int depths = 2; depths <= maxDepth; depths++) {
			generetPart(population, solsPerDepth, populationSize, depths, true,maxNodeCount);
			generetPart(population, solsPerDepth, populationSize, depths, false,maxNodeCount);
		}
		//add if missing
		while(population.size()!=populationSize){
			GPSolution solution = new GPSolution();
			SolNode root = SolTreeGenerator.genRandTree(maxDepth, true,maxNodeCount);
			solution.setRoot(root);
			solution.setNodesInTree(root.nodesBeneathCount+1);
			population.add(solution);
		}
		return population;
	}
	
	private static void generetPart(GPPopulation population, int solsPerDepth, int populationSize, int depth, boolean isFull, int maxNodeCount){
		for (int solutions = 1; solutions <= solsPerDepth; solutions++) {
			if (population.size() == populationSize) // mechanism to
														// exit after
														// reaching
														// popSize
														// subjects
				break;
			GPSolution solution = new GPSolution();
			SolNode root = SolTreeGenerator.genRandTree(depth,isFull,maxNodeCount);
			solution.setRoot(root);
			solution.setNodesInTree(root.nodesBeneathCount+1);
			population.add(solution);

		}
	}
}
