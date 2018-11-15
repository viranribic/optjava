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
import hr.fer.zemris.optjava.rng.EVOThread2;
import hr.fer.zemris.optjava.rng.RNG;

public class MulThreadGA2 implements IOptAlgorithm<GASolution<int[]>> {

	private int populationSize;
	private double minError;
	private int maxIterations;
	private IGAEvaluator<int[]> function;
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
	private Queue<Integer> taskQueue = new ConcurrentLinkedQueue<>();
	private Queue<GASolution<int[]>> childQueue = new ConcurrentLinkedQueue<>();

	// Thread
	private EVOThread2[] workers;
	private Runnable evaluationTask = new Runnable() {

		@Override
		public void run() {
			while (true) {
				Integer numberOfTasks = null;
				Queue<Integer> taskQueue = ((EVOThread2) Thread.currentThread()).getTaskQueue();
				Queue<GASolution<int[]>> childQueue = ((EVOThread2) Thread.currentThread()).getChildQueue();
				
				GrayScaleImage img=null;
				try {
					img = GrayScaleImage.load(new File(pngSrcPath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				IGAEvaluator<int[]> function=new Evaluator(img);
				
				// System.out.println("Thread " + Thread.currentThread() + " has
				// started.");
				// System.out.println("EvalNeededQueue:" + evalNeededQueue);
				// System.out.println("EvalSetQueue:" + evalSetQueue);

				synchronized (taskQueue) {
					while (numberOfTasks == null) {
						numberOfTasks = taskQueue.poll();
						if (numberOfTasks == null)
							try {
								// System.out.println("Thread " +
								// Thread.currentThread() + " will wait.");
								taskQueue.wait();
								// System.out.println("Thread " +
								// Thread.currentThread() + " will continue.");
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					}
				}

				if (numberOfTasks == -1) {
					// System.out.println("Thread " + Thread.currentThread() + "
					// will die.");
					break;
				}

				// System.out.println(Thread.currentThread() +" + one more
				// done.");
				LinkedList<GASolution<int[]>> solutions = proccessTasks(numberOfTasks,function);

				synchronized (childQueue) {
					for (GASolution<int[]> sol : solutions) {

						if (childQueue.isEmpty()) {
							// System.out.println("Thread " +
							// Thread.currentThread() + " will add and
							// notify.");

							childQueue.add(sol);
							childQueue.notifyAll();
						} else {
							// System.out.println("Thread " +
							// Thread.currentThread() + " will only add.");

							childQueue.add(sol);
						}
					}
				}
			}
		}

		private LinkedList<GASolution<int[]>> proccessTasks(Integer numberOfTasks, IGAEvaluator<int[]> function) {

			ISelectionOp selectionOp = new TournamentSelection(ConstantsTask.TOURNAMENT_SIZE);;
			ICrossoverOp crossoverOp = new OnePointCrossover(RNG.getRNG());;
			IMutationOp mutationOp = new Mutation(RNG.getRNG());
			
			GASolution<int[]> parentA;
			GASolution<int[]> parentB;
			GASolution<int[]> child;

			selectionOp.setPopulation(population);
			
			LinkedList<GASolution<int[]>> solutions=new LinkedList<>();
			
			for(int i=0;i<numberOfTasks;i++){

				// genetic operators
				parentA = selectionOp.selectRandomSubject(RNG.getRNG());
				parentB = selectionOp.selectRandomSubject(RNG.getRNG());
				child = crossoverOp.crossover(parentA, parentB);
				child = mutationOp.mutation(child);

				function.evaluate(child);

				solutions.add(child);
			}			
			return solutions;
		}
	};

	/**
	 * GenerationElitisticGA constructor.
	 * 
	 * @param populationSize
	 *            Size of population.
	 * @param minError
	 *            Minimal error at which the algorithm can stop.
	 * @param maxIterations
	 *            Maximal number of iterations.
	 * @param selection
	 *            Selection operator.
	 * @param sigma
	 *            Mutation parameter.
	 * @param function
	 *            Function we optimise.
	 * @param pngSrcPath 
	 * @param domainDim
	 *            Dimension of the domain we are solving.
	 */
	public MulThreadGA2(int populationSize, double minError, int maxIterations, IGAEvaluator<int[]> function,
			String pngSrcPath, int squareNumber) {
		this.populationSize = populationSize;
		this.minError = minError;
		this.maxIterations = maxIterations;
		this.selectionOp = new TournamentSelection(ConstantsTask.TOURNAMENT_SIZE);
		this.squareNumber = squareNumber;
		this.pngSrcPath=pngSrcPath;
		this.function = function;
		this.crossoverOp = new OnePointCrossover(RNG.getRNG());
		this.mutationOp = new Mutation(RNG.getRNG());
	}

	@Override
	public GASolution<int[]> run() {

		// Threads set up
		int procNum = Runtime.getRuntime().availableProcessors();
		workers = new EVOThread2[procNum];
		for (int i = 0; i < workers.length; i++) {
			workers[i] = new EVOThread2(evaluationTask, taskQueue, childQueue);
			workers[i].start();
		}
		// -----------------------

		initPopulation(function.getWidth(), function.getHeight());

		int iteration = maxIterations;
		LinkedList<GASolution<int[]>> nextPopulation;
		int nextPopulationSize;


		while (iteration-- > 0) {
			nextPopulation = new LinkedList<GASolution<int[]>>();
			nextPopulationSize = 0;
			
			// loop for creating
			while (nextPopulationSize < populationSize) {
				int nextTask=(populationSize-nextPopulationSize)>ConstantsTask.TASK_REQUEST_NUM ?ConstantsTask.TASK_REQUEST_NUM:populationSize-nextPopulationSize;
				
				//adding to queue
				synchronized (taskQueue) {

					if (taskQueue.isEmpty()) {
						taskQueue.add(nextTask);
						taskQueue.notifyAll();
					} else {
						taskQueue.add(nextTask);
					}

				}
				nextPopulationSize+=nextTask;
			}

			
			//retrieve evaluated
			nextPopulationSize = 0;
			while (nextPopulationSize < populationSize) {
				GASolution<int[]> evalChild=null;
				synchronized (childQueue) {
					
					while (evalChild == null) {
						evalChild = childQueue.poll();
						if(evalChild==null){
							try {
								childQueue.wait();
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
		synchronized (taskQueue) {
			for (int i = 0; i < workers.length; i++) {

				taskQueue.add(-1);
				taskQueue.notify();		
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

			function.evaluate(solution);

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
