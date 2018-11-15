package hr.fer.zemris.optjava.dz2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for linear system analysis.
 * @author Viran
 *
 */
public class Sustav {

	public static void main(String[] args) {
		if (args.length != 3)
			System.out.println("Invalid number of arguemtns.");
		String type = args[0];
		int maxIter = Integer.parseInt(args[1]);

		double[][] coefficientsMatrix=null;
		double[] yValueArray=null;
		int numberOfVariables=0;
		
		try (BufferedReader input = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(args[2])), "UTF-8"))) {
			int lineNumber=0;
			
			while(true){
				String line=input.readLine();
				if(line==null)
					break;
				if(line.startsWith("#"))
					continue;
				if(line.startsWith("[")){
					line=line.replace("[", " ").replace("]", " ").trim();
					
					String[] coefs=line.split(",");
					
					
					if(coefficientsMatrix==null && yValueArray==null){
						numberOfVariables=coefs.length;
						coefficientsMatrix=new double[coefs.length-1][coefs.length-1];
						yValueArray=new double[coefs.length];
					}
					
					for(int i=0;i<numberOfVariables-1;i++){
						coefficientsMatrix[lineNumber][i]=Double.parseDouble(coefs[i]);
					}
					yValueArray[lineNumber++]=Double.parseDouble(coefs[coefs.length-1]);
				}
			}
		} catch (IOException e) {

		} 

		
		ErrorFunction optFunction=new ErrorFunction(coefficientsMatrix,yValueArray,numberOfVariables-1);
		//tu dalje..
		if(type.equals("grad")){
			NumOptAlgorithms.GradientDescent(optFunction, maxIter, null, null, false);
		}else if(type.equals("newton")){
			NumOptAlgorithms.NewstonsMethod(optFunction, maxIter, null, null, false);
		}
	}
}
