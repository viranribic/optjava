package data;

import java.util.LinkedList;

public class DataSet {

	LinkedList<Double> inputValues;
	LinkedList<Double> outputValues;
	
	/**
	 * IrisData constructor.
	 * @param inputDoubles Input data values.
	 * @param outputDoubles Output data values.
	 */
	@SuppressWarnings("unchecked")
	public DataSet(LinkedList<Double> inputDoubles, LinkedList<Double> outputDoubles) {
		this.inputValues=(LinkedList<Double>)inputDoubles.clone();
		this.outputValues=(LinkedList<Double>)outputDoubles.clone();
	}
	/**
	 * Get the number of input elements.
	 * @return Number of input data elements.
	 */
	public int inputDataSize(){
		return inputValues.size();
	}
	
	/**
	 * Get the number of output elements.
	 * @return  Number of output data elements.
	 */
	public int outputDataSize(){
		return outputValues.size();
	}
	
	/**
	 * Get input value for the given index.
	 * @param atIndex Index of the requested value.
	 * @return Value at index.
	 */
	public Double getInputValue(int atIndex){
		return inputValues.get(atIndex);
	}
	
	/**
	 * Get output value for the given index.
	 * @param atIndex Index of the requested value.
	 * @return Value at index.
	 */
	public Double getOutputValue(int atIndex){
		return outputValues.get(atIndex);
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("(");
		for(int i=0;i<inputValues.size();i++){
			if(i==0)
				sb.append(String.format("%2.1f", inputValues.get(i)));				
			else
				sb.append(String.format(",%2.1f", inputValues.get(i)));				
			
		}
		sb.append(") : (");
		for(int i=0;i<outputValues.size();i++){
			if(i==0)
				sb.append(String.format("%2.0f", outputValues.get(i)));				
			else
				sb.append(String.format(",%2.0f", outputValues.get(i)));				
			
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Get the input values as array.
	 * @return Array of input values.
	 */
	public double[] getInputValues() {
		double[] inputArray=new double[inputValues.size()];
		for(int i=0;i<inputArray.length;i++)
			inputArray[i]=inputValues.get(i);
		return inputArray;
	}
	
}
