package hr.fer.zemris.trisat;

/**
 * SATFormulaStats class keeps track of the optimisation process of a function.
 * @author Viran
 *
 */
public class SATFormulaStats {

	private static final double percentageConstantUp = 0.01;
	private static final double percentageConstantDown = 0.1;
	//private static final int numberOfBest = 2;
	private static final double percentageUnitAmount = 0.50;
	
	
	
	private SATFormula formula;
	private int numberOfSatisfied=0;
	private boolean isSatisfied=false;
	private double percentageBonus=.0;
	private double[] percentageList;
	
	/**
	 * Construction which pairs a formula to its statistics corresponding object.
	 * @param formula
	 */
	public SATFormulaStats(SATFormula formula) {
		this.formula=formula;
		percentageList=new double[formula.getNumberOfClauses()];
	}
	
	/**
	 * Process the given assignment vector and collect the following information:
	 *  	Percentage list which contains the information of successfully tested clauses.
	 *  	Number of satisfied clauses.
	 * 		Decision weather the formula is satisfied of not.
	 *  	Percentage bonus used as correction coefficient.
	 * @param assignment BitVector we are testing.
	 * @param updatePercentages Decision if the given vector affect the statistical values of this formula.
	 */
	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		
			//vrsimo promjenu statistickih vrijednosti i brojimo koliko klauzula taj vektor zadovoljava
			numberOfSatisfied=0;
			for(int i=0;i<percentageList.length;i++){
				if(formula.getClause(i).isSatisfied(assignment)){
					//brojimo koliko zadovoljava
					numberOfSatisfied++;
					if(updatePercentages)
						//update-amo samo ako treba
						percentageList[i]+=(1-percentageList[i])*percentageConstantUp;
				}else{
					if(updatePercentages)
						//update-amo samo ako treba
						percentageList[i]+=(0-percentageList[i])*percentageConstantDown;
				}
			}
			//provjera je li cijela formula zadovoljiva
			if(numberOfSatisfied==formula.getNumberOfClauses())
				isSatisfied=true;
			else
				isSatisfied=false;
			percentageBonus=numberOfSatisfied;
			for(int i=0;i<percentageList.length;i++){
				if(formula.getClause(i).isSatisfied(assignment)){
					percentageBonus+=percentageUnitAmount *(1-percentageList[i]);
				}else{
					percentageBonus+=-percentageUnitAmount *(1-percentageList[i]);
				}
			}
	}
	
	/**
	 * Number of clauses satisfied.
	 * @return Satisfied clauses.
	 */
	public int getNumberOfSatisfied() {
		return numberOfSatisfied;
	}

	/**
	 * Decision if this formula satisfies the formula or not.
	 * @return True if the formula is satisfied, false otherwise.
	 */
	public boolean isSatisfied() {
		return isSatisfied;
	}

	/**
	 * Correction coefficient.
	 * @return Correction coefficient
	 */
	public double getPercentageBonus() {
		return percentageBonus;
	}
	
	/**
	 * Percentage of a particular clause in the list.
	 * @param index Position of the clause.
	 * @return Percentage for the specified clause.
	 */
	public double getPercentage(int index) {
		if(index<0 || index>percentageList.length)
			throw new IndexOutOfBoundsException();
		return percentageList[index];
	}
}
