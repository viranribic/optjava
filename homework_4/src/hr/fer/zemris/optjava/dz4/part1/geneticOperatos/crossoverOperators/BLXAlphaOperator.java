package hr.fer.zemris.optjava.dz4.part1.geneticOperatos.crossoverOperators;

import java.util.Random;

import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

/**
 * Class offering crossover operation as used in genetic algorithms.
 * This approach takes two parents and produces one offspring.
 * For more info visit page: <a href="http://www.tomaszgwiazda.com/blendX.htm"> BLX crossover.</a>
 * @author Viran
 *
 */
public class BLXAlphaOperator {

	private static Random rand=new Random();
	
	/**
	 * For the given parents perform a BLX-alpha crossover. 
	 * @param parentA First parent solution.
	 * @param parentB Second parent solution.
	 * @param alpha Alpha coefficient of algorithm.
	 * @return Offspring of parentA and parentB.
	 */
	public DoubleArraySolution crossover(DoubleArraySolution parentA, DoubleArraySolution parentB,double alpha){
		int solutionDim=parentA.getDimension();
		DoubleArraySolution offspring=parentA.newLikeThis();
		double cMin;
		double cMax;
		double I;
		double upperB;
		double lowerB;
		
		for(int i=0;i<solutionDim;i++){
			cMin=Math.min(parentA.values[i], parentB.values[i]);
			cMax=Math.max(parentA.values[i], parentB.values[i]);
			I=cMax-cMin;
			upperB=cMax+I*alpha;
			lowerB=cMin-I*alpha;
			//offspring.values[i]= unif( cMin-I*alpha, cMax+I*alpha);
			offspring.values[i]=rand.nextDouble()*(upperB-lowerB)+lowerB;
		}
		
		return offspring;
	}
	
}
