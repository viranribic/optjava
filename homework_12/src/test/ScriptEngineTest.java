package test;

import hr.fer.zemris.optjava.dz12.util.evaluate.LogicalTable;
import hr.fer.zemris.optjava.dz12.util.evaluate.LogicalTableGenerator;

public class ScriptEngineTest {

	public static void main(String[] args) {
		String inputString="(a OR b) AND c";
		LogicalTable table=LogicalTableGenerator.genFromString(inputString);
		
	}
	
	
}
