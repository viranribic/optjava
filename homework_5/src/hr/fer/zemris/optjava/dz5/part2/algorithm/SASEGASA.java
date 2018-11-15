package hr.fer.zemris.optjava.dz5.part2.algorithm;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz5.part1.interfaces.IOptAlgorithm;
import hr.fer.zemris.optjava.dz5.part2.algorithm.additionalHeuristcs.OffspringSelection;
import hr.fer.zemris.optjava.dz5.part2.algorithm.population.SASEGASAPopulation;
import hr.fer.zemris.optjava.dz5.part2.constants.Constants;
import hr.fer.zemris.optjava.dz5.part2.function.QAPFunction;
import hr.fer.zemris.optjava.dz5.part2.solution.QAPSolution;

/**
 * The Self-Adaptive Segregative Genetic Algorithm with Simulated Annealing
 * aspects. Adjusted for solving Quadratic Assignment problem.
 * 
 * @author Viran
 *
 */
public class SASEGASA implements IOptAlgorithm {

	// General algorithm parameters
	private QAPFunction function;
	int totalGenerations;
	
	// Population parameters
	private int totalPopSize;
	private int initNumOfSubPop;
	private SASEGASAPopulation population;

	/**
	 * SASEGASA constructor.
	 * 
	 * @param function
	 *            Fitness funcition we want
	 * @param totalPopSize
	 * @param initNumOfSubPop
	 */
	public SASEGASA(QAPFunction function, int totalPopSize, int initNumOfSubPop) {
		this.function = function;
		this.totalPopSize = totalPopSize;
		this.initNumOfSubPop = initNumOfSubPop;

	}

	@Override
	public void run() {

		int numberOfVillages = (int) totalPopSize / initNumOfSubPop;
		int maxGenerations = Constants.MAX_GENERATIONS;
		
		try {
			population = new SASEGASAPopulation(totalPopSize, numberOfVillages, function);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		totalGenerations = 0;
		int currGenerations = 0;
		OffspringSelection OSGeneticAlgorithm=new OffspringSelection(Constants.SELECT_TOURN_SIZE, function);
		do {
			//StringBuilder sb=new StringBuilder();
			//sb.append("\nSASEGASA Status:\n");
			while (!population.allSubPopConverged() || currGenerations <= maxGenerations) {
				for(int currentVillage=0;currentVillage<population.numberOfVillages();currentVillage++){
					LinkedList<QAPSolution> subPop=population.extrudeSubPopulation(currentVillage);
					boolean convergenceStatus=OSGeneticAlgorithm.evaluatePopulation(subPop, Constants.SUCESS_RATIO, Constants.MAX_SEL_PRESS);
					population.insertSubPopulation(currentVillage,subPop,convergenceStatus);
				}
				totalGenerations++;
				currGenerations++;
				//sb.append("|");
				//System.out.println(sb.toString());
			}
			//System.out.println();
			printRuntimeAlgorithmStatus();
			
			if (population.numberOfVillages() == 1)
				break;
			else {
				population.decreseVillageNum();
				currGenerations = 0;
			}
		} while (true);

		printRunResult();
	}

	private void printRunResult() {
		System.out.println("Status: "+totalGenerations);
		QAPSolution[] bestSubjects=population.bestFitnessPerSubpop();
		int villageCounter=0;
		for(QAPSolution solution:bestSubjects){
			System.out.print("In village : "+(villageCounter+1));
			villageCounter++;
			System.out.printf("Best fitness: %5.5f\n",solution.getFitness());
		}
	}

	private void printRuntimeAlgorithmStatus() {
		System.out.println("Total generations: "+totalGenerations);
		QAPSolution[] bestSubjects=population.bestFitnessPerSubpop();
		int villageCounter=0;
		for(QAPSolution solution:bestSubjects){
			System.out.print("In village : "+(villageCounter+1)+"\n");
			villageCounter++;
			System.out.printf("Best fitness: %5.5f\n",solution.getFitness());
			//System.out.println("Best found solution placement:");
			int[] factoryLocations=solution.getFactoryLocations();
			for(int factory=0;factory<factoryLocations.length;factory++)
				System.out.print(factoryLocations[factory]+" ");
			System.out.println();
		}
		
		System.out.println("\n\n\n");
	}
}
