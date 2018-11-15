package hr.fer.zemris.optjava.dz6.algorithm;


import java.util.LinkedList;
import java.util.Random;


import hr.fer.zemris.optjava.dz6.algorithm.util.domain.AntAlgDomain;
import hr.fer.zemris.optjava.dz6.algorithm.util.domain.AntAlgDomain.NodeDistance;
import hr.fer.zemris.optjava.dz6.parameters.Constants;
import hr.fer.zemris.optjava.dz6.solution.TSPSolution;

/**
 * Main class for Min Max Ant System optimisation.
 * @author Viran
 *
 */
public class MinMaxAntSysAlg {

	private Random rand;
	private int[] indexes;
	
	private TSPSolution[] ants;
	
	private int[] reachable;
	private double[] probabilities;
	
	private double ro;
	private double alpha;
	@SuppressWarnings("unused")
	private double beta;
	
	private TSPSolution best;
	private boolean bestFound = false;

	private double maxIter;

	private AntAlgDomain antDomain;
	private boolean updateBestInIteration=true;
	
	/**
	 * MinMax Ant System algorithm constructor.
	 * @param antDomain Domain on which the ant agents operate.
	 * @param antNum Number of ants in population.
	 * @param maxIter Maximal number of iterations allowed.
	 * @param alpha Alpha constant.
	 * @param beta Beta constant.
	 */
	public MinMaxAntSysAlg(AntAlgDomain antDomain, int antNum, int maxIter) {
		this.rand = new Random();
		
		this.antDomain=antDomain;
		this.alpha=Constants.ALPHA;
		this.beta=Constants.BETA;
		this.ro = Constants.RO;
		
		int numOfNodes=antDomain.numberOfNodes();
		
		indexes = new int[numOfNodes];
		for (int i = 0; i < indexes.length; i++)
			indexes[i] = i;
		probabilities = new double[numOfNodes];
		reachable = new int[numOfNodes];
		this.maxIter = maxIter;
		
		//init ants
		ants = new TSPSolution[antNum];
		for (int i = 0; i < ants.length; i++) {
			ants[i] = new TSPSolution();
			ants[i].nodeIndexes = new int[numOfNodes];
		}
		best = new TSPSolution();
		best.nodeIndexes = new int[numOfNodes];
	}

	/**
	 * Run this algorithm.
	 */
	public void run() {
		System.out.println("Algorithm started:");
		System.out.println("==================");
		int iter = 0;
		while (iter < maxIter) {
			iter++;
			for (int antIndex = 0; antIndex < ants.length; antIndex++) {
				TSPSolution ant = ants[antIndex];
				doWalk(ant);
			}
			updateTrails();
			evaporateTrails();
			checkBestSolution(); 
			//System.out.println(best);
			if(iter>=maxIter*Constants.ITERATION_AT_WHICH_WE_CHANGE_TO_GLOBAL_BEST)
				updateBestInIteration=false;
		}
		System.out.println("Best length: " + best.tourLength);
		System.out.println(best);

	}

	/**
	 * Run one walk over map for the given ant.
	 * @param ant Ant executing one walk.
	 */
	private void doWalk(TSPSolution ant) {
		//init walk parameters
		System.arraycopy(indexes, 0, reachable, 0, indexes.length);
		shuffleArray(reachable, rand);
		//start from a random but fixed node
		ant.nodeIndexes[0] = reachable[0];

		//for unexplored nodes
		for (int step = 1; step < antDomain.numberOfNodes() - 1; step++) {

			calculateProbabilityForAnt(ant,step);
			
			int selectedCandidate = -1;
			
			selectedCandidate=selectFromCandidateList(step);
			if(selectedCandidate<0)
				selectedCandidate=selectFromAllAvailable(step);
			
			if (selectedCandidate == -1) {
				selectedCandidate = antDomain.numberOfNodes() - 1;
			}
			
			//switch the found solution with the one from the rest of available candidates list
			int tmp = reachable[step];
			reachable[step] = reachable[selectedCandidate];
			reachable[selectedCandidate] = tmp;
			ant.nodeIndexes[step] = reachable[step];
		}
		ant.nodeIndexes[ant.nodeIndexes.length - 1] = reachable[ant.nodeIndexes.length - 1];
		evaluate(ant, antDomain.distances);

		checkBestSolution();
		
	}

	private int selectFromAllAvailable(int step) {
		int selectedCandidate=0;
		double number = rand.nextDouble();
		double probSum = 0.0;
		for (int candidate = step; candidate < antDomain.numberOfNodes(); candidate++) {
			int nodeIndex = reachable[candidate];
			probSum += probabilities[nodeIndex];
			if (number <= probSum) {
				selectedCandidate = candidate;
				break;
			}
		}
		return selectedCandidate;
	}


	
	private int selectFromCandidateList(int step) {
		//step-1 == current node 
		int prevNodeIndex = reachable[step-1];
		LinkedList<NodeDistance> nodeCandidates=antDomain.candidateList.get(prevNodeIndex);
		int selectedCandidate=-1;
		LinkedList<NodeDistance> unselectedNode=new LinkedList<>();
		
		//in candidates extract those who have not been selected
		for(NodeDistance nodeDistance:nodeCandidates){
			int nodeInCandList=nodeDistance.neighbourNode;
			for (int candidate = step; candidate < antDomain.numberOfNodes(); candidate++) {
				int nodeIndex = reachable[candidate];
				if(nodeIndex==nodeInCandList){
					unselectedNode.add(nodeDistance);
				}
			}
		}
		
		//if no candidate can be chosen
		if(unselectedNode.size()==0)
			return -1;
	
		//if there are some candidates calculate probability of choosing one of them
		
		double probSum = 0.0;
		double[] internalProbabilities=new double[unselectedNode.size()];
		for (int candidate = 0; candidate < unselectedNode.size(); candidate++) {
			int nodeIndex = unselectedNode.get(candidate).neighbourNode;
			internalProbabilities[candidate] = Math.pow(antDomain.trails[prevNodeIndex][nodeIndex], alpha)
					* antDomain.heuristics[prevNodeIndex][nodeIndex];
			probSum += internalProbabilities[candidate];
		}
		//normalise 
		for (int candidate = 0; candidate < unselectedNode.size(); candidate++) {
			internalProbabilities[candidate] = internalProbabilities[candidate] / probSum;
		}
		
		
		double number = rand.nextDouble();
		probSum = 0.0;
		for (int candidate = 0; candidate < unselectedNode.size(); candidate++) {
			probSum += internalProbabilities[candidate];
			if (number <= probSum) {
				selectedCandidate = unselectedNode.get(candidate).neighbourNode;
				break;
			}
		}
		
		
		for(int i=0;i<reachable.length;i++)
			if(reachable[i]==selectedCandidate){
				selectedCandidate=i;
				break;
			}
				
		return selectedCandidate;
	}

	/**
	 * Evaluate the given ant by measuring the total distance travelled.
	 * @param ant Evaluating subject.
	 * @param distances Matrix of distances.
	 */
	private static void evaluate(TSPSolution ant, double[][] distances) {
		int start=ant.nodeIndexes[0];
		double distance=0;
		
		for(int i=1; i<ant.nodeIndexes.length;i++){
			distance+=distances[start][ant.nodeIndexes[i]];
			start=ant.nodeIndexes[i];
		}
		distance+=distances[start][ant.nodeIndexes[0]];
		ant.tourLength=distance;
	}
	
	/**
	 * Evaluate complete ant population.
	 * @param population Solutions of the population.
	 */
	public void evaluate(TSPSolution[] population){
		for(int i=0;i<population.length;i++){
			TSPSolution s=population[i];
			evaluate(s, antDomain.distances);
		}
	}

	/**
	 * Fill the probability array for the given ant in a particular step of iteration.
	 * @param ant Ant for which we calculate probabilities.
	 * @param step Step of iteration.
	 */
	private void calculateProbabilityForAnt(TSPSolution ant,int step) {
		int prevNodeIndex = ant.nodeIndexes[step - 1];
		double probSum = 0.0;
		
		for (int candidate = step; candidate < antDomain.numberOfNodes(); candidate++) {
			int nodeIndex = reachable[candidate];
			probabilities[nodeIndex] = Math.pow(antDomain.trails[prevNodeIndex][nodeIndex], alpha)
					* antDomain.heuristics[prevNodeIndex][nodeIndex];
			probSum += probabilities[nodeIndex];
		}
		//normalise 
		for (int candidate = step; candidate < antDomain.numberOfNodes(); candidate++) {
			int nodeIndex = reachable[candidate];
			probabilities[nodeIndex] = probabilities[nodeIndex] / probSum;
		}
		
		
	}

	/**
	 * Increment trails by ant analysis.
	 */
	private void updateTrails() {
		int updates = ants.length;
		if (updateBestInIteration) { //update by iteration best -for ANT_UPDATE_NUM ants
			updates = Constants.ANT_UPDATE_NUM;
			partialSort(ants, updates);
			for(int antNum=0;antNum<updates;antNum++)
				updateForAnt(ants[antNum]);
		}else{
			updateForAnt(best);
		}
	}

	/**
	 * Update trails matrix for one particular ant. As specified by the MMAS don't let the trails exceed maximal trail values.
	 * @param ant Ant which updates the matrix.
	 */
	private void updateForAnt(TSPSolution ant) {
		double delta = 1.0 / ant.tourLength;

		for (int i = 0; i < ant.nodeIndexes.length - 1; i++) {
			int a = ant.nodeIndexes[i];
			int b = ant.nodeIndexes[i + 1];
			antDomain.trails[a][b] += delta;
			if(antDomain.trails[a][b]>=antDomain.maxTrail)
				antDomain.trails[a][b]=antDomain.maxTrail;
			antDomain.trails[b][a] = antDomain.trails[a][b];
		}
	}

	/**
	 * Sort the ant population in ascending order comparing the path length passed.
	 * @param ants Array of ant solutions.
	 * @param bestNum Number of best ants which should be shifted at the beginning of the population.
	 */
	private void partialSort(TSPSolution[] ants, int bestNum) {
		for(int i=0;i<bestNum;i++){
			int best=i;
			for(int j=i+1;j<ants.length;j++){
				if(ants[best].tourLength>ants[j].tourLength){
					best=j;
				}
			}
			if(best!=i){
				TSPSolution tmp=ants[i];
				ants[i]=ants[best];
				ants[best]=tmp;
			}
		}
		
	}

	/**
	 * Evaporate trails. As specified by the MMAS don't let the trails drop below minimal trail values.
	 */
	private void evaporateTrails() {
		for (int i = 0; i < antDomain.numberOfNodes(); i++) {
			for (int j = i + 1; j < antDomain.numberOfNodes(); j++) {
				antDomain.trails[i][j] = antDomain.trails[i][j] * (1 - ro);
				if(antDomain.trails[i][j]<=antDomain.minTrail)
					antDomain.trails[i][j]=antDomain.minTrail;
				antDomain.trails[j][i] = antDomain.trails[i][j];
			}
		}
	}

	/**
	 * Search for the best solution depending upon the ants tour length.
	 */
	private void checkBestSolution() {
		if (!bestFound) {
			bestFound = true;
			TSPSolution ant = ants[0];
			System.arraycopy(ant.nodeIndexes, 0, best.nodeIndexes, 0, ant.nodeIndexes.length);
			best.tourLength = ant.tourLength;
		}
		double currentBest = best.tourLength;
		int bestIndex = -1;
		for (int antIndex = 0; antIndex < ants.length; antIndex++) {
			TSPSolution ant = ants[antIndex];
			if (ant.tourLength < currentBest && ant.tourLength!=0) {
				currentBest = ant.tourLength;
				bestIndex = antIndex;
			}
		}
		if (bestIndex != -1) {
			TSPSolution ant = ants[bestIndex];
			System.arraycopy(ant.nodeIndexes, 0, best.nodeIndexes, 0, ant.nodeIndexes.length);
			best.tourLength = ant.tourLength;
		}
	}

	/**
	 * Shuffle the given array elements.
	 * @param array Array of elements.
	 * @param rand Random class object.
	 */
	private void shuffleArray(int[] array, Random rand) {
		int randPos;
		for (int i = array.length - 1; i > 0; i--) {
			randPos = rand.nextInt(i + 1);
			int tmp = array[randPos];
			array[randPos] = array[i];
			array[i] = tmp;
		}
	}

}
