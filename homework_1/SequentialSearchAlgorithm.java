package hr.fer.zemris.trisat;


/**
 * SequentialSearchAlgorithm is an algorithm 
 * @author Viran
 *
 */
public class SequentialSearchAlgorithm implements SATAlgorithm{

	private SATFormula formula;
	
	/**
	 * SequentialSearchAlgorithm constructor.
	 * @param inputClauses Clauses which describe the formula we evaluate.
	 * @param varNum Number of literals in each clause.
	 */
	public SequentialSearchAlgorithm(Clause[] inputClauses,int varNum) {
		this.formula=new SATFormula(varNum, inputClauses);
	}

	@Override
	public ResultLog run() {
		ResultLog log=new ResultLog();
		MutableBitVector testingVector=new MutableBitVector(formula.getNumberOfVariables());
			
		MutableBitVector endVector=maxVector(new MutableBitVector(testingVector.getSize()));
		
		while(!testingVector.equals(endVector)){
			
			if(formula.isSatisfied(testingVector)){
				log.addVector(testingVector.copy());
				//only one solution needed
				break;
			}
			testingVector=nextVector(testingVector);
			
		}
		
			log.setGlobalOptimum(true);
			return log;
	}
	
	/**
	 * For a given MutableBitVector determinate the next in sequence as binary addition with the vector x1.
	 * @param vector Starting vector.
	 * @return Next vector calculated as: v(n+1)=v(n)+x1.
	 */
	private static MutableBitVector nextVector(MutableBitVector vector){
		int pos=0;
		while(true){
			if(vector.get(pos)==false){
				vector.set(pos, true);
				break;
			}else{
				vector.set(pos, false);
				pos++;
			}
		}
		return vector;
	}
	
	/**
	 * Set a binary representation of a decade number to the given vector.
	 * @param vector Vector to which we assign the decade value. 
	 * @param startValue Decade value.
	 * @return Resulting vector.
	 */
	@SuppressWarnings("unused")
	private static MutableBitVector initVector(MutableBitVector vector, int startValue){
		int pos=0;
		while(startValue>0){
			if(startValue%2==0)
				vector.set(pos, false);
			else
				vector.set(pos, true);
			pos++;
			startValue/=2;
		}
		return vector;
	}
	
	/**
	 * Generate a vector with all values set to 1 (true).
	 * @param maxValVector Provided vector. 
	 * @return Generated vector.
	 */
	private static MutableBitVector maxVector(MutableBitVector maxValVector){
		for(int i=0;i<maxValVector.getSize();i++){
			maxValVector.set(i, true);
		}
		return maxValVector;
	}
}
