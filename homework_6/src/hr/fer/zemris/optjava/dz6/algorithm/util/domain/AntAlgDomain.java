package hr.fer.zemris.optjava.dz6.algorithm.util.domain;

import java.util.LinkedList;
import java.util.List;


import hr.fer.zemris.optjava.dz6.algorithm.util.greedy.GreedyTSP;
import hr.fer.zemris.optjava.dz6.parameters.Constants;
import hr.fer.zemris.optjava.dz6.util.Node;

/**
 * Ant algorithm domain as seen in MinMax Ant System optimisation.
 * 
 * @author Viran
 *
 */
public class AntAlgDomain {
	public LinkedList<Node> graphNodes;
	public int candidateListSize;

	public double[][] distances;
	public double[][] heuristics;
	public double[][] trails;

	public double maxTrail;
	public double minTrail;

	public LinkedList<LinkedList<NodeDistance>> candidateList = new LinkedList<>();

	/**
	 * AntAlgDomain constructor.
	 * 
	 * @param graphNodes
	 *            List of nodes contained in a city.
	 * @param candidateListSize
	 *            Number of best neighbour candidates per every node.
	 */
	public AntAlgDomain(List<Node> graphNodes, int candidateListSize) {
		this.graphNodes = new LinkedList<>(graphNodes);
		this.candidateListSize = candidateListSize;

		this.distances = new double[graphNodes.size()][graphNodes.size()];
		this.heuristics = new double[graphNodes.size()][graphNodes.size()];
		this.trails = new double[graphNodes.size()][graphNodes.size()];

		generateDistance_HeuristicsMatrix(Constants.BETA);
		generateTrailMatrix();
		generateCandidateList();

	}

	/**
	 * Create a matrix of heuristical relationships between nodes.
	 * 
	 * @param beta
	 *            Influence of distance between two nodes on a particular
	 *            relationship.
	 */
	private void generateDistance_HeuristicsMatrix(double beta) {
		for (int i = 0; i < graphNodes.size(); i++) {
			Node a = graphNodes.get(i);
			distances[i][i] = 0;
			heuristics[i][i] = 0;
			for (int j = i + 1; j < graphNodes.size(); j++) {
				Node b = graphNodes.get(j);
				double d = Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
				distances[i][j] = d;
				distances[j][i] = d;
				heuristics[i][j] = Math.pow(1/d, beta);
				heuristics[j][i] = heuristics[i][j];
			}
		}
	}

	/**
	 * Initiate the trail matrix as specified in MinMax Ant System algorithm.
	 */
	private void generateTrailMatrix() {
		
		double greedy=GreedyTSP.calcBest(distances, graphNodes.size());
		//System.out.println(greedy);
		this.maxTrail = 1. / (Constants.RO * greedy);
		this.minTrail = maxTrail / Constants.A;

		for (int i = 0; i < graphNodes.size(); i++) {
			trails[i][i] = maxTrail;
			for (int j = i + 1; j < graphNodes.size(); j++) {
				trails[i][j] = trails[j][i] = maxTrail;
			}
		}
	}

	/**
	 * Generate a list of best candidates for every node in terms of shortest
	 * length.
	 */
	private void generateCandidateList() {	
		for (int node = 0; node < graphNodes.size(); node++) {
			LinkedList<NodeDistance> candidateNodes = new LinkedList<>();

			for (int neighbour = 0; neighbour < graphNodes.size(); neighbour++) {
				// skip past the same node
				if (neighbour == node)
					continue;
				else {
					// if the list isn't full by
					if (candidateNodes.size() <= candidateListSize) {
						candidateNodes.add(new NodeDistance(neighbour, distances[node][neighbour]));
						candidateNodes.sort(null);
					} else {// if we need to check more elements and the
							// candidates are in place
						double distanceNoNeig = distances[node][neighbour];
						for (int i = 0; i < candidateNodes.size(); i++) {
							if (distanceNoNeig < candidateNodes.get(i).distance) {
								candidateNodes.add(i, new NodeDistance(neighbour, distanceNoNeig));
								candidateNodes.removeLast();
								break;
							}
						}
					}
				}
			}

			candidateList.add(node, candidateNodes);
		}
		
	}

	/**
	 * Ant algorithm domain utility class. Contains one pair of node-length pair
	 * for the element in list this object is contained in.
	 * 
	 * @author Viran
	 *
	 */
	public static class NodeDistance implements Comparable<NodeDistance> {
		public int neighbourNode;
		public double distance;

		/**
		 * NodeDistance constructor.
		 * 
		 * @param neighbourNode
		 *            Node neighbour.
		 * @param distance
		 *            Distance to neighbour.
		 */
		public NodeDistance(int neighbourNode, double distance) {
			this.neighbourNode = neighbourNode;
			this.distance = distance;
		}

		@Override
		public int compareTo(NodeDistance o) {
			return Double.compare(distance, o.distance);
		}

		@Override
		public String toString() {
			return String.format(" %d %5.2f", neighbourNode, distance);
		}
	}

	/**
	 * Get the number of nodes this domain contains.
	 * 
	 * @return Number of nodes.
	 */
	public int numberOfNodes() {
		return graphNodes.size();
	}

	
	public void printDistances(){
		// //---------print matrix---------
				 for(int i=0;i<graphNodes.size();i++){
				 for (int j = 0; j < graphNodes.size(); j++) {
				 System.out.printf("%f ",distances[i][j]);
				 }
				 System.out.println();
				 }
		 //------------------------------
	}
	
	public void printHeuristics(){
		// //---------print matrix---------
				 for(int i=0;i<graphNodes.size();i++){
				 for (int j = 0; j < graphNodes.size(); j++) {
				 System.out.printf("%f ",heuristics[i][j]);
				 }
				 System.out.println();
				 }
		 //------------------------------
	}
	
	public void printTrails(){
		// //---------print matrix---------
				 for(int i=0;i<graphNodes.size();i++){
				 for (int j = 0; j < graphNodes.size(); j++) {
				 System.out.printf("%f ",trails[i][j]);
				 }
				 System.out.println();
				 }
		 //------------------------------
	}
	
	public void printCandidateList(){
		int i=0;
		for(LinkedList<NodeDistance> nodeList:candidateList){
			System.out.println("Node " +i+" has list:" );
			for(NodeDistance nd:nodeList){
				System.out.println("\tNeighbour: "+nd.neighbourNode +" Distance: "+nd.distance);
			}
			i++;
			System.out.println();
		}
	}
	
}
