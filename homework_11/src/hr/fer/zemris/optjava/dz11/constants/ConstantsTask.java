package hr.fer.zemris.optjava.dz11.constants;

/**
 * Constants used in function optimisation.
 * @author Viran
 *
 */
public class ConstantsTask {

	public static final double[] MAX_COMPONENT_VALS = new double[] {-10,-10,-10,-10,-10,-10};
	public static final double[] MIN_COMPONENT_VALS = new double[] {10,10,10,10,10,10};

	public static final int NUMBER_OF_ELITE_SUBJECTS = 2;

	public static final int DOMAIN_DIM=6;
	public static final double ALPHA = 0.5;
	
	public static final double SIGMA=0.5; //IDK THIS VALUE!!
	public static final int TOURNAMENT_SIZE = 3;
	public static final int MAX_GREY_VAL = 256;
	public static final double XOVER_TAKE_BETTER_PROB = 0.60;
	
	public static final Integer TASK_REQUEST_NUM = 5;
	

}
