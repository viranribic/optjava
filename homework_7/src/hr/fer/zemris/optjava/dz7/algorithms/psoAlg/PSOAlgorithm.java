package hr.fer.zemris.optjava.dz7.algorithms.psoAlg;

import java.util.Random;

import hr.fer.zemris.optjava.dz7.function.FitnessFunction;
import hr.fer.zemris.optjava.dz7.solution.DoubleArraySolution;
import hr.fer.zemris.optjava.dz7.util.Constants;

/**
 * Particle swarm optimisation algorithm.
 * 
 * @author Viran
 *
 */
public class PSOAlgorithm {

	private int maxIter;
	private PSOPopulation population;
	private double mErr;
	private FitnessFunction fitFunction;
	private Random rand = new Random(System.currentTimeMillis());

	/**
	 * PSOAlgorithm constructor.
	 * 
	 * @param fitFunction
	 *            Fitness function.
	 * @param popSize
	 *            Population size.
	 * @param mErr
	 *            Minimal error.
	 * @param maxIter
	 *            Maximal number of iterations.
	 * @param usingGlobalBest
	 *            Flag for deciding weather the algorithm uses global or local
	 *            best solution.
	 */
	public PSOAlgorithm(FitnessFunction fitFunction, int popSize, double mErr, int maxIter, boolean usingGlobalBest) {
		this(fitFunction, popSize, mErr, maxIter, 0, usingGlobalBest);
	}

	/**
	 * PSOAlgorithm constructor.
	 * 
	 * @param fitFunction
	 *            Fitness function.
	 * @param popSize
	 *            Population size.
	 * @param mErr
	 *            Minimal error.
	 * @param maxIter
	 *            Maximal number of iterations.
	 * @param neighbourhoodSize
	 *            Size of the local neighbourhood.
	 * @param usingGlobalBest
	 *            Flag for deciding weather the algorithm uses global or local
	 *            best solution.
	 */
	public PSOAlgorithm(FitnessFunction fitFunction, int popSize, double mErr, int maxIter, int neighbourhoodSize,
			boolean usingGlobalBest) {

		this.fitFunction = fitFunction;
		this.mErr = mErr;
		this.maxIter = maxIter;
		// init pop
		this.population = new PSOPopulation(popSize, fitFunction.getWeightsCount(), usingGlobalBest, neighbourhoodSize,
				Constants.V_MIN, Constants.V_MAX);
		double[] minInitVals = new double[fitFunction.getWeightsCount()];
		double[] maxInitVals = new double[fitFunction.getWeightsCount()];
		for (int i = 0; i < minInitVals.length; i++) {
			minInitVals[i] = Constants.MIN_INIT_VAL;
			maxInitVals[i] = Constants.MAX_INIT_VAL;
		}
		this.population.initPopulation(fitFunction, minInitVals, maxInitVals);

		// System.out.println(population);
	}

	/**
	 * Run this algorithm.
	 * 
	 * @return
	 */
	public DoubleArraySolution run() {

		int iter = 0;
		while (iter < maxIter) {
			evaluatePopulation();
			moveParticles();
			if (population.getGlobalBest().getFitness() < mErr)
				break;
			System.out.println("Best solution found for iteration " + iter + " :");
			System.out.println(population.getGlobalBest() + "\n\n");
			iter++;
		}
		System.out.println("Best solution found:");
		System.out.println(population.getGlobalBest());
		return population.getGlobalBest();
	}

	/**
	 * Execute population particle migration.
	 */
	private void moveParticles() {
		int vecDim = population.getParticle(0).solution.getDimension();

		for (int partPos = 0; partPos < population.size(); partPos++) {
			PSOParticle particle = population.getParticle(partPos);
			double[] x = particle.solution.getValues();
			double[] pbest = particle.personalBest.getValues();
			double[] gbest = population.getSocialBest(particle).getValues();

			double[] nextVelocity = new double[vecDim];

			for (int vectPos = 0; vectPos < vecDim; vectPos++) {
				nextVelocity[vectPos] = particle.velocity[vectPos]
						+ Constants.C1 * rand.nextDouble() * (pbest[vectPos] - x[vectPos])
						+ Constants.C2 * rand.nextDouble() * (gbest[vectPos] - x[vectPos]);
				nextVelocity[vectPos] = (nextVelocity[vectPos] > population.getMaxVelocity())
						? population.getMaxVelocity() : nextVelocity[vectPos];
				nextVelocity[vectPos] = (nextVelocity[vectPos] < population.getMinVelocity())
						? population.getMinVelocity() : nextVelocity[vectPos];

				x[vectPos] += nextVelocity[vectPos];
			}

			particle.velocity = nextVelocity;
		}

	}

	/**
	 * Evaluate population particles..
	 */
	private void evaluatePopulation() {
		double fitness = 0;
		double bestParticleFitness = 0;
		PSOParticle workingParticle;

		for (int i = 0; i < population.size(); i++) {
			workingParticle = population.getParticle(i);

			fitness = fitFunction.valueAt(workingParticle.solution.getValues());
			workingParticle.solution.setFitness(fitness);
			bestParticleFitness = workingParticle.personalBest.getFitness();

			if (fitness < bestParticleFitness) {
				workingParticle.personalBest = workingParticle.solution.duplicate();
			}

			double globalBestFittness = population.getGlobalBest().getFitness();
			if (globalBestFittness > fitness)
				population.setGlobalBest(workingParticle.solution.duplicate());

		}
	}

}
