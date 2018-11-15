package hr.fer.zemris.optjava.dz4.part1.geneticAlgorithms;

import hr.fer.zemris.optjava.dz4.part1.constants.ConstantsTask1;
import hr.fer.zemris.optjava.dz4.part1.geneticAlgorithms.population.ElitePopulationGA;
import hr.fer.zemris.optjava.dz4.part1.geneticOperatos.crossoverOperators.BLXAlphaOperator;
import hr.fer.zemris.optjava.dz4.part1.geneticOperatos.mutationOperators.GaussDistMutation;
import hr.fer.zemris.optjava.dz4.part1.interfaces.IFunction;
import hr.fer.zemris.optjava.dz4.part1.interfaces.IOptAlgorithm;
import hr.fer.zemris.optjava.dz4.part1.interfaces.ISelection;
import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

/**
 * Generation elitistic genetic algorithm.
 * @author Viran
 *
 */
public class GenerationElitisticGA implements IOptAlgorithm {

	//Best run (alpha=0.5, eliteSubjects=2, populationSize=100 error=0.001 iterations=10000 type=rouletteWheel sigma=0.01) ->	(  13.617 , -2.358 ,  5.354 ,  0.009 , -6.708 ,  3.121  ) Fitness: (  0.001 )
	//Algorithm general attributes
	private int populationSize;
	private double minError;
	private int maxIterations;
	private double sigma;
	private IFunction function;

	//Population attributes
	private ElitePopulationGA population;
	private int domainDim;
	
	//Genetic operators
	private ISelection selectionOp;
	private BLXAlphaOperator crossoverOp;
	private GaussDistMutation mutationOp;
	
	/**
	 * GenerationElitisticGA constructor.
	 * @param populationSize Size of population.
	 * @param minError Minimal error at which the algorithm can stop.
	 * @param maxIterations Maximal number of iterations.
	 * @param selection Selection operator.
	 * @param sigma Mutation parameter.
	 * @param function Function we optimise.
	 * @param domainDim Dimension of the domain we are solving.
	 */
	public GenerationElitisticGA(int populationSize, double minError, int maxIterations, ISelection selection, double sigma, IFunction function, int domainDim) {
		this.populationSize=populationSize;
		this.minError=minError;
		this.maxIterations=maxIterations;
		this.selectionOp=selection;
		this.sigma=sigma;
		this.domainDim=domainDim;
		this.function=function;
	
		//specific classes:
		this.crossoverOp=new BLXAlphaOperator();
		this.mutationOp=new GaussDistMutation();
	}
	
	@Override
	public void run() {
		
		population=new ElitePopulationGA(populationSize,domainDim, ConstantsTask1.MIN_COMPONENT_VALS, ConstantsTask1.MAX_COMPONENT_VALS, ConstantsTask1.NUMBER_OF_ELITE_SUBJECTS);
		evaluatePopulation(population);
		int iteration=maxIterations;
		DoubleArraySolution[] nextPopulation;
		int nextPopulationSubject;	//index of next population element
		
		DoubleArraySolution parentA;
		DoubleArraySolution parentB;
		DoubleArraySolution child;
		
		while(iteration-- >0){
			nextPopulation=new DoubleArraySolution[populationSize];
			nextPopulationSubject=0;
			nextPopulationSubject=insertEliteSubjects(population,nextPopulation,nextPopulationSubject);
			selectionOp.setPopulation(population);
			int abortions=0;
			double dynamicSigma=sigma;
			double dynamicAlpha=ConstantsTask1.ALPHA;
			
			while(nextPopulationSubject<populationSize){
//				if(abortions>10){
//					dynamicAlpha*=1.001;
//					dynamicSigma*=1.0001;
//				}
				parentA=selectionOp.selectRandomSubject();
				parentB=selectionOp.selectRandomSubject();
				child=crossoverOp.crossover(parentA, parentB, dynamicAlpha);
				child=mutationOp.mutation(child, dynamicSigma);
				evaluateSubject(child);
				if(abortions<100)
					if(child.fitness>parentA.fitness  ||  child.fitness>parentB.fitness){
					abortions++;
					continue;
				}
				//reset these constant for next iteration
//				abortions=0;
//				dynamicSigma=sigma;
//				dynamicAlpha=Constants.ALPHA;
				nextPopulationSubject=insertChildSubject(nextPopulation,child,nextPopulationSubject);
			}
			population=new ElitePopulationGA(nextPopulation, populationSize);
			evaluatePopulation(population);
			population.sortThisPopulation();
			DoubleArraySolution best=population.getSubjectAtIndex(0);
			printSubject(best);
			if(best.fitness<=minError){
				break;
			}
		}
		//end of run();
		
	}

	/**
	 * Print the given subject to standard output.
	 * @param subjectAtIndex Subject to be printed.
	 */
	private void printSubject(DoubleArraySolution subjectAtIndex) {
		StringBuffer sb=new StringBuffer();
		sb.append("( ");
		for(int i=0;i<domainDim;i++){
			if(i==0)
				sb.append(String.format(" %6.3f ", subjectAtIndex.values[i]));
			else
				sb.append(String.format(", %6.3f ", subjectAtIndex.values[i]));
			
		}
		sb.append(" ) Fitness: ");
		sb.append(String.format("( %6.3f )", subjectAtIndex.fitness));
		System.out.println(sb.toString());
		
	}

	/**
	 * Insert the child to population.
	 * @param nextPopulation Next population generation.
	 * @param child New member of population.
	 * @param nextPopulationSubject Index of the next subject for insertion.
	 * @return New index of the next subject in need of population insertion.
	 */
	private int insertChildSubject(DoubleArraySolution[] nextPopulation,DoubleArraySolution child, int nextPopulationSubject) {
		nextPopulation[nextPopulationSubject++]=child.duplicate();
		return nextPopulationSubject;
	}

	/**
	 * Insert the best subject from given population to next generation.
	 * @param population Previous population generation.
	 * @param nextPopulation Next population generation.
	 * @param nextPopulationSubject Index of the next subject for insertion.
	 * @return New index of the next subject in need of population insertion.
	 */
	private int insertEliteSubjects(ElitePopulationGA population, DoubleArraySolution[] nextPopulation, int nextPopulationSubject) {
		population.sortThisPopulation();
		int elite=population.getNumberOfEliteSubjects();
		for(int i=0;i<elite;i++){
			nextPopulation[nextPopulationSubject++]=population.getSubjectAtIndex(i).duplicate();
		}
		return nextPopulationSubject;
	}

	/**
	 * For a given population, update the fitness values.
	 * @param population Population in need of evaluation.
	 */
	private void evaluatePopulation(ElitePopulationGA population) {
		for(int i=0;i<populationSize;i++){
			DoubleArraySolution subject=population.getSubjectAtIndex(i);
			evaluateSubject(subject);
			}
	}

	/**
	 * Calculate fitness for one subject.
	 * @param subject Subject in need of evaluation.
	 */
	private void evaluateSubject(DoubleArraySolution subject){
		subject.fitness=function.valueAt(subject.values);
	}
}
