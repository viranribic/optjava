package hr.fer.zemris.optjava.dz8.algorithm.operators.selection;

import hr.fer.zemris.optjava.dz8.algorithm.solution.DifEvSolution;
import hr.fer.zemris.optjava.dz8.interfaces.ISelection;

public class FitSelection implements ISelection {
	private boolean minimise=true;
	
	public FitSelection() {
	}
	
	public FitSelection(boolean minimise) {
		this.minimise=minimise;
	}
	
	@Override
	public DifEvSolution select(DifEvSolution targetVector, DifEvSolution trialVector) {
		if(minimise){
			if(trialVector.getFitness()<=targetVector.getFitness())
				return trialVector;
			else
				return targetVector;
		}else{
			if(trialVector.getFitness()>=targetVector.getFitness())
				return trialVector;
			else
				return targetVector;
		
		}
		
	}

}
