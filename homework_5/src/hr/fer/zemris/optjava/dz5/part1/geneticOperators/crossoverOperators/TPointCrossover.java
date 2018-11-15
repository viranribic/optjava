package hr.fer.zemris.optjava.dz5.part1.geneticOperators.crossoverOperators;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part1.interfaces.ICrossover;
import hr.fer.zemris.optjava.dz5.part1.solution.BitvectorSolution;

/**
 * Crossover with t breakpoints class.
 * @author Viran
 *
 */
public class TPointCrossover implements ICrossover {

	Random rand=new Random();
	private int numberOfCrossoverPoints;
	
	/**
	 * TPointCrossover constructor.
	 * @param numberOfCrossoverPoints Number of crossover points. 
	 */
	public TPointCrossover(int numberOfCrossoverPoints) {
		this.numberOfCrossoverPoints=numberOfCrossoverPoints;
	}

	@Override
	public BitvectorSolution crossover(BitvectorSolution parentA, BitvectorSolution parentB) {
		if(parentA.getVectorSize()<numberOfCrossoverPoints)
			throw new IllegalArgumentException("CrossoverOperation: Bitvector lenght smaller than the number of crossover breakpoints.");
		
		HashSet<Integer> set=new HashSet<>();
		while(set.size()!=numberOfCrossoverPoints)
			set.add(rand.nextInt(parentA.getVectorSize()-2)+1); //breakpoint only after parentA pos=1 and parentB pos=n-1
		
		LinkedList<Integer> list=new LinkedList<>(set);
		Collections.sort(list);
		
		BitvectorSolution child=parentA.newLikeThis();
		boolean[] childvector=child.getVector();
		boolean genesFromA=true;
		int xPoint=list.removeFirst();
		for(int i=0;i<parentA.getVectorSize();i++){
			if(xPoint==i){
				genesFromA=(genesFromA)?false:true;
				if(list.size()!=0)
					xPoint=list.removeFirst();
			}
			if(genesFromA)
				childvector[i]=parentA.getVector()[i];
			else
				childvector[i]=parentB.getVector()[i];
		}
		
		return child;
	}

}
