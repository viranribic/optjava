package hr.fer.zemris.optjava.dz8.algorithm.operators.crossover;

import java.util.Random;

import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;
import hr.fer.zemris.optjava.dz8.interfaces.ICrossover;

public class UniformCrossover implements ICrossover {

private double C;
private Random rand;
	
	public UniformCrossover(double c,Random rand) {
		this.C=c;
		this.rand=rand;
	}
	@Override
	public DifEvSolution crossover(DifEvSolution mutantVector, DifEvSolution targetVector) {
		DifEvSolution xoverVector=new DifEvSolution(targetVector.getDimension());
		int sureIndex=rand.nextInt(mutantVector.getDimension());
		double[] xoverVec=xoverVector.getSolution();
		double[] mutantVec=mutantVector.getSolution();
		double[] targetVec=targetVector.getSolution();
		
		xoverVec[sureIndex]=mutantVec[sureIndex];
		
		for(int i=1;i<xoverVector.getDimension()-1;i++){
			int position=(sureIndex+i)%xoverVector.getDimension();
			double probability=rand.nextDouble();
			
			if(probability<C){
				xoverVec[position]= mutantVec[position];
			}else{
				xoverVec[position]= targetVec[position];
			}
		}
		
		return xoverVector;
	}

}
