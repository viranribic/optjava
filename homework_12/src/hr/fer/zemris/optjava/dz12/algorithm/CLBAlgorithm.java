package hr.fer.zemris.optjava.dz12.algorithm;

import java.util.Queue;

import hr.fer.zemris.optjava.dz12.CLBSolver.IntMonitor;
import hr.fer.zemris.optjava.dz12.algorithm.crossover.CLBCrossover;
import hr.fer.zemris.optjava.dz12.algorithm.errorFunction.CLBEvaluator;
import hr.fer.zemris.optjava.dz12.algorithm.mutation.CLBMutation;
import hr.fer.zemris.optjava.dz12.algorithm.population.CLBSolPopulation;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolutionPack;
import hr.fer.zemris.optjava.dz12.algorithm.selection.CLBSelection;
import hr.fer.zemris.optjava.dz12.interfaces.ICrossover;
import hr.fer.zemris.optjava.dz12.interfaces.IMutation;
import hr.fer.zemris.optjava.dz12.interfaces.IOptAlgorithm;
import hr.fer.zemris.optjava.dz12.interfaces.ISelection;
import hr.fer.zemris.optjava.dz12.util.AlgConstants;
import hr.fer.zemris.optjava.dz12.util.threads.EVOThread;

public class CLBAlgorithm implements IOptAlgorithm {

	// algorithm
	private CLBSolPopulation population;
	private ISelection selection;
	private ICrossover crossover;
	private IMutation mutation;
	private CLBEvaluator evaluator;

	// thread communication
	private Queue<CLBSolutionPack> fromQueue;
	private Queue<CLBSolutionPack> toQueue;
	private CLBSolution gBest;
	private IntMonitor gBestFound;

	public CLBAlgorithm(int clbInputNum, int clbMaxNum, String logicFunction) {

		selection = new CLBSelection(AlgConstants.TOUR_SIZE);
		crossover = new CLBCrossover();
		mutation = new CLBMutation();
		evaluator = new CLBEvaluator(logicFunction);
		population = new CLBSolPopulation(AlgConstants.POP_SIZE, clbInputNum, clbMaxNum, evaluator.numberOfVariables());
		for (CLBSolution sol : population)
			evaluator.evaluate(sol);

	}

	@Override
	public void run() {

		if (Thread.currentThread() instanceof EVOThread) {
			fromQueue = ((EVOThread) Thread.currentThread()).getFromQueue();
			toQueue = ((EVOThread) Thread.currentThread()).getToQueue();
			gBest = ((EVOThread) Thread.currentThread()).getGBest();
			gBestFound = ((EVOThread) Thread.currentThread()).getGBestFound();

		}

		int iter = 0;
		while (iter++ < AlgConstants.MAX_ITER) {
			CLBSolPopulation nextPopulation = new CLBSolPopulation();

			// check for from queue insertions
			checkFromQueue();

			while (nextPopulation.size() != population.size()) {

				CLBSolution parentA = selection.select(population);
				CLBSolution parentB = selection.select(population);
				CLBSolution child = crossover.crossover(parentA, parentB);
				child = mutation.mutation(child);
				child = evaluator.evaluate(child);

				nextPopulation.add(child);
			}
			// insert back to pop
			population = generateNextPop(population, nextPopulation);

			// check for to queue insertions
			prepareToQueue(iter);

			System.out.println(String.format("%s | %3d | E: %.0f", Thread.currentThread().getName(), iter, population.get(0).getError()));
			
			// look if done for this algorithm run
			if (endBest())
				return;
		}

		end();
	}


	private void prepareToQueue(int iter) {
		if (iter % AlgConstants.TIME_OUT_ITER == 0) {
			CLBSolutionPack send = population.preparePack(AlgConstants.PACK_SIZE);
			synchronized (toQueue) {
				toQueue.add(send);
			}
		}
	}

	private void checkFromQueue() {
		synchronized (fromQueue) {
			CLBSolutionPack receive=fromQueue.poll();
			while(receive!=null){
				//get packet and explore the content
				for(CLBSolution solution:receive){
					for(int i=0;i<population.size();i++){
						//try to insert it sorted
						if(solution.getError()<population.get(i).getError()){
							population.remove(i);
							population.add(i,solution);
							break;
						}
						//else add to end
						population.remove(population.size()-1);
						population.add(solution);
					}
				}
				receive=fromQueue.poll();
			}
		}

	}


	private void end() {
		synchronized (gBest) {

			if (gBest.getError() == 0) {
				synchronized (gBestFound) {
					gBestFound.inc();
					gBestFound.notify();
				}
				return; // finish
			} else {
				CLBSolution personalBest = population.get(0);
				if (personalBest.getError() < gBest.getError()) 
					gBest.copy(personalBest);
				synchronized (gBestFound) {
					gBestFound.inc();
					gBestFound.notify();
				}
				return; // finish for this run
				
			}
		}
		
	}
	
	private boolean endBest() {
		synchronized (gBest) {

			if (gBest.getError() == 0) {
				synchronized (gBestFound) {
					gBestFound.inc();
					gBestFound.notify();
				}
				return true; // finish
			} else {
				CLBSolution personalBest = population.get(0);
				if (personalBest.getError() == 0) {
					gBest.copy(personalBest);
					synchronized (gBestFound) {
						gBestFound.inc();
						gBestFound.notify();
					}
					return true; // finish for this run
				}
			}
		}
		return false;
	}

	private CLBSolPopulation generateNextPop(CLBSolPopulation population, CLBSolPopulation nextPopulation) {
		CLBSolPopulation genPop = new CLBSolPopulation();
		population.sort();
		nextPopulation.sort();
		for (int i = 0; i < population.size(); i++) {
			if (i < AlgConstants.ELITE_SOLS) {
				if (population.get(i).compareTo(nextPopulation.get(i)) < 0) {
					genPop.add(population.get(i));
				} else {
					genPop.add(nextPopulation.get(i));
				}
			} else {
				genPop.add(nextPopulation.get(i));
			}
		}
		return genPop;
	}

}
