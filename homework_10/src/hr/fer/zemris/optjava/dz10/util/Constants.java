package hr.fer.zemris.optjava.dz10.util;

public class Constants {

	//This parameters stay the way they are if the need to solve the homework assignment.
	public static final double LOWER_BOUNDARY_PROB2_X1 = 0.1;
	public static final double LOWER_BOUNDARY_PROB2_X2 = 0;
	public static final double UPPER_BOUNDARY_PROB2_X1 = 1;
	public static final double UPPER_BOUNDARY_PROB2_X2 = 5;
	public static final double LOWER_BOUNDARY_PROB1 = -5;
	public static final double UPPER_BOUNDARY_PROB1 = 5;
	public static final int PROBLEM1_DOMAIN_DIM = 4;
	public static final int PROBLEM2_DOMAIN_DIM = 2;
	public static final double SOMETHING_SMALL = 1E-15;
	
	// These parameters are set to perform in the best way possible for the specific problems.
	public static final double ALPHA_BLX = 0.01; 
	public static final double GAUSS_DIST_MUTAT_SIGMA = 0.001;
	
	public static final double ALPHA_SHARING_FUNCTION = 2;
	public static final double SIGMA_DISTANCE = 0.5;
	
	//Optionally if you want to monitor the front sizes as the algorithm runs, set this flag to true.
	public static final boolean PRINT_TO_SCREEN=true;

}
