package hr.fer.zemris.optjava.dz5.part2.algorithm.population;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part2.function.QAPFunction;
import hr.fer.zemris.optjava.dz5.part2.solution.QAPSolution;

/**
 * The Self-Adaptive Segregative Genetic Algorithm with Simulated Annealing
 * aspects population.
 * @author Viran
 *
 */
public class SASEGASAPopulation {

	private Random rand=new Random();
	private int totalPopSize;
	private int numberOfVillages;
	private LinkedList<QAPSolution> qapSolutions;
	private boolean[] convergedPopulatiosn;
	private int[] subPopIndexMarkers;
	
	int lowerPopIndexBound=-1;
	int upperPopIndexBound=-1;
	
	/**
	 * SESEGASAPopulation constructor.
	 * @param totalPopSize Total number of population subjects.
	 * @param numberOfVillages Number of subpopulations.
	 * @param function Fitness function for subject evaluation.
	 * @throws IllegalAccessException If the number of subpopulations can not be equally assigned from the total population size.
	 */
	public SASEGASAPopulation(int totalPopSize, int numberOfVillages, QAPFunction function) throws IllegalAccessException {
		if(totalPopSize%numberOfVillages!=0)
			throw new IllegalAccessException("Total population size must be devisible with subpopulation size.");
		this.totalPopSize=totalPopSize;
		this.numberOfVillages=numberOfVillages;
		this.qapSolutions=new LinkedList<>();
		this.convergedPopulatiosn=new boolean[numberOfVillages];
		setIndexMarkers();
		initThisPopulation(function);
	}

	/**
	 * Based in the total size and number of subpopulations in it,<br>update the subpopulation index markers.
	 */
	private void setIndexMarkers() {
		subPopIndexMarkers=new int[numberOfVillages+1];
		int subPopSize=(int)(totalPopSize/numberOfVillages);
		for(int i=0;i<subPopIndexMarkers.length;i++){
			subPopIndexMarkers[i]=i*subPopSize;
		}
		
	}

	/**
	 * Initialise this population.
	 * @param function Function for assigning particular fitness values.
	 */
	public void initThisPopulation(QAPFunction function) {
		for(int i=0;i<totalPopSize;i++){
			
			LinkedList<Integer> factoryLocations=new LinkedList<>();
			for(int j=0;j<function.getNumberOfFactories();j++)
				factoryLocations.add(j);
			Collections.shuffle(factoryLocations, rand);
			
			QAPSolution nextSol=new QAPSolution(factoryLocations);
			nextSol.setFitness(function.valueAt(nextSol));
			
			this.qapSolutions.add(nextSol);
		}
		
	}

	/**
	 * Decrease total subpopulation count.
	 */
	public void decreseVillageNum() {
		numberOfVillages--;
		reEvaluateSubPopulations();
	}

	/**
	 * Make necessary adjustments after the destruction of a random population village.
	 */
	private void reEvaluateSubPopulations() {
		this.convergedPopulatiosn=new boolean[numberOfVillages];
		setIndexMarkers();
	}

	/**
	 * Check weather all subpopulations(villages) of this population converged to a particular value.
	 * @return True if the subpopulations converged, false otherwise.
	 */
	public boolean allSubPopConverged() {
		for(Boolean b:convergedPopulatiosn)
			if(!b)
				return false;
		return true;
	}

	/**
	 * Get current number of villages in this population.
	 * @return Number of villages.
	 */
	public int numberOfVillages() {
		return numberOfVillages;
	}

	/**
	 * Extrude a copy of the population subjects in a sublist format.<br> Changes to the given list do not affect this population.
	 * @param currentVillage Index of the village we want to extrude.
	 * @return Subpopulation list.
	 */
	public LinkedList<QAPSolution> extrudeSubPopulation(int currentVillage) {
		if(currentVillage>=numberOfVillages || currentVillage<0)
			throw new IllegalArgumentException("SASEGASAPopulation-Tried to reach an unreachable population.");
		if(lowerPopIndexBound!=-1 && upperPopIndexBound!=-1)
			throw new RuntimeException("Unable to get the subpopulation requested. Another subpopulation evaluation is in progress.");
		lowerPopIndexBound=this.subPopIndexMarkers[currentVillage];
		upperPopIndexBound=this.subPopIndexMarkers[currentVillage+1];
		return new LinkedList<QAPSolution>(qapSolutions.subList(lowerPopIndexBound, upperPopIndexBound));
	}

	/**
	 * Insert the given population back to this superpopulation.
	 * @param currentVillage Subpopulation we are inserting back.
	 * @param subPop Subpopulation elements.
	 * @param convergenceStatus Status weather this subpopulation has converged yet.
	 */
	public void insertSubPopulation(int currentVillage, LinkedList<QAPSolution> subPop, boolean convergenceStatus) {
		int numberOfElements=upperPopIndexBound-lowerPopIndexBound; //excluded
		if(numberOfElements!=subPop.size())
			throw new RuntimeException("SASEGASAPopulaiton-Subpopulation given is not valid.");
		
		for(int i=0;i<numberOfElements;i++)
			qapSolutions.remove(lowerPopIndexBound);
		qapSolutions.addAll(lowerPopIndexBound, subPop);
		
		this.convergedPopulatiosn[currentVillage]=convergenceStatus;
		
		lowerPopIndexBound=-1;
		upperPopIndexBound=-1;
		
	}

	/**
	 * Get best fitness subjects in subpopulations.
	 * @return Best subject by fitness.
	 */
	public QAPSolution[] bestFitnessPerSubpop(){
		QAPSolution[] topFitnesses=new QAPSolution[numberOfVillages];
		//start extracting
		for(int currentVillage=0;currentVillage<numberOfVillages;currentVillage++){
			double bestFitOfVillage=0;
			QAPSolution bestSubjOfVillage=null;
			
			int firstVillIndex=this.subPopIndexMarkers[currentVillage];
			int lastVillIndex=this.subPopIndexMarkers[currentVillage+1];
			
			int subPopSize=lastVillIndex-firstVillIndex;
			
			//for all elements per village
			for(int subject=firstVillIndex;subject<firstVillIndex+subPopSize;subject++){
				if(subject==firstVillIndex){	//init for the first subject
					bestSubjOfVillage=qapSolutions.get(subject);
					bestFitOfVillage=bestSubjOfVillage.getFitness();
					
				}else{
					if(bestFitOfVillage>qapSolutions.get(subject).getFitness()){ //if other subject fit is smaller
						bestSubjOfVillage=qapSolutions.get(subject);
						bestFitOfVillage=bestSubjOfVillage.getFitness();
						
					}
				}
			}
			
			topFitnesses[currentVillage]=bestSubjOfVillage.duplicate();
		}
		return topFitnesses;
 	}
	
}
