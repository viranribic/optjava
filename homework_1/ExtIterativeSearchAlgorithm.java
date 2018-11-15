package hr.fer.zemris.trisat;

import java.util.Random;

/**
 * Extended iterative search algorithm.
 * @author Viran
 *
 */
public class ExtIterativeSearchAlgorithm implements SATAlgorithm {



	private static final int numberOfBest = 2;
	private static final int NUM_OF_ITERATION = 100000;

	Random random=new Random();
	private SATFormula formula;
	private SATFormulaStats formulaStats;

	BitVector vector;
	
	/**
	 * Constructor for extended iterative search algorithm.
	 * @param inputClauses List of clauses which form a specific formula.
	 * @param varNum Number of literals in formula.
	 */
	public ExtIterativeSearchAlgorithm(Clause[] inputClauses, int varNum) {
		this.formula=new SATFormula(varNum, inputClauses);
		this.formulaStats=new SATFormulaStats(formula);
	}

	@Override
	public ResultLog run() {
		ResultLog log=new ResultLog();
		
		
		vector=new BitVector(random, formula.getNumberOfVariables());
		
		IterationFlowControl condition=new IterationFlowControl(NUM_OF_ITERATION);
		do{
			formulaStats.setAssignment(vector, true);
			BitVectorNGenerator vecGenerator=new BitVectorNGenerator(vector);
			BitVector nextVector=evaluateNeighbourhood(vecGenerator.createNeighbourhood());
			if(nextVector.equals(vector))
				break;
			
			vector=nextVector;
			
			if(formula.isSatisfied(vector)){
				log.addVector(vector);
				log.setGlobalOptimum(true);
				break;
			}
			
			condition.iterationPassed();
		}while(!condition.stopCondition());

		
		
		return log; 
	}

	/**
	 * Evaluates the neighbourhood of the given BitVectors and returns the one with the greatest fitness result.
	 * @param neighborhood Neighbourhood of BitVectors we evaluate.
	 * @return The best found BitVector.
	 */
	private BitVector evaluateNeighbourhood(MutableBitVector[] neighborhood){
		double[] fitnessList=new double[neighborhood.length];
		int pos=0;
		
		for(MutableBitVector vec:neighborhood){
			formulaStats.setAssignment(vec, false);
			fitnessList[pos]=formulaStats.getNumberOfSatisfied()+formulaStats.getPercentageBonus();
			pos++;
		}
		
		int bestPos=0;
		BitVector[] bestVectors=new BitVector[numberOfBest];
		for(int chooseBest=0;chooseBest<numberOfBest;chooseBest++){
			double maxFit=0;
			for(int i=0;i<neighborhood.length;i++){
				if(maxFit<fitnessList[i]){
					maxFit=fitnessList[i];
					bestPos=i;
				}
			}
			
			fitnessList[bestPos]=0;
			bestVectors[chooseBest]=neighborhood[bestPos].copy();
		}
		
		int result=random.nextInt(numberOfBest);
		return bestVectors[(result>0)?result:-1*result];
	}
	

}
