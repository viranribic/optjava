package hr.fer.zemris.optjava.dz5.part1.function;

import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

/**
 * Modified Max-Ones problem.
 * @author Viran
 *
 */
public class ModifiedMaxOnes {

	/**
	 * Calculate fitness value for this solution.
	 * @param solution Solution in need of evaluation.
	 * @return Solution fitness.
	 */
	public double valueAt(BitvectorSolution solution) {
		boolean[] solutionVector=solution.getVector();
		int k=0;
		for(boolean b:solutionVector)
			if(b)
				k++;
		int n=solution.getVectorSize();
		double f=0;
		if(k<=0.8*n){
			f=(double)k/n;
		}else if(k<=0.9*n && k>=0.8*n){
			f= 0.8;
		}else{
			f=(2.*k/n)-1;
		}
		return f;
	}

}
