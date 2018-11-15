package hr.fer.zemris.optjava.dz12.util.evaluate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class LogicalTableGenerator {

	private static ScriptEngine engine= new ScriptEngineManager().getEngineByName("JavaScript");
	
	public static LogicalTable genFromString(String formula){
		formula=modifyString(formula);
		LinkedList<String> variables=getFormulaVariables(formula);
		LinkedList<Boolean> table=genTable(formula,variables);
		return new LogicalTable(variables, table);
		
	}
	
	private static LinkedList<Boolean> genTable(String formula, LinkedList<String> variables) {
		int combinations=(int) Math.pow(2, variables.size());
		LinkedList<Boolean> table=new LinkedList<>();
		for(int i=0;i<combinations;i++){
			table.add(getValue(i,variables,formula));
		}
		return table;
	}

	private static Boolean getValue(int number, LinkedList<String> variables, String formula) {
		int variableCount=variables.size();
		Integer[] binInt=getListFromInt(number,variableCount);
		formula=insertValues(formula,variables,binInt);
		Boolean res=false;
		try {
			res=(Boolean)engine.eval(formula);
		} catch (ScriptException e) {
			System.out.println("Given formula can't be evaluated.");
			System.exit(-1);
		}
		return res;
	}

	private static String insertValues(String formula, LinkedList<String> variables, Integer[] binInt) {
		for(int i=0;i<binInt.length;i++){
			formula=formula.replace(variables.get(i), (binInt[i]==1)?"true":"false");
		}
		return formula;
	}

	private static Integer[] getListFromInt(int number, int listSize) {
		Integer[] list=new Integer[listSize];
		Arrays.fill(list, 0);
		int listIndex=listSize-1;
		while(number>0){
			list[listIndex]=number%2;
			number/=2;
			listIndex--;
		}
		return list;
	}

	private static LinkedList<String> getFormulaVariables(String formula) {
		formula=formula.replaceAll("&&", "").replaceAll("\\|\\|", "").replaceAll("==", "").replaceAll("!", "").replaceAll("\\(", "").replaceAll("\\)", "");
		String[] varList=formula.split("");
		Set<String> variables=new HashSet<>();
		for(String var:varList){
			if(var.trim().equals(""))
				continue;
			variables.add(var);
		}
		return new LinkedList<>(variables);
	}

	private static String modifyString(String input){
		String output;
		output=input.replaceAll("AND","&&" );
		output=output.replaceAll("OR","||");
		output=output.replaceAll("=", "==");
		output=output.replaceAll("NOT", "!");	
		return output;
	}
}
