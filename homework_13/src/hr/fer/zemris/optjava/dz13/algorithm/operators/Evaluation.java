package hr.fer.zemris.optjava.dz13.algorithm.operators;

import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;
import hr.fer.zemris.optjava.dz13.problemComponents.World;

public class Evaluation {

	private World world;
	
	public Evaluation(String inputFile) {
		this.world=new World(inputFile);
	}
	
	public int evaluation(GPSolution solution){
		resetData();
		SolNode root=solution.getRoot();
		while(!world.foundAllPieces() && world.hasMoreIterations()){
			root.evaluate(world);
		}
		int collected=world.getFoodCollected();
		solution.setFoodCollected(collected);
		solution.setFitness(collected);
		return collected;
	}

	private void resetData() {
		world.resetAgent();
		world.resetFoodCollected();
		world.resetWorld();
		world.resetStepsTaken();
	}
	
}
