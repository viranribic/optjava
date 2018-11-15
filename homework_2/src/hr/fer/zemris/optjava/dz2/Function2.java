package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

/**
 * Function described as:
 * f1 ( x1, x2 ) = ( x1 - 1 ) ^ 2 + 10 * (x2 - 2) ^ 2.
 * @author Viran
 *
 */
public class Function2 implements IHFunction {


	private static final int DOMAIN_DIM = 2;
	
	@Override
	public int domainDimension() {
		return DOMAIN_DIM;
	}

	@Override
	public double functionCalc(Matrix value) {
		if(value.getColumnDimension()!=1 || value.getRowDimension()!=DOMAIN_DIM)
			throw new IllegalArgumentException("Given value verctor is not the right dimension.");
		double f,x1=value.get(0, 0),x2=value.get(1, 0);
		
		f=Math.pow(x1-1, 2)+10*Math.pow(x2-2, 2);
		
		return f;
	}

	@Override
	public Matrix gradCalc(Matrix value) {
		if(value.getColumnDimension()!=1 || value.getRowDimension()!=DOMAIN_DIM)
			throw new IllegalArgumentException("Given value verctor is not the right dimension.");
		double x1=value.get(0, 0),x2=value.get(1, 0);
		Matrix g=new Matrix(DOMAIN_DIM,1);
		g.set(0, 0, 2*x1-2);
		g.set(1, 0, 20*x2-40);
		return g;
	}

	@Override
	public Matrix HessianMatrix(Matrix value) {
		if(value.getColumnDimension()!=1 || value.getRowDimension()!=DOMAIN_DIM)
			throw new IllegalArgumentException("Given value verctor is not the right dimension.");
		
		@SuppressWarnings("unused")
		double x1=value.get(0, 0),x2=value.get(1, 0);	//I guess i don't need them for this function
		
		Matrix hesMat=new Matrix(DOMAIN_DIM, DOMAIN_DIM);
		hesMat.set(0, 0, 2);
		hesMat.set(0, 1, 0);
		hesMat.set(1, 0, 0);
		hesMat.set(1, 1, 20);
		return hesMat;
	}

}
