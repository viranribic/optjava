package hr.fer.zemris.optjava.dz5.part1.geneticOperators.mutationOperators;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.part1.interfaces.IMutation;
import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

/**
 * SimpleBitvectorMutation class.
 * @author Viran
 *
 */
public class SimpleBitvectorMutation implements IMutation {

	private double mutationProbability;
	private Random rand=new Random();
	
	/**
	 * SimpleBitvectorMutation constructor.
	 * @param mutationProbability General probability of mutation.
	 */
	public SimpleBitvectorMutation(double mutationProbability) {
		this.mutationProbability=mutationProbability;
	}
	@Override
	public BitvectorSolution mutate(BitvectorSolution child) {
		BitvectorSolution mChild=child.duplicate();
		if(rand.nextDouble()<mutationProbability){
			int mPosition=rand.nextInt(mChild.getVectorSize());
			mChild.getVector()[mPosition]=(mChild.getVector()[mPosition])?false:true; 
		}
		return mChild;
	}

}
