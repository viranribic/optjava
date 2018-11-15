package hr.fer.zemris.optjava.dz7.algorithms.psoAlg;

import java.util.Random;

import hr.fer.zemris.optjava.dz7.solution.DoubleArraySolution;

/**
 * particle swarm optimisation algorithm particle representation.
 * @author Viran
 *
 */
public class PSOParticle {
	private Random rand;
	DoubleArraySolution solution;
	DoubleArraySolution personalBest;
	double[] velocity;
	double vMin;
	double vMax;
	
	/**
	 * PSOParticle constructor.
	 * @param solution Solution of this particle.
	 * @param rand Object for initialisation.
	 */
	public PSOParticle(DoubleArraySolution solution, Random rand) {
		this.solution=solution.duplicate();
		this.personalBest=solution.duplicate();
		this.velocity=new double[solution.getDimension()];
		this.rand=rand;
	}

	/**
	 * Initialise velocity of this particle with a random value between the given boundaries.
	 * @param vmin Minimal velocity.
	 * @param vmax Maximal velocity.
	 */
	public void initVelocity(double vmin, double vmax) {
		this.vMin=vmin;
		this.vMax=vmax;
		for(int i=0;i<velocity.length;i++)
			velocity[i]=rand.nextDouble()*(vmax-vmin)+vmin;
	}

//	/**
//	 * Get the solution contained in this particle.
//	 * @return Particle solution.
//	 */
//	public DoubleArraySolution getSolution() {
//		//TODO possibly unnecessary duplicate
//		return this.solution.duplicate();
//	}
//
//	/**
//	 * Set the new solution value.
//	 * @param solution New solution.
//	 */
//	public void setSolution(DoubleArraySolution solution){
//
//		//TODO possibly unnecessary duplicate
//		this.solution=solution.duplicate();
//	}
//	
//	/**
//	 * Get the personal best solution.
//	 * @return Personal best solution.
//	 */
//	public DoubleArraySolution getPersonalBest(){
//
//		//TODO possibly unnecessary duplicate
//		return personalBest.duplicate();
//	}
//	
//	/**
//	 * Set the personal best solution.
//	 * @param personalBest Personal best solution.
//	 */
//	public void setPersonalBest(DoubleArraySolution personalBest){
//
//		//TODO possibly unnecessary duplicate
//		this.personalBest=personalBest.duplicate();
//	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<velocity.length;i++)
			sb.append(String.format("%4.6f ", velocity[i]));
		return "Particle\n\tSolution: "+solution.toString()+"\n\tPersonal best: "+personalBest.toString()+"\n\tVelocity: "+sb.toString();
	}
}
