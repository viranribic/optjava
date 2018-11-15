package data;

import java.util.LinkedList;

/**
 * DataContainer class.
 * @author Viran
 *
 */
public class DataContainer {
	private LinkedList<Double> data=new LinkedList<>();
	private int elementBufferLength;
	
	/**
	 * DataContainer constructor.
	 * @param l Element buffer length.
	 */
	public DataContainer(int l) {
		if (l<=0)
			throw new IllegalArgumentException("Element buffer length must be at least 1.");
		this.elementBufferLength=l;
	}
	
	/**
	 * Add new element.
	 * @param element New element.
	 */
	public void add(Double element){
		data.add(element);
	}
	
	/**
	 * Add new element at index.
	 * @param element New element.
	 * @param atIndex Element index.
	 */
	public void addAtIndex(Double element, int atIndex){
		data.add(atIndex, element);
	}
	
	/**
	 * Form a tuple of elementBufferLength elements as input values and one more element for the output value. 
	 * @param atIndex Index of the requested tuple.
	 * @return List of contained double values.
	 */
	public double[] getTuple(int atIndex){
		if(atIndex<0 || atIndex>=data.size()-elementBufferLength)
			throw new IndexOutOfBoundsException();
		double[] tuple=new double[elementBufferLength+1];
		for(int i=0;i<elementBufferLength+1;i++){
			tuple[i]=data.get(i+atIndex);
		}
		return tuple;
	}
	
	/**
	 * Get buffer length.
	 * @return Buffer length.
	 */
	public int getBufferLength(){
		return elementBufferLength;
	}

	/**
	 * Number of tuples this data collection can generate.
	 * @return Total number of tuples.
	 */
	public int numberOfTuples() {
		return data.size()-elementBufferLength;
	}

	/**
	 * Get input values in one tuple.
	 * @return Input values in one tuple.
	 */
	public int tupleInputNum() {
		return elementBufferLength;
	}

	/**
	 * Get output values in one tuple.
	 * @return Output values in one tuple.
	 */
	public int tupleOutputNum() {
		return 1;
	}

	/**
	 * Number of contained elements.
	 * @return Contained elements.
	 */
	public int size() {
		return data.size();
	}

	/**
	 * Get element at index.
	 * @param atIndex Index of the element.
	 * @return Requested element.
	 */
	public double get(int atIndex) {
		return data.get(atIndex);
	}
}
