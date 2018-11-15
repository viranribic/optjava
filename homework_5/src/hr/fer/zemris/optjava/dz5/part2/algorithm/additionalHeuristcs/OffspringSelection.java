package hr.fer.zemris.optjava.dz5.part2.algorithm.additionalHeuristcs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part2.interfaces.ICrossover;
import hr.fer.zemris.optjava.dz5.part2.interfaces.IMutation;
import hr.fer.zemris.optjava.dz5.part2.constants.Constants;
import hr.fer.zemris.optjava.dz5.part2.function.QAPFunction;
import hr.fer.zemris.optjava.dz5.part2.geneticOperators.crossoverOperator.PositionBasedCrossover;
import hr.fer.zemris.optjava.dz5.part2.geneticOperators.mutationOperator.ExchangeMutation;
import hr.fer.zemris.optjava.dz5.part2.geneticOperators.selectionOperator.TournamentSelection;
import hr.fer.zemris.optjava.dz5.part2.interfaces.ISelection;
import hr.fer.zemris.optjava.dz5.part2.solution.QAPSolution;

public class OffspringSelection {
	private ISelection selectioOp;
	private IMutation mutationOp;
	private ICrossover crossoverOp;
	private QAPFunction function;
	private static Random rand=new Random();
	
	public OffspringSelection(int tourSize, QAPFunction function) {
		this.selectioOp=new TournamentSelection(tourSize);
		this.mutationOp=new ExchangeMutation();
		this.crossoverOp=new PositionBasedCrossover();
		//this.crossoverOp=new OrderCrossover();
		this.function=function;
	}
	
	public boolean evaluatePopulation(LinkedList<QAPSolution> subPop,double successRatio,double maxSelectionPressure) {
		int totalNumOfIter=Constants.MAX_ITERATIONS;
		int iteration=0;
		//popSize
		//double successRatio=0;
		//maxSelPres
		double comparisonFactor=Constants.COMPARISON_FACTOR;
		double actSelPress=1;
		Set<QAPSolution> curPopulation=new HashSet<>(subPop);
		//System.out.println("OSGA Status: ");
		while(iteration<totalNumOfIter && (actSelPress)<maxSelectionPressure ){
			//System.out.print("|");
			Set<QAPSolution> nextPopulation=new HashSet<>();
			Set<QAPSolution> pool=new HashSet<>();
			selectioOp.setPopulation(curPopulation);
			
			while(nextPopulation.size()<successRatio*curPopulation.size() && (nextPopulation.size()+pool.size())<curPopulation.size()*maxSelectionPressure || (nextPopulation.size()+pool.size()<curPopulation.size())){
				QAPSolution parentA= selectioOp.select();
				QAPSolution parentB= selectioOp.select();
				QAPSolution[] children=crossoverOp.crossover(parentA, parentB);
				
				for(QAPSolution child:children){
					child=mutationOp.mutate(child);
					child.setFitness(function.valueAt(child));
					if(childIsBetter(child,parentA,parentB,comparisonFactor))
						nextPopulation.add(child);
					else
						pool.add(child);
				}
			}
			
			actSelPress=((double)nextPopulation.size()+pool.size())/curPopulation.size();
			//fill the rest of pop with elements from pool
			LinkedList<QAPSolution> poolList = new LinkedList<>(pool);
			while (nextPopulation.size()<curPopulation.size()) {
				if (!poolList.isEmpty())
					nextPopulation.add(poolList.remove(rand.nextInt(poolList.size())));
				else
					throw new RuntimeException("OffspringSelection-Can not fill the next pop. Selection pressure was too small.");
			}
			
			//Adjust comparison factor
			comparisonFactor+=Constants.COMP_FACTOR_GROWTH;
			if(comparisonFactor>=1)
				comparisonFactor=1;
			//update population
			curPopulation=nextPopulation;
			iteration++;
		}
		//System.out.println();
		subPop=new LinkedList<QAPSolution>(curPopulation);
		
		return ((actSelPress)<=maxSelectionPressure); //true if convergence has been observed, false otherwise
		
	}

	/**
	 * For the given pair of parents and child, determinate if the child fitness
	 * is better than the one from its parents.
	 * 
	 * @param child
	 *            Child subject.
	 * @param parentA
	 *            First parent subject.
	 * @param parentB
	 *            Second parent subject.
	 * @return True is the child is acceptable for next generation, false
	 *         otherwise.
	 */
	private boolean childIsBetter(QAPSolution child, QAPSolution parentA, QAPSolution parentB, double comparisonFactor) {
		QAPSolution better = null;
		QAPSolution worse = null;
		if (parentA.getFitness() < parentB.getFitness()) {
			better = parentA;
			worse = parentB;
		} else {
			better = parentB;
			worse = parentA;
		}
		double decider = worse.getFitness() + comparisonFactor * (better.getFitness() - worse.getFitness());
		return child.getFitness() <= decider;
	}
	
}
