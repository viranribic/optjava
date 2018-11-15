package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

/**
 * Linear system solver adapted for optimisation algorithms using approximations for minimal distance from solution.
 * @author Viran
 *
 */
public class ErrorFunction implements IHFunction {

	private double[][] coefficients;
	
	private int dim;

	private double[] yArray;
	
	/**
	 * Error function constructor.
	 * @param coefficients Matrix of coefficients of linear system variables.
	 * @param yArray Values of linear functions.
	 * @param numberOfVariables Number of variables in linear system.
	 */
	public ErrorFunction(double[][] coefficients,double[] yArray, int numberOfVariables) {
		this.coefficients=coefficients;
		this.yArray=yArray;
		dim=numberOfVariables;
	}
	
	@Override
	public int domainDimension() {
		return dim;
	}

	@Override
	public double functionCalc(Matrix value) {
		double f=0;
		
		for(int i=0;i<dim;i++){
			f+=Math.pow(subFunctionRes(value, i), 2);
		}
		return f;
	}

	/**
	 * Result of the specified sub-function in the given value.
	 * @param value Position in which the function is being calculated.
	 * @param subFunctionPos Line of the function we need to evaluate.
	 * @return Function result.
	 */
	private double subFunctionRes(Matrix value,int subFunctionPos){
		double subFunction=0;
		for(int j=0;j<dim;j++){
			subFunction+=coefficients[subFunctionPos][j]*value.get(j, 0);
		}
		return subFunction-yArray[subFunctionPos];
	}
	
	@Override
	public Matrix gradCalc(Matrix value) {
		Matrix grad=new Matrix(dim, 1);
		for(int i=0;i<dim;i++){
			double gradAtPosition=0;
			for(int j=0;j<dim;j++){
				gradAtPosition+=2*subFunctionRes(value, j)*coefficients[j][i];
			}
			grad.set(i, 0, gradAtPosition);
		}
		return grad;
	}

	@Override
	public Matrix HessianMatrix(Matrix value) {
		Matrix hess=new Matrix(dim,dim);
		for(int i=0;i<dim;i++){
			for(int j=0;j<dim;j++){
				hess.set(i, j, hessAtPosition(i,j,value));
			}
		}
		return hess;
	}

	/**
	 * Calculates the element of a Hessian Matrix.
	 * @param i i-th row ot the HessianMatrix.
	 * @param j j-th column of the HessianMatrix.
	 * @param value Position in which we analyse the matrix.
	 * @return The element of Hessian Matrix.
	 */
	private double hessAtPosition(int i, int j, Matrix value) {
		double sum=0;
		for(int z=0;z<dim;z++){
			sum+=2*coefficients[z][j]*coefficients[z][i];
		}
		return sum;
	}
}
