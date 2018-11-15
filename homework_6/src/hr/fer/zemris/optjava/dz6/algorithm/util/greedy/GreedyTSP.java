package hr.fer.zemris.optjava.dz6.algorithm.util.greedy;

import java.util.HashSet;
/**
 * TSP greedy approach for finding the best graph route. 
 * @author Viran
 *
 */
public class GreedyTSP {
	
	/**
	 * Calculate the shortest path distance.
	 * @param distances Matrix of distances.
	 * @param numberOfCities Number of cities included.
	 * @return Shortest path length.
	 */
	public static double calcBest(double[][] distances,int numberOfCities) {
		int a=0;
		int favNode=0;
		HashSet<Integer> passedNodes=new HashSet<>();
		passedNodes.add(0);
		double distanceAB;
		double totalDistance=0;
		while(passedNodes.size()<numberOfCities){	
			
			double currMinDistance=Double.MAX_VALUE;
			favNode=0;
			
			for(int b=0;b<numberOfCities;b++){ 
				if(passedNodes.contains(b))
					continue;
				else{
					distanceAB=distances[a][b];
					if(distanceAB<currMinDistance){
						currMinDistance=distanceAB;
						favNode=b;
					}
				}
			}
//			System.out.println("From node" +a+ " to node "+favNode+" is distance "+currMinDistance);
//			System.out.println("Total distance"+totalDistance);
			
			totalDistance+=currMinDistance;
			a=favNode;
			if(!passedNodes.add(a)){
				throw new IllegalArgumentException("Error: greedy algorithm tryes to add selected node.");
			};
//			System.out.println("\n");
		}
		totalDistance+=distances[0][favNode];	//close the cycle by returning to the starting city
		return totalDistance;
	}

}
