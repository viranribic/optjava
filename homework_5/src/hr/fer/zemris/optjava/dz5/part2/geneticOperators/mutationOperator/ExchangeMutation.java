package hr.fer.zemris.optjava.dz5.part2.geneticOperators.mutationOperator;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.part2.constants.Constants;
import hr.fer.zemris.optjava.dz5.part2.interfaces.IMutation;
import hr.fer.zemris.optjava.dz5.part2.solution.QAPSolution;
/**
 * Exchange mutation genetic operator;
 * @author Viran
 *
 */
public class ExchangeMutation implements IMutation {
	private static Random rand=new Random();

	@Override
	public QAPSolution mutate(QAPSolution child) {
		int[] facLoc=child.getFactoryLocations();
		if(rand.nextDouble()<Constants.MUTATION_PROBABILITY){
			int pos1=rand.nextInt(facLoc.length);
			int pos2=rand.nextInt(facLoc.length);
			while(pos2==pos1)
				pos2=rand.nextInt(facLoc.length);
			int tmp=facLoc[pos1];
			facLoc[pos1]=facLoc[pos2];
			facLoc[pos2]=tmp;
		}
		return new QAPSolution(facLoc);
	}
	

}
