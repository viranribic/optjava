package hr.fer.zemris.optjava.dz12.util.threads;

import java.util.Queue;

import hr.fer.zemris.optjava.dz12.CLBSolver.IntMonitor;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolutionPack;
import hr.fer.zemris.optjava.dz12.interfaces.IRNG;
import hr.fer.zemris.optjava.dz12.interfaces.IRNGProvider;
import hr.fer.zemris.optjava.dz12.util.random.RNGRandomImpl;


public class EVOThread extends Thread implements IRNGProvider {

	private IRNG rng = new RNGRandomImpl();
	private Queue<CLBSolutionPack> fromQueue;
	private Queue<CLBSolutionPack> toQueue;
	private CLBSolution gBest;
	private IntMonitor gBestFound;

	public EVOThread(Runnable target,Queue<CLBSolutionPack> fromQueue,Queue<CLBSolutionPack> toQueue, IntMonitor gBestFound, CLBSolution gBest) {
		super(target);
		this.fromQueue=fromQueue;
		this.toQueue=toQueue;
		this.setgBest(gBest);
		this.setgBestFound(gBestFound);
	}

	@Override
	public IRNG getRNG() {
		return rng;
	}

	public Queue<CLBSolutionPack> getFromQueue(){
		return fromQueue;
	}
	
	public Queue<CLBSolutionPack> getToQueue(){
		return toQueue;
	}

	public CLBSolution getGBest() {
		return gBest;
	}

	public void setgBest(CLBSolution gBest) {
		this.gBest = gBest;
	}

	public IntMonitor getGBestFound() {
		return gBestFound;
	}

	public void setgBestFound(IntMonitor gBestFound) {
		this.gBestFound = gBestFound;
	}

	
}
