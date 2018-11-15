package hr.fer.zemris.trisat;

import java.util.LinkedList;
import java.util.Random;

/**
 * IterateSearchAlgorithm is a optimisation algorithm.
 * @author Viran
 *
 */
public class IterateSearchAlgorithm implements SATAlgorithm {


	private static final int NUM_OF_ITERATION = 100_000;

	Random random=new Random();
	private SATFormula formula;
	
	
	BitVector vector;
	
	/**
	 * Constructor for IterateSearchAlgorithm class.
	 * @param inputClauses List of clauses contained in formula we are modelling.
	 * @param varNum Number of variables the clause is operating with.
	 */
	public IterateSearchAlgorithm(Clause[] inputClauses, int varNum) {
		this.formula=new SATFormula(varNum, inputClauses);
	}

	@Override
	public ResultLog run() {
		ResultLog log=new ResultLog();
		
		vector=new BitVector(random, formula.getNumberOfVariables());
		
		
		IterationFlowControl condition=new IterationFlowControl(NUM_OF_ITERATION);
		do{

			if(formula.isSatisfied(vector)){
				log.setGlobalOptimum(true);
				break;
			}
			
			BitVectorNGenerator vecGenerator=new BitVectorNGenerator(vector);
			BitVector nextVector=bestFromNeighbourhood(vecGenerator.createNeighbourhood());
			
			
			if(nextVector.equals(vector)){
				break;
			}
			vector=nextVector;
			
			condition.iterationPassed();
		}while(!condition.stopCondition());
		
		log.addVector(vector);
		
		return log; //add the resulting log
	}

	/**
	 * Find the vector with the greatest fitness function value from the given neighbourhood.
	 * @param neighborhood List of BitVectors we evaluate.
	 * @return The best vector from the neighbourhood.
	 */
	private BitVector bestFromNeighbourhood(
			MutableBitVector[] neighborhood) {
			
			int currBestFitness=fitnessFunction(vector);
			int maxFit=0;
			LinkedList<BitVector> bestVectors=new LinkedList<>();
			
			for(MutableBitVector vec:neighborhood){
				int vecFitness=fitnessFunction(vec);
				if(maxFit<vecFitness){
					maxFit=vecFitness;
				}
			}
			
			if(maxFit<currBestFitness)
				throw new OptimisationFailureException("Algoritam je zapeo u lokalnom optimumu.");

			for(MutableBitVector vec:neighborhood){
				int vecFitness=fitnessFunction(vec);
				if(vecFitness==maxFit)
					bestVectors.add(vec.copy());
			
			}
			return bestVectors.get(random.nextInt(bestVectors.size())).copy();
	}

	/**
	 * Number of clauses vector x satisfies. 
	 * @param x Testing vector.
	 * @return Number of satisfied clauses.
	 */
	private int fitnessFunction(BitVector x){
		int satisfied=0;
		for(int i=0;i<formula.getNumberOfClauses();i++){
		
			if(formula.getClause(i).isSatisfied(x))
				satisfied++;
		}
		
		return satisfied;
	}
	
}