package hr.fer.zemris.optjava.dz8.algorithm.population;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;
import hr.fer.zemris.optjava.dz8.interfaces.IFitnessFunction;
import hr.fer.zemris.optjava.dz8.neuralNetwork.ElmanNN;

public class DifEvolPopulation implements Iterable<DifEvSolution> {
	private DifEvSolution globalBest;
	private Random rand = new Random(System.currentTimeMillis());
	private LinkedList<DifEvSolution> subjects = new LinkedList<>();
	private int vectorDim;
	private double minBound;
	private double maxBound;

	public DifEvolPopulation(int populationSize, int vectorDim) {
		for (int i = 0; i < populationSize; i++) {
			subjects.add(new DifEvSolution(vectorDim));
		}
		this.vectorDim = vectorDim;
	}

	public DifEvolPopulation(int solutionVectorSize) {
		this.vectorDim = solutionVectorSize;
	}

	public void initialise(double minBound, double maxBound, IFitnessFunction<?> function) {
		this.minBound = minBound;
		this.maxBound = maxBound;

		for (int i = 0; i < subjects.size(); i++) {
			DifEvSolution solution = subjects.get(i);
			solution.initVector(minBound, maxBound, rand);
		}

		// set the 1st hidden layer node values as last vector values
		Object nNet = function.getNeuralNetwork();
		if (nNet instanceof ElmanNN) {
			ElmanNN elmanNNet = (ElmanNN) nNet;
			for (int i = 0; i < subjects.size(); i++) {
				DifEvSolution solution = subjects.get(i);
				double[] contextValues = elmanNNet.getHiddenValuesForInput(subjects.get(i).getSolution(),
						function.getData());
				// append the given values to solution
				int startingPos = elmanNNet.getWeightsCount();
				double[] solutionVector = solution.getSolution();
				for (int j = startingPos, k = 0; j < solutionVector.length; j++, k++) {
					solutionVector[j] = contextValues[k];
				}
			}
		}

		for (int i = 0; i < subjects.size(); i++) {
			DifEvSolution solution = subjects.get(i);
			solution.setFitness(function.valueAt(solution.getSolution()));
			if (globalBest == null) {
				this.globalBest = solution.duplicate();
			} else {
				if (globalBest.getFitness() > solution.getFitness())
					globalBest = solution.duplicate();
			}

		}

	}

	public int size() {
		return subjects.size();
	}

	public int solutionVectorSize() {
		return vectorDim;
	}

	@Override
	public Iterator<DifEvSolution> iterator() {
		return subjects.iterator();
	}

	public DifEvSolution getSolution(int atIndex) {
		return subjects.get(atIndex);
	}

	public void setGlobalBest(DifEvSolution bestSolution) {
		this.globalBest = bestSolution;
	}

	public DifEvSolution getGlobalBest() {
		return globalBest;
	}

	public double getMinVal() {
		return this.maxBound;
	}

	public double getMaxVal() {
		return this.minBound;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (DifEvSolution solution : subjects)
			sb.append(solution + "\n");
		return sb.toString();
	}

	public void addSolution(DifEvSolution selectedVector) {
		if(globalBest==null)
			globalBest=selectedVector.duplicate();
		else
			if(selectedVector.getFitness()<globalBest.getFitness())
				globalBest=selectedVector.duplicate();
		subjects.add(selectedVector.duplicate());
	}
}
