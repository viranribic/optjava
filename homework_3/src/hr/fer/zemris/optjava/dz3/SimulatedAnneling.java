package hr.fer.zemris.optjava.dz3;

import java.util.Random;

/**
 * Optimisation algorithm- Simulated Anneling.
 * @author Viran
 *
 * @param <T> Class of the value representation used in optimisation.
 */
public class SimulatedAnneling<T extends SingleObjectSolution> implements IOptAlgorithm<T> {
	
	private IDecoder<T> decoder;
	private INeighborhood<T> neighborhood;
	private T startsWith;
	private IFunction function;
	private boolean minimize;
	private Random rand;
	private ITempSchedule schedule;
	
	/**
	 * SimulatedAnneling constructor.
	 * @param decoder Decoder for proper value interpretation.
	 * @param neighborhood Neighbourhood definer class.
	 * @param startsWith Entrance point to the problem domain.
	 * @param function Function we evaluate.
	 * @param minimize If true minimise variable searches for the global minimum, otherwise it searches for the global maximum.
	 */
	public SimulatedAnneling( IDecoder<T> decoder,INeighborhood<T> neighborhood,T startsWith,IFunction function,ITempSchedule schedule,boolean minimize) {
		this.decoder=decoder;
		this.neighborhood=neighborhood;
		this.startsWith=startsWith;
		this.function=function;
		this.minimize=minimize;
		this.schedule=schedule;
	}
	
	@Override
	public void run() {
		rand=new Random(System.currentTimeMillis());
		startsWith.fitness=function.valueAt(decoder.decode(startsWith));
		
		for(int i=0;i<schedule.getOuterLoopCounter();i++){
			
			printIterInfo(startsWith);
			//double temperature=schedule.getNextTemperature();
			T neighbor;
			for(int j=0;j<schedule.getInnerLoopCounter();j++){
				
				neighbor=neighborhood.randomNeighbour(startsWith);
				neighbor.fitness=function.valueAt(decoder.decode(neighbor));
				
				double deltaE=neighbor.fitness-startsWith.fitness;
				deltaE=(minimize)?deltaE:-deltaE;
				
				if(deltaE<=0)
					startsWith=neighbor;
				else
					setWithProbability(neighbor, deltaE,i);
				printIterInfo(startsWith);
			}
		}
	}


	/**
	 * Print iteration information such as iteration pass, current best value and fittness.
	 * @param iteration Iteration cycle.
	 */
	private void printIterInfo(T position) {
		double[] values=decoder.decode(position);
		System.out.print("Values: ");
		for(int i=0;i<values.length;i++)
			System.out.format("%10.5f",values[i]);
		System.out.println( " Fittness:" +position.fitness);
	}

	/**
	 * Change a better solution with a worse one with the described probability.
	 * @param neighbor Solution neighbour evaluated.
	 * @param deltaE Difference between the current position and its neighbour.
	 * @param temperature Current temperature.
	 */
	private void setWithProbability(T neighbor, double deltaE, double iteration) {
		
		if(deltaE>0){
			double X=rand.nextDouble();
			//double x=Math.exp((-deltaE)/temperature);
			double x=0.4*Math.pow(0.999, iteration);
			if(X<x){
				startsWith=neighbor;
			}
		}else{
			startsWith=neighbor;
		}
	}
}
