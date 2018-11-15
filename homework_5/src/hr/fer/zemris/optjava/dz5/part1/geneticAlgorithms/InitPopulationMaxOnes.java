package hr.fer.zemris.optjava.dz5.part1.geneticAlgorithms;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.part1.function.ModifiedMaxOnes;
import hr.fer.zemris.optjava.dz5.part1.geneticAlgorithms.population.RAPGAPopulation;
import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

/**
 * Class for Max-Ones problem population initialisation.
 * @author Viran
 *
 */
public class InitPopulationMaxOnes {

	/**
	 * Initialise the given population for solving Max-Ones problem.
	 * @param population Population in need of initialisation.
	 * @param function 
	 */
	public static void initPopulation(RAPGAPopulation population,int vectorSize, ModifiedMaxOnes function, int popSize) {
		
		Random random=new Random();
		while(popSize-->=0){
			
			boolean[] vector=new boolean[vectorSize];
			for(int i=0;i<vectorSize;i++){
				vector[i]=random.nextBoolean();
			}
			BitvectorSolution solution=new BitvectorSolution(vector);
			solution.setFitness(function.valueAt(solution));
			population.addSolution(solution);
		}
	}

}
