package hr.fer.zemris.optjava.dz4.part2.geneticOperatos.mutationOperators;

import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.part2.constants.ConstantsTask2;
import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution;
import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution.BoxFillBucket.BoxFillStick;

/**
 * Box Fill Mutation used in genetic algorithm solving.
 * 
 * @author Viran
 *
 */
public class BoxFillMutation {

	private static Random rand = new Random();

	/**
	 * Mutates the subject.
	 * 
	 * @param subject
	 *            Subject on which we perform mutation.
	 * @return Mutated subject.
	 */
	public BoxFillSolution mutation(BoxFillSolution subject) {
		int destroyedLimit = (int) (ConstantsTask2.MUTATION_DESTROY_BIN_PERCENT * subject.getNumOfBins());
		int numOfBinsToDestroy = rand.nextInt(destroyedLimit);
		LinkedList<BoxFillStick> unassignedSticks = new LinkedList<>();
		for (int i = 0; i < numOfBinsToDestroy; i++) {
			int randomPos = rand.nextInt(subject.getNumOfBins());
			unassignedSticks.addAll(subject.destroyBin(randomPos));
		}
		if(unassignedSticks.size()!=0)
			subject.reinsertSticks(unassignedSticks);
		return subject;
	}
}
