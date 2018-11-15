package hr.fer.zemris.optjava.dz2;

import java.util.LinkedList;

import Jama.Matrix;

/**
 * Class for solving the unknown function coefficients.
 * 
 * @author Viran
 *
 */
public class TransferFunction implements IHFunction {

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
			LinkedList<Double> functionVlaues, int numberOfVariables) {
		this.functionList = functionsList;
		this.functionVlaues = functionVlaues;
		this.numberOfVariables = numberOfVariables;

	}

	@Override
	public int domainDimension() {
		return numberOfVariables;
	}

	@Override
	public double functionCalc(Matrix value) {
		double f = 0;
		for (int i = 0; i < numberOfVariables; i++) {
			f += Math.pow(calcParticularFunction(i, value), 2);
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
	private double calcParticularFunction(int i, Matrix value) {
		LinkedList<Double> functionCoef = functionList.get(i);
		Double y = functionVlaues.get(i);
		double f = 0;
		// f= a*x1 + b*x1^3*x2 + c* exp(d*x3)*(1+cos(e * x4)) + f*x4*x5^2
		// a=value.get(0,0) x1=functionCoef.get(0)
		// b=value.get(1,0) x2=functionCoef.get(1)
		// c=value.get(2,0) x3=functionCoef.get(2)
		// d=value.get(3,0) x4=functionCoef.get(3)
		// e=value.get(4,0) x5=functionCoef.get(4)
		// f=value.get(5,0)

		f = value.get(0, 0) * functionCoef.get(0) + value.get(1, 0)
				* Math.pow(functionCoef.get(0), 2) * functionCoef.get(1)
				+ value.get(2, 0)
				* Math.exp(value.get(3, 0) * functionCoef.get(2))
				* (1 + Math.cos(value.get(4, 0) * functionCoef.get(2)))
				+ value.get(5, 0) * functionCoef.get(3)
				* Math.pow(functionCoef.get(4), 2);
		;
		return f - y;
	}

	@Override
	public Matrix gradCalc(Matrix value) {
		Matrix grad = new Matrix(numberOfVariables, 1);

		for (int i = 0; i < numberOfVariables; i++) {
			grad = grad.plus(calcParticularGrad(i, value).times(2).times(
					calcParticularFunction(i, value)));
		}
		return grad;
	}

	private Matrix calcParticularGrad(int i, Matrix value) {
		Matrix gradFunc = new Matrix(numberOfVariables, 1);

		LinkedList<Double> functionCoef = functionList.get(i);

		gradFunc.set(
				0,
				0,
				value.get(0, 0) + 3 * value.get(1, 0)
						* Math.pow(functionCoef.get(0), 2)
						* functionCoef.get(1));
		gradFunc.set(1, 0, value.get(1, 0) * Math.pow(functionCoef.get(0), 3));
		gradFunc.set(
				2,
				0,
				value.get(2, 0) * value.get(3, 0)
						* Math.exp(value.get(3, 0) * functionCoef.get(2))
						* (1 + Math.cos(value.get(4, 0) * functionCoef.get(3))));
		gradFunc.set(
				3,
				0,
				-1 * value.get(2, 0)
						* Math.exp(value.get(3, 0) * functionCoef.get(2))
						* Math.sin(value.get(4, 0) * functionCoef.get(3))
						* value.get(4, 0) + value.get(5, 0)
						* Math.pow(functionCoef.get(4), 2));
		gradFunc.set(4, 0, value.get(5, 0) * functionCoef.get(3) * 2
				* functionCoef.get(4));

		return gradFunc;
	}

	@Override
	public Matrix HessianMatrix(Matrix value) {
		// TODO Auto-generated method stub
		return null;
	}

}
