package hr.fer.zemris.optjava.dz13.test;

import hr.fer.zemris.optjava.dz13.algorithm.operators.Evaluation;
import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.util.GPPopulation;
import hr.fer.zemris.optjava.dz13.util.InitGPPopulation;

public class EvaluationTest {

	
	public static void main(String[] args) {
		GPPopulation population=InitGPPopulation.genInitialPopulation(1, 6, 10);
		GPSolution solution=population.get(0);
		Evaluation evaluation=new Evaluation("13-SantaFeAntTrail.txt");
		evaluation.evaluation(solution);
		System.out.println(solution+"\n---\n"+solution.getRoot());
	}
}
