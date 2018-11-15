package hr.fer.zemris.trisat;

import java.util.Iterator;

/**
 * BitVectorNGenerator generates the neighbourhood of the vector given as constructor argument.
 * @author Viran
 *
 */
public class BitVectorNGenerator implements Iterable<MutableBitVector> {

	private BitVector assignment;
	private MutableBitVector[] resultingNeighborhood;
	
	/**
	 * BitVector whose neighbourhood we need to determinate.
	 * @param assignment Vector we evaluate.
	 */
	public BitVectorNGenerator(BitVector assignment) {
		this.assignment=assignment;
	}
	
	/**
	 * Creates the neighbourhood for the given vector as all the vector who can be generated from the assignment vector
	 * by changing one position only in its sequence.
	 * @return List of MutableBitVector object which meet the requirements listed in the description.
	 */
	public MutableBitVector[] createNeighbourhood() {
		
		resultingNeighborhood=new MutableBitVector[assignment.getSize()]; 
		for(int i=0;i<assignment.getSize();i++){
			MutableBitVector newNeighbor=new MutableBitVector(assignment.bits);
			boolean newVal=(newNeighbor.get(i))?false:true;
			newNeighbor.set(i, newVal);
			resultingNeighborhood[i]=newNeighbor;
		}
		
		return resultingNeighborhood;
	}
	
	
	@Override
	public Iterator<MutableBitVector> iterator() {
		return new Iterator<MutableBitVector>(){
			private int pos=0;		
			
			@Override
			public boolean hasNext() {
				return (pos<resultingNeighborhood.length)?true:false;
			}

			@Override
			public MutableBitVector next() {
				return resultingNeighborhood[pos++];
			}
			
		};
	}


}
