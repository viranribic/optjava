package hr.fer.zemris.optjava.dz11.geneticAlgorithm.population;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.optjava.dz11.constants.ConstantsTask;
import hr.fer.zemris.optjava.dz11.geneticAlgorithm.solution.GAImgSolution;
import hr.fer.zemris.optjava.dz11.geneticOperators.selectionOperators.TournamentSelection;
import hr.fer.zemris.optjava.dz11.geneticOperatos.crossoverOperators.OnePointCrossover;
import hr.fer.zemris.optjava.dz11.geneticOperatos.mutationOperators.Mutation;
import hr.fer.zemris.optjava.dz11.interfaces.ICrossoverOp;
import hr.fer.zemris.optjava.dz11.interfaces.IMutationOp;
import hr.fer.zemris.optjava.dz11.interfaces.IOptAlgorithm;
import hr.fer.zemris.optjava.dz11.interfaces.ISelectionOp;
import hr.fer.zemris.optjava.rng.EVOThread1;
import hr.fer.zemris.optjava.rng.RNG;



public class MulThreadGA1 implements IOptAlgorithm<GASolution<int[]>> {

	
	private int populationSize;
	private double minError;
	private int maxIterations;
	private String pngSrcPath;

	// Population attributes
	private GASolution<int[]> globalBest = null;
	private LinkedList<GASolution<int[]>> population;
	private int squareNumber;

	// Genetic operators
	private ISelectionOp selectionOp;
	private ICrossoverOp crossoverOp;
	private IMutationOp mutationOp;

	// Queues
	private Queue<GASolution<int[]>> evalNeededQueue = new ConcurrentLinkedQueue<>();
	private Queue<GASolution<int[]>> evalSetQueue = new ConcurrentLinkedQueue<>();

	// Thread
	private EVOThread1[] workers;
	private Runnable evaluationTask = new Runnable() {

		@Override
		public void run() {
			while (true) {
				GASolution<int[]> sol = null;
				Queue<GASolution<int[]>> evalNeededQueue = ((EVOThread1) Thread.currentThread()).getEvalNeededQueue();
				Queue<GASolution<int[]>> evalSetQueue = ((EVOThread1) Thread.currentThread()).getEvalSetQueue();
				
				GrayScaleImage img=null;
				try {
					img = GrayScaleImage.load(new File(pngSrcPath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				IGAEvaluator<int[]> function=new Evaluator(img);
				
				//System.out.println("Thread " + Thread.currentThread() + " has started.");
				//System.out.println("EvalNeededQueue:" + evalNeededQueue);
				//System.out.println("EvalSetQueue:" + evalSetQueue);

				synchronized (evalNeededQueue) {
					while (sol == null) {
						sol = evalNeededQueue.poll();
						if (sol == null)
							try {
								//System.out.println("Thread " + Thread.currentThread() + " will wait.");
								evalNeededQueue.wait();
								//System.out.println("Thread " + Thread.currentThread() + " will continue.");
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					}
				}

				if (sol.poisounus) {
					//System.out.println("Thread " + Thread.currentThread() + " will die.");
					break;
				}

				//System.out.println(Thread.currentThread() +" + one more done.");
				function.evaluate(sol);

				synchronized (evalSetQueue) {

					if (evalSetQueue.isEmpty()) {
						//System.out.println("Thread " + Thread.currentThread() + " will add and notify.");

						evalSetQueue.add(sol);
						evalSetQueue.notifyAll();
					} else {
						//System.out.println("Thread " + Thread.currentThread() + " will only add.");

						evalSetQueue.add(sol);
					}
				}
			}
		}
	};
	private IGAEvaluator<int[]> function;

	/**
	 * GenerationElitisticGA constructor.
	 * 
	 * @param populationSize
	 *            Size of population.
	 * @param minError
	 *            Minimal error at which the algorithm can stop.
	 * @param maxIterations
	 *            Maximal number of iterations.
	 * @param function 
	 * @param selection
	 *            Selection operator.
	 * @param sigma
	 *            Mutation parameter.
	 * @param pngSrcPath
	 *            Function we optimise.
	 * @param domainDim
	 *            Dimension of the domain we are solving.
	 */
	public MulThreadGA1(int populationSize, double minError, int maxIterations, IGAEvaluator<int[]> function, String pngSrcPath,
			int squareNumber) {
		this.populationSize = populationSize;
		this.minError = minError;
		this.maxIterations = maxIterations;
		this.selectionOp = new TournamentSelection(ConstantsTask.TOURNAMENT_SIZE);
		this.squareNumber = squareNumber;
		this.pngSrcPath = pngSrcPath;
		this.function=function;
		this.crossoverOp = new OnePointCrossover(RNG.getRNG());
		this.mutationOp = new Mutation(RNG.getRNG());
	}

	@Override
	public GASolution<int[]> run() {

		// Threads set up
		int procNum = Runtime.getRuntime().availableProcessors();
		workers = new EVOThread1[procNum];
		for (int i = 0; i < workers.length; i++) {
			workers[i] = new EVOThread1(evaluationTask, evalNeededQueue, evalSetQueue);
			workers[i].start();
		}
		// -----------------------

		initPopulation(function.getWidth(), function.getHeight());

		int iteration = maxIterations;
		LinkedList<GASolution<int[]>> nextPopulation;
		int nextPopulationSize;

		GASolution<int[]> parentA;
		GASolution<int[]> parentB;
		GASolution<int[]> child;

		while (iteration-- > 0) {
			nextPopulation = new LinkedList<GASolution<int[]>>();
			nextPopulationSize = 0;
			selectionOp.setPopulation(population);

			// loop for creating
			while (nextPopulationSize < populationSize) {
				
				//genetic operators
				parentA = selectionOp.selectRandomSubject(RNG.getRNG());
				parentB = selectionOp.selectRandomSubject(RNG.getRNG());
				child = crossoverOp.crossover(parentA, parentB);
				child = mutationOp.mutation(child);
				
				//adding to queue
				synchronized (evalNeededQueue) {

					if (evalNeededQueue.isEmpty()) {
						evalNeededQueue.add(child);
						evalNeededQueue.notifyAll();
					} else {
						evalNeededQueue.add(child);
					}

				}
				nextPopulationSize++;
			}

			
			//retrieve evaluated
			nextPopulationSize = 0;
			while (nextPopulationSize < populationSize) {
				GASolution<int[]> evalChild=null;
				synchronized (evalSetQueue) {
					
					while (evalChild == null) {
						evalChild = evalSetQueue.poll();
						if(evalChild==null){
							try {
								evalSetQueue.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}

				
				//TODO some form of global best save 
				if (globalBest == null) {
					globalBest = evalChild;
				} else if (Math.abs(globalBest.fitness) > Math.abs(evalChild.fitness)) {
					globalBest = evalChild;
				}else if(Math.abs(globalBest.fitness) < Math.abs(evalChild.fitness) && nextPopulationSize==population.size()-1){
					nextPopulation.add(globalBest);
					nextPopulationSize++;
					break;
				}

				nextPopulation.add(evalChild);
				nextPopulationSize++;
			}
			//
			population = nextPopulation;
			printSubject(globalBest);
			if (Math.abs(globalBest.fitness) <= minError) {
				break;
			}
		}

		//thread poisoning
		synchronized (evalNeededQueue) {
			for (int i = 0; i < workers.length; i++) {
				GAImgSolution deadlySol = new GAImgSolution(0, 0, 0);
				deadlySol.makePoisounus(true);
				evalNeededQueue.add(deadlySol);
				evalNeededQueue.notify();		
			}
		}
		
		//wait for all threads
		for (int i = 0; i < workers.length; i++) {
			try {
				workers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		//exit with global best
		return globalBest;
	}

	/**
	 * Print the given subject to standard output.
	 * 
	 * @param subjectAtIndex
	 *            Subject to be printed.
	 */
	private void printSubject(GASolution<int[]> subjectAtIndex) {
		StringBuffer sb = new StringBuffer();
		sb.append("Fitness: ");
		sb.append(String.format("( %6.3f )", subjectAtIndex.fitness));
		System.out.println(sb.toString());

	}

	private void initPopulation(int maxWidth, int maxHeight) {
		population = new LinkedList<>();
		// create and leave for evaluation
		for (int i = 0; i < populationSize; i++) {
			GAImgSolution solution = new GAImgSolution(squareNumber, maxWidth, maxHeight);
			solution.init();

			synchronized (evalNeededQueue) {

				if (evalNeededQueue.isEmpty()) {
					evalNeededQueue.add(solution);
					evalNeededQueue.notifyAll();
				} else {
					evalNeededQueue.add(solution);
				}

			}

		}

		// wait for them to come back
		for (int i = 0; i < populationSize; i++) {

			GASolution<int[]> solution = null;

			synchronized (evalSetQueue) {
				while (solution == null) {
					solution = evalSetQueue.poll();
					if (solution == null)
						try {
							evalSetQueue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}

			population.add(solution);
		}

		// get the best solution
		for (int i = 0; i < populationSize; i++) {
			GASolution<int[]> solution = population.get(i);
			if (globalBest == null) {
				globalBest = solution;
			} else if (Math.abs(globalBest.fitness) > Math.abs(solution.fitness)) {
				globalBest = solution;
			}
		}
	}

}
