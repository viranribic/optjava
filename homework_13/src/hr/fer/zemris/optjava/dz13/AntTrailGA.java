package hr.fer.zemris.optjava.dz13;

import hr.fer.zemris.optjava.dz13.algorithm.GPAlgorithm;
import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;

public class AntTrailGA {

	
	public static void main(String[] args) {
		if(args.length!=5){
			System.out.println("Neispravan broj ulaznih parametara. Potrebno je pružiti iduće parametre\n"+
								"\tPutanja do datoteke s mapom\n"+
									"\tMaksimalan broj generacija koji je dozvoljen\n"+
										"\tVeličina populacije s kojom se radi\n"+
											"\tMinimalna dobrota\n"+
												"\tPutanja do izlazne datoteke\n");
			return;
		}
		
		String mapPath=args[0];
		int maxGeneration=Integer.parseInt(args[1]);
		int popSize=Integer.parseInt(args[2]);
		int minimalFit=Integer.parseInt(args[3]);
		String outProgPath=args[4];
		
		GPAlgorithm algorithm=new GPAlgorithm(mapPath,maxGeneration,popSize,minimalFit);
		GPSolution bestFoundSol=algorithm.run();
		System.out.println("Best solution found:"+bestFoundSol);
		System.out.println(bestFoundSol.getRoot());
		writeToFile(outProgPath,bestFoundSol);
		runSimulation(mapPath,bestFoundSol);
	}

	private static void writeToFile(String outProgPath, GPSolution bestFoundSol) {
		ProgramWriter pw=new ProgramWriter(outProgPath,bestFoundSol);
		pw.run();
		
	}

	private static void runSimulation(String mapPath,GPSolution solution) {
		 
		Simulation sim=new Simulation(mapPath, solution);
		sim.run();
	}
	
	
}
