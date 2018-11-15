package hr.fer.zemris.optjava.dz8.algorithm.solution;

import java.util.Arrays;
import java.util.Random;

public class DifEvSolution {
	private double[] solution;
	private double fitness;
	
	public DifEvSolution(int dimension) {
		solution=new double[dimension];
	}
	
	public void initVector(double minBound, double maxBound, Random rand) {
		for(int i=0;i<solution.length;i++)
			solution[i]=rand.nextDouble()*(maxBound-minBound)+minBound;
		
	}

	
	public int getDimension() {
		return solution.length;
	}

	public void setFitness(double fitness) {
		this.fitness=fitness;
	}
	
	public double getFitness() {
		return this.fitness;
	}

	public double[] getSolution() {
		return solution;
	}

	public DifEvSolution duplicate() {
		DifEvSolution clone=new DifEvSolution(solution.length);
		clone.fitness=this.fitness;
		clone.solution=Arrays.copyOf(solution, solution.length);
		return clone;
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("Fitness: "+fitness+" Vector: ");
		for(int i=0;i<solution.length;i++){
			sb.append(String.format("%6.5f ", solution[i]));
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fitness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(solution);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DifEvSolution other = (DifEvSolution) obj;
		if (Double.doubleToLongBits(fitness) != Double.doubleToLongBits(other.fitness))
			return false;
		if (!Arrays.equals(solution, other.solution))
			return false;
		return true;
	}

	
	
}
