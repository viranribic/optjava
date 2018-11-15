package hr.fer.zemris.optjava.dz5.part1.constants;

/**
 * Constants used in modified Max-Ones problem.
 * @author Viran
 *
 */
public class Constants {

	public static final boolean TASK_CASE_1 = true;
	
	//Selection pressure constants
	public static final double MAX_SELECTION_PRESSURE = 100; 
	public static final double COMPARISON_FACTOR = 0.7;  
	public static final int MAX_EFFORT = 10_000;
	public static final double SUCCESS_RATIO = 0.7; 
	
	//Population definition constants
	public static final int MAX_POP_SIZE = 500;
	public static final int MIN_POP_SIZE = 100;

	//Genetic operator constants
	public static final int NUMBER_OF_CROSSOVER_POINTS_T = 50;  
	public static final double MUTATION_PROBABILITY = 1.0;
	public static final int TOURNAMENT_SIZE_K = 5; //k=2,3,5

	public static final double COMP_FACTOR_GROWTH = 1/MAX_EFFORT; 

	
	
}
