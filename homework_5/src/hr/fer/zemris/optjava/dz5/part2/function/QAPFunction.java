package hr.fer.zemris.optjava.dz5.part2.function;

import hr.fer.zemris.optjava.dz5.part2.solution.QAPSolution;
/**
 * Quadratic assignment problem optimisation function.
 * @author Viran
 *
 */
public class QAPFunction{

	private int sizeOfInstance;
	private int[][] distanceMatrix;
	private int[][] flowMatrix;
	
	/**
	 * QAP function constructor.
	 * @param sizeOfInstance Number of factories(locations) available.
	 * @param distanceMatrix Matrix of distance between two locations.
	 * @param flowMatrix Matrix of flow between two factories.
	 */
	public QAPFunction(int sizeOfInstance, int[][] distanceMatrix, int[][] flowMatrix) {
		this.sizeOfInstance=sizeOfInstance;
		this.distanceMatrix=distanceMatrix.clone();
		this.flowMatrix=flowMatrix.clone();
	}
	
	/**
	 * Get the fitness for the given subject.
	 * @param solution Tested solution.
	 * @return Fitness of the solution.
	 */
	public double valueAt(QAPSolution solution) {
		int[] factoryLocatins=solution.getFactoryLocations();
		double f=0;
		int firstLocation;
		int secondLocation;
		for(int firstFactory=0;firstFactory<sizeOfInstance;firstFactory++){
			for(int secondFactory=0;secondFactory<sizeOfInstance;secondFactory++){
				firstLocation=factoryLocatins[firstFactory];
				secondLocation=factoryLocatins[secondFactory];
				f+=distanceMatrix[firstLocation][secondLocation]*flowMatrix[firstFactory][secondFactory];
			}
		}
		return f;
	}

	/**
	 * Return the number of factories/locations of this problem.
	 * @return Number of factories/locations processed.
	 */
	public int getNumberOfFactories() {
		return sizeOfInstance;
	}

}
