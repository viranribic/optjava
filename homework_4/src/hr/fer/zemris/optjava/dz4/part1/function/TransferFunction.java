package hr.fer.zemris.optjava.dz4.part1.function;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz4.part1.interfaces.IFunction;

/**
 * Class for solving the unknown function coefficients.
 * 
 * @author Viran
 *
 */
public class TransferFunction implements IFunction {

	private LinkedList<LinkedList<Double>> functionList;
	private LinkedList<Double> functionVlaues;
	private int numberOfVariables;

	/**
	 * Basic constructor for TransferFunction class.
	 * 
	 * @param functionsList
	 *            List of points of this function for who the resulting values
	 *            is known.
	 * @param functionVlaues
	 *            Outputs of the function for the given points.
	 * @param numberOfVariables
	 *            Number of variables of this function.
	 */
	public TransferFunction(LinkedList<LinkedList<Double>> functionsList,
			LinkedList<Double> functionVlaues) {
		this.functionList = functionsList;
		this.functionVlaues = functionVlaues;
		this.numberOfVariables = 6;	//a,b,c,d,e,f
	}

	
	@Override
	public double valueAt(double[] value){
		if(value.length!=6)
			throw new IllegalArgumentException("TransferFunction-This function can not be calculated with the given value.");
		double f = 0;
		for (int i = 0; i < numberOfVariables; i++) {
			Double y = functionVlaues.get(i);
			f += Math.pow(calcParticularFunction(i, value)-y, 2);
		}
		return f;
	}

	/**
	 * Calculates the value of the i-th function in the given position.
	 * 
	 * @param i
	 *            Position of the function in its array.
	 * @param value
	 *            Position on the domain.
	 * @return Value of this transfer function error.
	 */
	private double calcParticularFunction(int i, double[] value) {
		LinkedList<Double> functionCoef = functionList.get(i);
		
		double f = 0;
		// f= a*x1 + b*x1^3*x2 + c* exp(d*x3)*(1+cos(e * x4)) + f*x4*x5^2
		// a=value[0] x1=functionCoef.get(0)
		// b=value[1] x2=functionCoef.get(1)
		// c=value[2] x3=functionCoef.get(2)
		// d=value[3] x4=functionCoef.get(3)
		// e=value[4] x5=functionCoef.get(4)
		// f=value[5]

		f = value[0] * functionCoef.get(0) + value[1]
				* Math.pow(functionCoef.get(0), 3) * functionCoef.get(1)
				+ value[2]
				* Math.exp(value[3] * functionCoef.get(2))
				* (1 + Math.cos(value[4] * functionCoef.get(3)))
				+ value[5] * functionCoef.get(3)
				* Math.pow(functionCoef.get(4), 2);
		;
		return f;
	}

}
