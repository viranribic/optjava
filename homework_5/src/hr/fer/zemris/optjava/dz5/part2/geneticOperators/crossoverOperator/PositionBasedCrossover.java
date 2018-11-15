package hr.fer.zemris.optjava.dz5.part2.geneticOperators.crossoverOperator;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part2.constants.Constants;
import hr.fer.zemris.optjava.dz5.part2.interfaces.ICrossover;
import hr.fer.zemris.optjava.dz5.part2.solution.QAPSolution;;

/**
 * Position based crossover genetic operator.
 * @author Viran
 *
 */
public class PositionBasedCrossover implements ICrossover {

	private static Random rand=new Random();
	
	@Override
	public QAPSolution[] crossover(QAPSolution parentA, QAPSolution parentB) {
		//get random positions
		Set<Integer> set=new HashSet<>();
		int numOfFactoryes=parentA.getFactoryLocations().length;
		int xoverRandomPosNum=(int)(numOfFactoryes*Constants.XOVER_RAND_POS_PERC);
		while(set.size()<xoverRandomPosNum)
			set.add(rand.nextInt(numOfFactoryes));
		LinkedList<Integer> randomPositions=new LinkedList<>(set);
		Collections.sort(randomPositions);
		
		//generate child
		int[] childAValues=new int[numOfFactoryes];
		int[] childBValues=new int[numOfFactoryes];
		int[] parentALocations=parentA.getFactoryLocations();
		int[] parentBLocations=parentB.getFactoryLocations();
		
		Set<Integer> childAset=new HashSet<>();
		Set<Integer> childBset=new HashSet<>();
		
		for(int i=0;i<randomPositions.size();i++){
			//copy values @ from random positions
			childAValues[randomPositions.get(i)]=parentBLocations[randomPositions.get(i)];
			//keep track of the duplicates
			childAset.add(parentBLocations[randomPositions.get(i)]);
			
			//copy values @ from random positions
			childBValues[randomPositions.get(i)]=parentALocations[randomPositions.get(i)];
			//keep track of the duplicates
			childBset.add(parentALocations[randomPositions.get(i)]);
		}
		
		//for all unassigned positions of both children
		for(int i=0;i<childAValues.length;i++){
			//for child a find the right element in parent A
			if(!randomPositions.contains(i))
			for(int j=0;j<parentALocations.length;j++){
				if(!childAset.contains(parentALocations[j])){
					childAset.add(parentALocations[j]);
					childAValues[i]=parentALocations[j];
					break;
				}
			}
			//for child a find the right element in parent A
			if(!randomPositions.contains(i))
			for(int j=0;j<parentBLocations.length;j++){
				if(!childBset.contains(parentBLocations[j])){
					childBset.add(parentBLocations[j]);
					childBValues[i]=parentBLocations[j];
					break;
				}
			}
			
		}
		
		
		QAPSolution childA=new QAPSolution(childAValues);
		QAPSolution childB=new QAPSolution(childBValues);
		QAPSolution[] result=new QAPSolution[2];
		result[0]=childA;
		result[1]=childB;
		return result;
	}

}
