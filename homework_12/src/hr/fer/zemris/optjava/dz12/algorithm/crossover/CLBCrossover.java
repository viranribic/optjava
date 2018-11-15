package hr.fer.zemris.optjava.dz12.algorithm.crossover;

import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.interfaces.ICrossover;
import hr.fer.zemris.optjava.dz12.util.random.RNG;

public class CLBCrossover implements ICrossover {

	@Override
	public CLBSolution crossover(CLBSolution parentA, CLBSolution parentB) {
		int clbInputs=parentA.getCLBInputs();
		int numberOfCLBs=parentA.getNumberOfCLBs();
		CLBSolution child=new CLBSolution(clbInputs, numberOfCLBs);
		int xOverPos=RNG.getRNG().nextInt(0, numberOfCLBs);
		
		for(int i=0;i<numberOfCLBs;i++){
			if(i<xOverPos){
				child.setCLB(i, parentA.getCLB(i));
			}else{
				child.setCLB(i, parentB.getCLB(i));
			}
		}
		return child;
	}

}
