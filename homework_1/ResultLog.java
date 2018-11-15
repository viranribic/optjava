package hr.fer.zemris.trisat;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * ResultLog class provides the informations of an optimisation execution.
 * @author Viran
 *
 */
public class ResultLog implements Iterable<BitVector>{
	private LinkedList<BitVector> resultingVectors=new LinkedList<>();
	private boolean globalOptima=false;
	
	/**
	 * Adds a vector to the ResultLog collection of acceptable results.
	 * @param vector Vector we want to store.
	 */
	public void addVector(BitVector vector){
		if(!resultingVectors.contains(vector))
			resultingVectors.add(vector);
	}

	/**
	 * Get the number of stored vectors.
	 * @return Size of the stored vectors.
	 */
	public int size(){
		return resultingVectors.size();
	}
	@Override
	public Iterator<BitVector> iterator() {
		return new Iterator<BitVector>(){

			private int pos=0;
			
			@Override
			public boolean hasNext() {
				return (pos<resultingVectors.size())?true:false;
			}

			@Override
			public BitVector next() {
				return resultingVectors.get(pos++);
			}
			
		};
	}

	public void setGlobalOptimum(boolean value) {
		this.globalOptima=value;
	}
	
	public boolean getGlobalOptimum(){
		return globalOptima;
	}
}
