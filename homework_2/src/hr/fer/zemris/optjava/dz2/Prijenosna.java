package hr.fer.zemris.optjava.dz2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class for calculating the coefficients of a function.
 * @author Viran
 *
 */
public class Prijenosna {

	//Ovaj zadatak nije uspješno riješen: 
	//	Metoda gradijenta javlja NaN i Infinity vrijednosti
	//	Newton metoda nije ostvariva jer Hesseova matrica nije implementirana
	
	public static void main(String[] args) {
		if (args.length != 3)
			System.out.println("Invalid number of arguemtns.");
		String type = args[0];
		int maxIter = Integer.parseInt(args[1]);

		int numberOfVariables=0;
		
		LinkedList<LinkedList<Double>> functionsList=new LinkedList<>();
		LinkedList<Double> functionVlaues=new LinkedList<Double>();
		
		try (BufferedReader input = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(args[2])), "UTF-8"))) {
			
			
			while(true){
				String line=input.readLine();
				if(line==null)
					break;
				if(line.startsWith("#"))
					continue;
				if(line.startsWith("[")){
					line=line.replace("[", " ").replace("]", " ").trim();
					
					String[] coefs=line.split(",");
					numberOfVariables=coefs.length-1;
					LinkedList<Double> coefficients=new LinkedList<>();
					
					for(int i=0;i<numberOfVariables;i++){
						coefficients.add(Double.parseDouble(coefs[i]));
					}
					
					functionVlaues.add(Double.parseDouble(coefs[coefs.length-1]));
					functionsList.add(coefficients);
				}
			}
		} catch (IOException e) {

		} 

		TransferFunction optFunction= new TransferFunction(functionsList,functionVlaues,6);
		Random rand=new Random();
		double[] startingPoint=new double[6];
		for(int i=0;i<6;i++){
			startingPoint[i]=rand.nextDouble();
		}
		
		if(type.equals("grad")){
			NumOptAlgorithms.GradientDescent(optFunction, maxIter, startingPoint, null, false);
		}else if(type.equals("newton")){
			NumOptAlgorithms.NewstonsMethod(optFunction, maxIter, startingPoint, null, false);
		}
	}

}
