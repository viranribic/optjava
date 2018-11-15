package hr.fer.zemris.optjava.dz12.util.evaluate;

import java.util.LinkedList;

public class LogicalTable {
	private LinkedList<Boolean> table;
	private LinkedList<String> inputLabels;
	
	public LogicalTable(LinkedList<String> inputLabels,LinkedList<Boolean> table) {
		this.inputLabels=inputLabels;
		this.table=table;
	}
	
	public Boolean evaluateInput(Integer inputValues){
		return table.get(inputValues);
	}
	
	public int tableSize(){
		return table.size();
	}

	public int getNumberOfVariables() {
		return inputLabels.size();
	}
	
	
}
