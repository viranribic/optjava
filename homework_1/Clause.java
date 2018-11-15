package hr.fer.zemris.trisat;
/**
 * Clause object contains the information of a cluster of literals which describes a formula of the propositional logics.
 * @author Viran
 *
 */
public class Clause {

	private int[] indexes;
	
	/**
	 * Construct a clause with the literal values described with the index array. Positive value marks for a regular variable
	 * and the negative value represents its complement.
	 * @param indexes Clause given as an array of integer values.
	 */
	public Clause(int[] indexes) {
		this.indexes=indexes.clone();
	}
	
	/**
	 * Get the number of literals this clause contains.
	 * @return Number of this clause literals.
	 */
	public int getSize() {
		return indexes.length;
	}
	
	/**
	 * Get the literal at given index.
	 * @param index Position of the literal in this clause.
	 * @return Literal value.
	 */
	public int getLiteral(int index) {
		return indexes[index];
	}
	
	/**
	 * If the clause is satisfied by the given BitVector return true. Otherwise return false.
	 * @param assignment Testing BitVector.
	 * @return Decision weather the given vector satisfies this clause or not.
	 */
	public boolean isSatisfied(BitVector assignment) {
		
		for(int i=0;i<indexes.length;i++){
			
			//Check if vector matches the clause in every position
			if((	(indexes[i]>0 && assignment.get(indexes[i]-1)==true)	
					||	(indexes[i]<0 && assignment.get(-1*indexes[i]-1)==false)	)){
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append("( ");
		for(int i=0; i<indexes.length;i++){
			if(i!=0)
				sb.append(" + ");
			sb.append(indexes[i]);
		}
		sb.append(" )");
		return sb.toString();

	}
}
