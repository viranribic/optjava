package hr.fer.zemris.optjava.dz5.part2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import hr.fer.zemris.optjava.dz5.part1.interfaces.IOptAlgorithm;
import hr.fer.zemris.optjava.dz5.part2.algorithm.SASEGASA;
import hr.fer.zemris.optjava.dz5.part2.function.QAPFunction;

/**
 * QAP main class.
 * 
 * @author Viran
 *
 */
public class GeneticAlgorithm {
	private static int sizeOfInstance=0;
	private static int[][] locationMatrix=null;
	private static int[][] factoryMatrix=null;

	public static void main(String[] args) {
		if (args.length != 3)
			throw new IllegalArgumentException("Input arguments are not valid.");
		String src = args[0];
		int totalPopSize = Integer.parseInt(args[1]);
		int initNumOfSubPop = Integer.parseInt(args[2]);

		if(totalPopSize%initNumOfSubPop!=0){
			System.out.println("Total population size must be devisible with subpopulation size.");
			return;
		}
		getInputData(src);
		
		 QAPFunction function=new QAPFunction(sizeOfInstance,locationMatrix,factoryMatrix);
		
		 IOptAlgorithm algorithm= new SASEGASA(function,totalPopSize,initNumOfSubPop);
		 algorithm.run();
	}

	/**
	 * For the given source file, parse input matrix data to class argument variables.
	 * @param src Data source path.
	 */
	private static void getInputData(String src) {
		String line;
		int curARow = 0;
		int curBRow = 0;
		boolean locationMatrixFlag=true;
		
		try (BufferedReader input = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(new FileInputStream(src)), "UTF-8"))) {

			while (true) {
				line = input.readLine().trim();
				if (line.equals(""))
					continue;

				if (sizeOfInstance == 0) {
					
					sizeOfInstance = Integer.parseInt(line);
					locationMatrix=new int[sizeOfInstance][sizeOfInstance];
					factoryMatrix=new int[sizeOfInstance][sizeOfInstance];
					if (sizeOfInstance == 0)
						throw new RuntimeException("Bad source data given.");
					
				} else {
					if (locationMatrixFlag) {
						parseIntoMatrix(line,curARow++,locationMatrix);
						if(curARow==sizeOfInstance)
							locationMatrixFlag=false;
					} else {
						parseIntoMatrix(line, curBRow++, factoryMatrix);
						if(curBRow==sizeOfInstance)
							break;
					}
				}

			}

		} catch (IOException e) {

		}
	}
	
		/**
		 * Parse the given line to matrix elements for the given row.
		 * @param line Line input.
		 * @param curARow Current matrix row.
		 * @param matrix Matrix to which we add elements.
		 */
	private static void parseIntoMatrix(String line, int curARow, int[][] matrix) {
		String[] values=line.split(" ");
		int i=0;
		for(String s:values){
			if(s.trim().length()==0)
				continue;
			else
				matrix[curARow][i++]=Integer.parseInt(s);
		}
	}
	
	/**
	 * Write used matrices to standard output. Used for debugging only.
	 */
	@SuppressWarnings("unused")
	private static void writeMatrix(){
		System.out.println("A=");
		for(int i=0;i<sizeOfInstance;i++){
			for(int j=0;j<sizeOfInstance;j++){
				System.out.print(locationMatrix[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("B=");
		for(int i=0;i<sizeOfInstance;i++){
			for(int j=0;j<sizeOfInstance;j++){
				System.out.print(factoryMatrix[i][j]+" ");
			}
			System.out.println();
		}
	}
}
