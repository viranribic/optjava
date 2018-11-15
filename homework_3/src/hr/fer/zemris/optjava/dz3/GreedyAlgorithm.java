package hr.fer.zemris.optjava.dz3;

/**
 * Simple greedy algorithm for finding a function optimum.
 * @author Viran
 *
 * @param <T> Class of the value representation used in optimisation.
 */
public class GreedyAlgorithm<T extends SingleObjectSolution> implements IOptAlgorithm<T> {
	private static final int ITERATIONS = 10_000;
	private IDecoder<T> decoder;
	private INeighborhood<T> neighborhood;
	private T startsWith;
	private IFunction function;
	private boolean minimize;
	
	/**
	 * SimulatedAnneling constructor.
	 * @param decoder Decoder for proper value interpretation.
	 * @param neighorhood Neighbourhood definer class.
	 * @param startsWith Entrance point to the problem domain.
	 * @param function Function we evaluate.
	 * @param minimize If true minimise variable searches for the global minimum, otherwise it searches for the global maximum.
	 */
	public GreedyAlgorithm( IDecoder<T> decoder,INeighborhood<T> neighborhood,T startsWith,IFunction function, boolean minimize) {
		this.decoder=decoder;
		this.neighborhood=neighborhood;
		this.startsWith=startsWith;
		this.function=function;
		this.minimize=minimize;
	}
	
	@Override
	public void run() {
			int minOption=(minimize)?1:-1;
			
			startsWith.fitness=function
					.valueAt(decoder.decode(startsWith));
			for(int i=0;i<ITERATIONS;i++){
				T neighbor=neighborhood.randomNeighbour(startsWith);
				neighbor.fitness=function.valueAt(decoder.decode(neighbor));
				if(minOption*neighbor.compareTo(startsWith)<0){
					printIterInfo(ITERATIONS-i);
					startsWith=neighbor;
				}
			}printIterInfo(0);
	}

	/**
	 * Print iteration information such as iteration pass, current best value and fittness.
	 * @param iteration Iteration cycle.
	 */
	private void printIterInfo(int iteration ){

		double[] valAtPos = decoder.decode(startsWith);
		int len = valAtPos.length;
		System.out.print("Itaration: "+iteration+" Values: ");
		for(int j=0;j<len;j++)
			System.out.format("%5.5f ",valAtPos[j]);
		System.out.format("function: %5.5f \n",function.valueAt(valAtPos));
		
	}

}
