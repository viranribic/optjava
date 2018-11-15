package hr.fer.zemris.optjava.dz5.part2.constants;

/**
 * SASEGASA constants.
 * @author Viran
 *
 */
public class Constants {

	public static final int MAX_GENERATIONS = 100;
	public static final int SELECT_TOURN_SIZE = 3;
	
	public static final double XOVER_RAND_POS_PERC = 0.5; //number of random positions in crossover depending on the domain dim
	public static final double MUTATION_PROBABILITY = 0.9;
	public static final int MAX_ITERATIONS = 100; //max iterations in OSGA

	public static final double COMPARISON_FACTOR = 0.1;
	public static final double COMP_FACTOR_GROWTH = 1/MAX_ITERATIONS;
	
	public static final double MAX_SEL_PRESS = 10;
	public static final double SUCESS_RATIO = 0.9;  

}
