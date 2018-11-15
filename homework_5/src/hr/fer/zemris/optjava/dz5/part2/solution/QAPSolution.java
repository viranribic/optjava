package hr.fer.zemris.optjava.dz5.part2.solution;

import java.util.Arrays;
import java.util.LinkedList;


/**
 * Quadratic assignment problem solution representation.
 * @author Viran
 *
 */
public class QAPSolution implements Comparable<QAPSolution>{
	private int[] factoryLocations;
	private double fitness;
	/**
	 * QAPSolution constructor.
	 * @param factoryLocations Initialise by locations array.
	 */
	public QAPSolution(int[] factoryLocations) {
		this.factoryLocations=factoryLocations.clone();
	}
	
	/**
	 * QAPSolution constructor.
	 * @param numberOfFactories Initialise by number of locations/factories.
	 */
	public QAPSolution(int numberOfFactories){
		this.factoryLocations=new int[numberOfFactories];
	}
	
	/**
	 * QAPSolution constructor.
	 * @param factoryLocations Initialise by locations list.
	 */
	public QAPSolution(LinkedList<Integer> factoryLocations) {
		this.factoryLocations=new int[factoryLocations.size()];
		int pos=0;
		for(Integer i:factoryLocations)
			this.factoryLocations[pos++]=i;
	}

	/**
	 * Make a clone of this solution.
	 * @return Cloned solution.
	 */
	public QAPSolution duplicate(){
		QAPSolution clone=new QAPSolution(this.factoryLocations);
		clone.fitness=this.fitness;
		return clone;
	}
	
	/**
	 * Retrieve a copy of this file locations in respect to the factories represented by the position indexes.
	 * @return Factory location relation vector.
	 */
	public int[] getFactoryLocations() {
		return factoryLocations.clone();
	}
	
	/**
	 * Get the number of factories/locations in the problem domain.
	 * @return Number of factories.
	 */
	public int getNumberOfFactories(){
		return factoryLocations.length;
	}
	
	/**
	 * Get fitness for this solution.
	 * @return Current fitness.
	 */
	public double getFitness(){
		return this.fitness;
	}

	/**
	 * Set fitness for this solution.
	 * @param fitness New fitness value.
	 */
	public void setFitness(double fitness){
		this.fitness=fitness;
	}
	
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		for(int factory=0;factory<factoryLocations.length;factory++){
			sb.append("(F: "+factory+" L:"+factoryLocations[factory]+" ) ");
		}
		return super.toString();
	}

	@Override
	public int compareTo(QAPSolution o) {
		return Double.compare(this.fitness, o.getFitness());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof QAPSolution))
			return false;
		QAPSolution qapObj=(QAPSolution)obj;
		return Arrays.equals(factoryLocations, qapObj.factoryLocations);
	}
}
