package hr.fer.zemris.optjava.dz12.algorithm.errorFunction;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLB;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.interfaces.IEvaluator;
import hr.fer.zemris.optjava.dz12.util.evaluate.LogicalTable;
import hr.fer.zemris.optjava.dz12.util.evaluate.LogicalTableGenerator;

public class CLBEvaluator implements IEvaluator {

	private LogicalTable table;

	public CLBEvaluator(String formula) {
		table = LogicalTableGenerator.genFromString(formula);
	}

	public int numberOfVariables() {
		return table.getNumberOfVariables();
	}

	@Override
	public CLBSolution evaluate(CLBSolution solution) {
		int clbNum = solution.getNumberOfCLBs();
		int clbInNum = solution.getCLBInputs();
		int[] allErrorsList = new int[clbNum];

		int tableElements = table.tableSize();

		Arrays.fill(allErrorsList, 0);
		for (int tableKey = 0; tableKey < tableElements; tableKey++) {
			int[] resultingOutputs = testKey(tableKey, table.getNumberOfVariables(), solution);
			int realValue = (table.evaluateInput(tableKey)) ? 1 : 0;
			for (int ev = 0; ev < clbNum; ev++) {
				if (resultingOutputs[ev + clbInNum] != realValue) {
					allErrorsList[ev]++;
				}

			}
		}
		solution.setError(findMinError(allErrorsList));
		return solution;
	}

	private double findMinError(int[] allErrorsList) {
		int minVal = 0;
		for (int i = 0; i < allErrorsList.length; i++) {
			if (i == 0)
				minVal = allErrorsList[i];
			else if (minVal > allErrorsList[i])
				minVal = allErrorsList[i];
		}
		return minVal;
	}

	private int[] testKey(int tableKey, int numOfFuncVars, CLBSolution solution) {
		
		int clbNum = solution.getNumberOfCLBs();
		// get the key as int list
		Integer[] binInt = getListFromInt(tableKey, numOfFuncVars);
		int[] proccessOutputs = new int[clbNum + table.getNumberOfVariables()];
		// initialise process outputs
		for (int i = 0; i < binInt.length; i++)
			proccessOutputs[i] = binInt[i];

		fillProcessOutputs(proccessOutputs, solution);

		return proccessOutputs;
	}

	private void fillProcessOutputs(int[] processOutputs, CLBSolution solution) {
		int clbInNum = solution.getCLBInputs();
		int clbNum = solution.getNumberOfCLBs();
		int procIndex = clbInNum;
		// processOutputs contains only the variable starting values.
		// while moving trough the CLB components we decode particular CLB input
		// trough processOutputs values and
		// find it's LUT counterpart adding that values as the CLB output

		for (int clbIndex = 0; clbIndex < clbNum; clbIndex++) {
			CLB curCLB = solution.getCLB(clbIndex);
			int LUTpos = 0;
			// lowest values is last in list
			for (int clbEntryIndex = clbInNum - 1; clbEntryIndex >= 0; clbEntryIndex--) {
				LUTpos *= 2;
				try {
					LUTpos += processOutputs[curCLB.getinputIndexList().get(clbEntryIndex)];
				} catch (IndexOutOfBoundsException e) {
					System.out.println("HOLD UP!");
				}
			}
			processOutputs[procIndex++] = curCLB.getLUT().get(LUTpos);

		}

	}

	private static Integer[] getListFromInt(int number, int listSize) {
		Integer[] list = new Integer[listSize];
		Arrays.fill(list, 0);
		int listIndex = listSize - 1;
		while (number > 0) {
			list[listIndex] = number % 2;
			number /= 2;
			listIndex--;
		}
		return list;
	}
}
