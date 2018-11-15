package hr.fer.zemris.trisat;

/**
 * SATFormula is the object representation of the propositional logics formula containing the respective clauses
 * and modelling its behaviour.
 * @author Viran
 *
 */
public class SATFormula {

	private int numberOfVariables;
	private Clause[] clauses;
	
	/**
	 * Constructor for SATFormula.
	 * @param numberOfVariables Number of variables each clause has.
	 * @param clauses List of clauses that are contained in the formula.
	 */
	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.numberOfVariables=numberOfVariables;
		this.clauses=clauses.clone();
		
	}
	/**
	 * Return the number of variables(literals).
	 * @return Number of literals each clause contains.
	 */
	public int getNumberOfVariables() {
		return numberOfVariables;
	}
	/**
	 * Return the number of clauses.
	 * @return Number of clauses that are contained in this formula.
	 */
	public int getNumberOfClauses() {
		return clauses.length;
	}
	/**
	 * Get the clause at the given index.
	 * @param index Position of the clause in the internal array.
	 * @return The requested clause.
	 */
	public Clause getClause(int index) {
		return clauses[index];
	}
	/**
	 * Test if the formula is completely satisfied for the given assignment vector.
	 * @param assignment Vector we test.
	 * @return True if the condition is met, false otherwise.
	 */
	public boolean isSatisfied(BitVector assignment) {
		for(int i=0;i<clauses.length;i++){
			if(clauses[i].isSatisfied(assignment)==false)
				return false;
		}
		return true;
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<clauses.length;i++)
			sb.append(clauses[i]);
		return sb.toString();
	}
}
