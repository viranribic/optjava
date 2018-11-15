package hr.fer.zemris.optjava.dz7.algorithms.psoAlg;

import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.function.FitnessFunction;
import hr.fer.zemris.optjava.dz7.solution.DoubleArraySolution;

/**
 * particle swarm optimisation algorithm population.
 * 
 * @author Viran
 *
 */
public class PSOPopulation {

	private Random rand = new Random(System.currentTimeMillis());
	private LinkedList<PSOParticle> particlePop = new LinkedList<>();
	private boolean globalBestFlag;
	private DoubleArraySolution globalBest;
	private int neighbourhoodSize;
	private double vMin;
	private double vMax;

	/**
	 * PSOPopulation constructor.
	 * 
	 * @param numberOfParticles
	 *            Number of particles in population.
	 * @param solVecSize
	 *            Dimension of the problem domain.
	 * @param globalBestFlag
	 *            Flag for choosing weather the algorithm uses the global or
	 *            local best solution.
	 * @param neighbourhoodSize
	 *            Number of local neighbours. Set to 0 if using the global best
	 *            variation.
	 * @param vMin
	 *            Minimal velocity allowed.
	 * @param vMax
	 *            Maximal velocity allowed.
	 */
	public PSOPopulation(int numberOfParticles, int solVecSize, boolean globalBestFlag, int neighbourhoodSize,
			double vMin, double vMax) {
		if (neighbourhoodSize >= numberOfParticles / 2)
			throw new IllegalArgumentException(
					"Local neighbourhood must be smaller than the half of the population size.");
		this.globalBestFlag = globalBestFlag;
		this.neighbourhoodSize = neighbourhoodSize;
		this.vMax = vMax;
		this.vMin = vMin;
		for (int i = 0; i < numberOfParticles; i++)
			particlePop.add(new PSOParticle(new DoubleArraySolution(solVecSize), rand));
	}

	/**
	 * Get particle at the specific index.
	 * 
	 * @param atIndex
	 *            Index of the particle.
	 * @return Particle.
	 */
	public PSOParticle getParticle(int atIndex) {
		return particlePop.get(atIndex);
	}

	/**
	 * Get the best particle solution.
	 * 
	 * @param evaluatedParticle
	 *            Particle we are evaluating.
	 * @return Social best solution component.
	 */
	public DoubleArraySolution getSocialBest(PSOParticle evaluatedParticle) {
		if (globalBestFlag) {
			return globalBest;
		} else {
			return localBest(evaluatedParticle).solution;
		}
	}

	/**
	 * For the given particle calculate the local best solution.
	 * 
	 * @param evaluatedParticle
	 *            Particle we are evaluation.
	 * @return Local best solution.
	 */
	private PSOParticle localBest(PSOParticle evaluatedParticle) {
		int indexOfParticle = particlePop.indexOf(evaluatedParticle);
		PSOParticle bestLocalSolution = evaluatedParticle;

		for (int neighbour = 1; neighbour < neighbourhoodSize; neighbour++) {
			// pos+1
			int nextIndex = (indexOfParticle + neighbour) % particlePop.size();
			PSOParticle nextSolution = particlePop.get(nextIndex);
			if (nextSolution.solution.getFitness() < bestLocalSolution.solution.getFitness())
				bestLocalSolution = nextSolution;

			// pos -1
			int prevIndex = (indexOfParticle - neighbour) % particlePop.size();
			prevIndex = (prevIndex < 0) ? prevIndex + particlePop.size() : prevIndex;
			PSOParticle prevSolution = particlePop.get(prevIndex);
			if (prevSolution.solution.getFitness() < bestLocalSolution.solution.getFitness())
				bestLocalSolution = prevSolution;
		}
		return bestLocalSolution;
	}

	/**
	 * Initialise particle values.
	 * 
	 * @param func
	 *            Fitness function used in calculating function fitness.
	 * @param minInitVals
	 *            List of minimal initial values per particle.
	 * @param maxInitVals
	 *            List of maximal initial values per particle.
	 * @param vmin
	 *            Minimal velocity.
	 * @param vmax
	 *            Maximal velocity.
	 */
	public void initPopulation(FitnessFunction func, double[] minInitVals, double[] maxInitVals) {

		for (PSOParticle particle : particlePop) {
			particle.solution.randomize(rand, minInitVals, maxInitVals);
			particle.solution.setFitness(func.valueAt(particle.solution.getValues()));
			particle.personalBest = particle.solution.duplicate();
			particle.initVelocity(this.vMin, this.vMax);

			if (globalBest == null) {
				globalBest = particle.solution.duplicate();
			} else { // we're looking for the lowest value
				if (globalBest.getFitness() > particle.solution.getFitness())
					globalBest = particle.solution.duplicate();
			}

		}
	}

	/**
	 * Number of particles in this population.
	 * 
	 * @return Population size.
	 */
	public int size() {
		return particlePop.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (PSOParticle particle : particlePop)
			sb.append(particle + "\n");
		return sb.toString();
	}

	/**
	 * Set the global best for this particle.
	 * 
	 * @param bestSolution
	 *            Global best solution.
	 */
	public void setGlobalBest(DoubleArraySolution bestSolution) {
		this.globalBest = bestSolution;
	}

	/**
	 * Get the global best solution.
	 * 
	 * @return Global best solution.
	 */
	public DoubleArraySolution getGlobalBest() {
		return globalBest;
	}

	/**
	 * Tell if the population uses global best or local best variation.
	 * 
	 * @return True if uses the global best variation, false otherwise.
	 */
	public boolean globalBestFlag() {
		return globalBestFlag;
	}

	/**
	 * Get maximal allowed velocity.
	 * 
	 * @return Maximal velocity.
	 */
	public double getMaxVelocity() {
		return this.vMax;
	}

	/**
	 * Get minimal allowed velocity.
	 * 
	 * @return Minimal velocity.
	 */
	public double getMinVelocity() {
		return this.vMin;
	}
}
