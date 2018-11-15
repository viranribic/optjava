package hr.fer.zemris.optjava.dz10.function.fitnessFunction;

import java.util.LinkedList;


import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Population;
import hr.fer.zemris.optjava.dz10.algorithm.NSGA2Solution;
import hr.fer.zemris.optjava.dz10.function.util.NSGA2DimComparator;
import hr.fer.zemris.optjava.dz10.function.util.NSGA2DistComparator;
import hr.fer.zemris.optjava.dz10.interfaces.IMOOPOptFunction;
import hr.fer.zemris.optjava.dz10.interfaces.MOOPProblem;
import hr.fer.zemris.optjava.dz10.util.Constants;

/**
 * FitnessFunction for MOOP task.
 * 
 * @author Viran
 *
 */
public abstract class FitnessFunction implements IMOOPOptFunction {

	protected MOOPProblem moopProblem;
	protected LinkedList<LinkedList<Integer>> dominatingOnThis;
	protected int[] dominatedByThis;
	protected LinkedList<LinkedList<Integer>> paretoFronts;

	/**
	 * One matrix representing distances, sharing function values and density
	 * values.
	 */
	protected double[][] subjectRelations;

	/**
	 * FitnessFunction constructor.
	 * 
	 * @param moopProblem
	 *            Problem we are trying to solve.
	 */
	public FitnessFunction(MOOPProblem moopProblem) {
		this.moopProblem = moopProblem;
	}

	@Override
	public int getDecisionSpaceDim() {
		return moopProblem.getDecisionSpaceDim();
	}

	@Override
	public int getObjectiveSpaceDim() {
		return moopProblem.getObjectiveSpaceDim();
	}

	@Override
	public double[] getMinDomainVals() {
		return moopProblem.getMinDomainVals();
	}

	@Override
	public double[] getMaxDomainVals() {
		return moopProblem.getMaxDomainVals();
	}

	@Override
	public void evaluatePopulation(LinkedList<NSGA2Solution> population, double alpha, double sigma) {
		for (NSGA2Solution solution : population) {
			double[] objectives = new double[moopProblem.getObjectiveSpaceDim()];
			moopProblem.evaluateSolution(solution.getValuesVector(), objectives);
			solution.setObjSolValues(objectives);
		}
		extendedNonDominantSorting(population, alpha, sigma);
		calculateCrowdingDistances(population);
		
		int frontC=1;
		if (Constants.PRINT_TO_SCREEN)
			for (LinkedList<Integer> front : paretoFronts) {
				System.out.print("Front " + (frontC++) + " : \n\t");
				for (int i = 0; i < front.size(); i++) {
					System.out.print("#");
					//System.out.println("\tS:"+front.get(i)+"\n"+population.get(front.get(i)));
				}
				System.out.println();
			}
		System.out.println();
	}

	/**
	 * Calculate the niche count for every solution.
	 * 
	 * @param subPopulation
	 *            Population of solutions.
	 * @param alpha
	 *            Alpha parameter.
	 * @param sigma
	 *            Sigma parameter.
	 * @return Array of niche count values per solution.
	 */
	private double[] getNcForSubPop(LinkedList<NSGA2Solution> subPopulation, double alpha, double sigma) {
		calculateDistance(subPopulation);
		calculateSharingValues(subPopulation.size(), alpha, sigma);

		double[] nc = new double[subPopulation.size()];

		for (int i = 0; i < subPopulation.size(); i++)
			for (int j = 0; j < subPopulation.size(); j++)
				nc[i] += subjectRelations[i][j];

		int i = 0;
		for (NSGA2Solution solution : subPopulation) {
			solution.setFit(solution.getFit() / nc[i++]);
		}
		return nc;
	}

	/**
	 * Calculate shared values for every population subject.
	 * 
	 * @param populationSize
	 *            Number of subjects in population.
	 * @param alpha
	 *            Alpha parameter.
	 * @param sigma
	 *            Sigma parameter.
	 */
	protected void calculateSharingValues(int populationSize, double alpha, double sigma) {
		for (int i = 0; i < populationSize; i++)
			for (int j = 0; j < populationSize; j++) {
				if (subjectRelations[i][j] < sigma) {
					double tmp = subjectRelations[i][j] / sigma;
					tmp = Math.pow(tmp, alpha);
					subjectRelations[i][j] = 1 - tmp;
				} else {
					subjectRelations[i][j] = 0;
				}
			}
	}

	/**
	 * Execute non dominant sorting algorithm and assign proper values to
	 * population subjects.
	 * This extension writes to solution its rank and distance values.
	 * @param population
	 *            Population subjects.
	 * @param alpha
	 *            Alpha parameter.
	 * @param sigma
	 *            Sigma parameter.
	 */
	public void extendedNonDominantSorting(LinkedList<NSGA2Solution> population, double alpha, double sigma) {
		paretoFronts = new LinkedList<>();

		dominatedByThis = new int[population.size()];
		dominatingOnThis = new LinkedList<>();
		LinkedList<Integer> currentParetoFront = new LinkedList<>();

		solveDominationRelationships(population, currentParetoFront);
		
		generateFronts(currentParetoFront);

//		int frontC = 1;
//		if (Constants.PRINT_TO_SCREEN)
//			for (LinkedList<Integer> front : paretoFronts) {
//				System.out.print("Front " + (frontC++) + " : \n");
//				for (int i = 0; i < front.size(); i++) {
//					//System.out.print("#");
//					System.out.println("\tS:"+front.get(i)+"\n"+population.get(front.get(i)));
//				}
//				System.out.println();
//			}

		double[] niceCount = getNcForSubPop(population, alpha, sigma);

		double currN = population.size();

		for (LinkedList<Integer> frontElements : paretoFronts) {
			double minCurrN = 0;
			for (int i = 0; i < frontElements.size(); i++) {
				int pos = frontElements.get(i);
				NSGA2Solution solution = population.get(pos);
				double fScaled = currN / niceCount[pos];
				if (i == 0)
					minCurrN = fScaled;
				else if (minCurrN > fScaled)
					minCurrN = fScaled;
				solution.setFit(fScaled);
			}
			currN = minCurrN - Constants.SOMETHING_SMALL;
		}

		//System.out.println();
	}

	/**
	 * Generate Pareto fronts taking into consideration the current front.
	 * @param currentParetoFront Starting front.
	 */
	private void generateFronts(LinkedList<Integer> currentParetoFront) {
		int frontCounter = 0;
		paretoFronts.add(frontCounter, currentParetoFront);

		while (true) {
			if (currentParetoFront.size() == 0) {
				break;
			}
			LinkedList<Integer> qList = new LinkedList<>();
			for (int i = 0; i < currentParetoFront.size(); i++) {
				LinkedList<Integer> currentsSolutionSet = dominatingOnThis.get(currentParetoFront.get(i));
				for (int j = 0; j < currentsSolutionSet.size(); j++) {
					int workingIndex = currentsSolutionSet.get(j);
					dominatedByThis[workingIndex]--;
					if (dominatedByThis[workingIndex] == 0) {
						qList.add(workingIndex);
					}
				}
			}

			currentParetoFront = qList;
			frontCounter++;
			paretoFronts.add(frontCounter, currentParetoFront);
		}
		paretoFronts.removeLast();
	}

	/**
	 * For the given population make dominatingOnThis and dominatedByThis lists. 
	 * @param population Population on which the evaluation takes place.
	 * @param currentParetoFront Starting Pareto front of the population. 
	 */
	private void solveDominationRelationships(LinkedList<NSGA2Solution> population,
			LinkedList<Integer> currentParetoFront) {
		for (int firstSubjectIndex = 0; firstSubjectIndex < population.size(); firstSubjectIndex++) {
			NSGA2Solution sol1 = population.get(firstSubjectIndex);
			dominatedByThis[firstSubjectIndex] = 0;
			LinkedList<Integer> sol1Dominated = new LinkedList<>();
			dominatingOnThis.add(sol1Dominated);

			for (int secondSubjectIndex = 0; secondSubjectIndex < population.size(); secondSubjectIndex++) {

				if (secondSubjectIndex == firstSubjectIndex) {
					continue;
				}

				NSGA2Solution sol2 = population.get(secondSubjectIndex);

				if (sol1.dominates(sol2)) {
					sol1Dominated.add(secondSubjectIndex);
					//System.out.println("Solution at index "+firstSubjectIndex+" dominates over solution at index "+secondSubjectIndex+".");
				} else if (sol2.dominates(sol1)) {
					dominatedByThis[firstSubjectIndex]++;
					//System.out.println("Solution at index "+firstSubjectIndex+" dominates over solution at index "+secondSubjectIndex+".");
				}
			}
			if (dominatedByThis[firstSubjectIndex] == 0) {
				currentParetoFront.add(firstSubjectIndex);
			}
			sol1.setNonDominationRank(dominatedByThis[firstSubjectIndex]);
		}
	}

	/**
	 * Determinate non dominant set of population subjects
	 * 
	 * @param population
	 *            List od subjects.
	 * @return Non dominated subjects list.
	 */
	public LinkedList<NSGA2Solution> determinateNonDominantSet(LinkedList<NSGA2Solution> population) {
		LinkedList<NSGA2Solution> nonDominantSet = new LinkedList<>();
		for (int i = 0; i < population.size(); i++) {
			NSGA2Solution solutionOne = population.get(i);
			boolean dominated = false;
			for (int j = 0; j < population.size(); j++) {
				NSGA2Solution solutionTwo = population.get(j);
				if (solutionTwo.dominates(solutionOne)) {
					dominated = true;
					break;
				}
			}
			if (!dominated)
				nonDominantSet.add(solutionOne);
		}
		return nonDominantSet;
	}

	@Override
	public MOOPProblem getMOOPProblem() {
		return moopProblem;
	}

	@Override
	public LinkedList<LinkedList<NSGA2Solution>> getParetoFronts(LinkedList<NSGA2Solution> population) {
		LinkedList<LinkedList<NSGA2Solution>> paretoFrontsSubjects = new LinkedList<>();

		for (LinkedList<Integer> front : paretoFronts) {
			LinkedList<NSGA2Solution> subjects = new LinkedList<>();
			for (Integer pos : front) {
				subjects.add(population.get(pos));
			}
			paretoFrontsSubjects.add(subjects);
		}

		return paretoFrontsSubjects;
	}
	
	@Override
	public NSGA2Population generateNextPopulation(LinkedList<NSGA2Solution> combinedPopulations,
			double alpha, double sigma) {
		
		
		this.evaluatePopulation(combinedPopulations, alpha, sigma);
		
		NSGA2Solution[] nextPopulation=new NSGA2Solution[(int)(combinedPopulations.size()/2)];
		int nextPopCount=0;
		int frontIndex=0;
		while(nextPopCount<nextPopulation.length){
			if(paretoFronts.get(frontIndex).size()+nextPopCount<=nextPopulation.length){
				//add the whole population
				LinkedList<Integer> frontSolIndex=paretoFronts.get(frontIndex);
				for(Integer posIndex:frontSolIndex){
					nextPopulation[nextPopCount++]=combinedPopulations.get(posIndex).duplicate();
				}
			}else{
				//get to special case
				NSGA2Solution[] elementsToAdd=selectFromLastFront(frontIndex,nextPopulation.length-nextPopCount,combinedPopulations);
				for(int i=0;i<elementsToAdd.length;i++){
					nextPopulation[nextPopCount++]=elementsToAdd[i];
				}
				//after this last step exit and return the population.
				break;
			}
			
			frontIndex++;
		}
		
		return new NSGA2Population(nextPopulation);
	}

	/**
	 * calculate crowding distances between population subjects.
	 * @param combinedPopulations Population in need of evaluation.
	 */
	private void calculateCrowdingDistances(LinkedList<NSGA2Solution> combinedPopulations) {
		
		//for every solution in front F
		for(LinkedList<Integer> front:paretoFronts){
			// make a solution list from index list
			LinkedList<NSGA2Solution> frontSolutions=new LinkedList<>();
			for(Integer index:front){
				NSGA2Solution sol=combinedPopulations.get(index);
				sol.setCrowdingDistance(0);	//reset the distance to 0.
				frontSolutions.add(sol);
					
			}
			
			//for every dimension 
			int objectiveDimensionNum=combinedPopulations.getFirst().getObjectiveDimension();
			for(int dim=0;dim<objectiveDimensionNum;dim++){
				//get minimal and maximal value per dimension.
				double fMin=moopProblem.getMinDomainVals()[dim];
				double fMax=moopProblem.getMaxDomainVals()[dim];
				
				//sort per every dimension
				frontSolutions.sort(new NSGA2DimComparator(dim));
				
				double maxDistance=0;
				//za ostale: kreni po riješenjima 
				for(int solIndex=1;solIndex<frontSolutions.size()-1;solIndex++){
					NSGA2Solution curSolution=frontSolutions.get(solIndex);
					double d=curSolution.getCrowdingDistance();
					NSGA2Solution prevSolution=frontSolutions.get(solIndex-1);
					NSGA2Solution nextSolution=frontSolutions.get(solIndex+1);
					
					double result=(nextSolution.getObjSolValues()[dim]-prevSolution.getObjSolValues()[dim])/(fMax-fMin);
					d+=result;
					curSolution.setCrowdingDistance(d);
					if(solIndex==1){
						maxDistance=result;
					}else if(maxDistance<result)
						maxDistance=result;
				}
				//first and last elements have the greatest distance possible
				NSGA2Solution firstSolution=frontSolutions.getFirst();
				NSGA2Solution lastSolution=frontSolutions.getLast();
				//firstSolution.setCrowdingDistance(firstSolution.getCrowdingDistance()+4*maxDistance);	//instead of Double.POSITIVE_INFINITY
				//lastSolution.setCrowdingDistance(lastSolution.getCrowdingDistance()+4*maxDistance);
				firstSolution.setCrowdingDistance(Double.POSITIVE_INFINITY); //TODO 
				lastSolution.setCrowdingDistance(Double.POSITIVE_INFINITY); //TODO
			}
			
		}
		
		
	}

	/**
	 * From the final pareto front select the specified number of elements to complete the population.
	 * @param frontIndex Get the last front index.
	 * @param numOfSubj Number of solution we need to select to complete the population.
	 * @return Selected elements array.
	 */
	private NSGA2Solution[] selectFromLastFront(int frontIndex, int numOfSubj, LinkedList<NSGA2Solution> combinedPopulations) {
		NSGA2Solution[] elementsToAdd=new NSGA2Solution[numOfSubj];
		
		//take the given front
		LinkedList<Integer> workingFront=paretoFronts.get(frontIndex);
		LinkedList<NSGA2Solution> solutions=new LinkedList<>();
		for(Integer index:workingFront)
			solutions.add(combinedPopulations.get(index));
		//copy the population into new list
		//sort the list by distance ascending
		solutions.sort(new NSGA2DistComparator());
		
		// add the elements from the end
		int solPosition=solutions.size()-1;
		for(int i=0;i<elementsToAdd.length;i++){
			elementsToAdd[i]=solutions.get(solPosition--);
		}
		return elementsToAdd;
	}

	/**
	 * Calculate distance between population elements.
	 * 
	 * @param population
	 *            List of elements whose distances we need to find.
	 */
	abstract protected void calculateDistance(LinkedList<NSGA2Solution> population);
}
