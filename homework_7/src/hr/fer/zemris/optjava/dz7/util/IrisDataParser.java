package hr.fer.zemris.optjava.dz7.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Parser for the iris data text file.
 * @author Viran
 *
 */
public class IrisDataParser {


	/**
	 * Parses the file given by path as input into a list of iris data elements.
	 * @param fileSrc File source path.
	 * @return List of data contained in file.
	 */
	public static LinkedList<IrisData> parseSourceData(String fileSrc) {
		
		String line;
		LinkedList<IrisData> irisData=new LinkedList<>();
		
		
		try(BufferedReader irisDataStream=new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(fileSrc)), "UTF-8"))){
			
			while (true) {
				line = irisDataStream.readLine();
				if(line==null)
					break;
				if (line.equals(""))
					continue;
				
				String[] splitExp=line.split(":");
				splitExp[0]=splitExp[0].replace("(", "").replace(")", "");
				splitExp[1]=splitExp[1].replace("(", "").replace(")", "");
				
				String[] inputValues= splitExp[0].split(",");
				String[] outputValues= splitExp[1].split(",");
				
				LinkedList<Double> inputDoubles=new LinkedList<>(); 
				for(String in:inputValues)
					inputDoubles.add(Double.parseDouble(in));
				
				LinkedList<Double> outputDoubles=new LinkedList<>(); 
				for(String out:outputValues)
					outputDoubles.add(Double.parseDouble(out));
				
				IrisData nextDataInput=new IrisData(inputDoubles,outputDoubles);
				irisData.add(nextDataInput);
			}
		}catch(IOException ignorable){
			
		}
		
		return irisData;
	}

}
