package hr.fer.zemris.optjava.dz8.algorithm.operators.mutation;

import java.util.Random;

import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;
import hr.fer.zemris.optjava.dz8.util.Constants;

public class ExtendedDifMutation extends SimpleDifMutation {
	
	public ExtendedDifMutation(Random rand) {
		super(rand);
	}	

	@Override
	protected DifEvSolution multiply(DifEvSolution diference, double F) {
		DifEvSolution dif=new DifEvSolution(diference.getDimension());
		double[] differenceVecotr=diference.getSolution();
		double[] difVecotr=dif.getSolution();
		double fSpec;
		for(int i=0;i<difVecotr.length;i++){
			fSpec=F+Constants.F_OSCILATIONS*(rand.nextDouble()-0.5);
			difVecotr[i]=differenceVecotr[i]*fSpec;
		}
		return dif;
	}
}
