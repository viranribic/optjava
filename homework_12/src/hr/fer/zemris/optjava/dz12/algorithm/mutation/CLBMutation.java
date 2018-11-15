package hr.fer.zemris.optjava.dz12.algorithm.mutation;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.interfaces.IMutation;
import hr.fer.zemris.optjava.dz12.interfaces.IRNG;
import hr.fer.zemris.optjava.dz12.util.AlgConstants;
import hr.fer.zemris.optjava.dz12.util.random.RNG;

public class CLBMutation implements IMutation {

	@Override
	public CLBSolution mutation(CLBSolution child) {
		int clbNum = child.getNumberOfCLBs();
		int inputNum = child.getCLBInputs();
		int LUTfields = (int) Math.pow(2, inputNum);
		IRNG rand = RNG.getRNG();
		for (int clbIndex = 0; clbIndex < clbNum; clbIndex++) {
			if (rand.nextDouble() < AlgConstants.CLB_MUT_PROB) {
				if (rand.nextDouble() < AlgConstants.LUT_MUT_PROB) {
					int randomLutPos = rand.nextInt(0, LUTfields);
					flipBit(child,clbIndex,randomLutPos);
				} else {
					int randomInputPos = rand.nextInt(0, inputNum);
					shuffleInputs(child,clbIndex,randomInputPos);
				}
			}
		}
		return child;
	}

	private void shuffleInputs(CLBSolution child, int clbIndex, int randomInputPos) {
		IRNG rand=RNG.getRNG();
		LinkedList<Integer> inputs=child.getCLBInputList(clbIndex);
		int clbInSeq=child.getCLB(clbIndex).getIndexInSequence();
		inputs.remove(randomInputPos);
		
		inputs.add(randomInputPos, rand.nextInt(0, clbInSeq+child.getCLBInputs()));
		
		//System.out.println("For CLB "+clbIndex+" input at "+randomInputPos+" changed to "+inputs.get(randomInputPos));
	}

	private void flipBit(CLBSolution child, int clbIndex, int randomLutPos) {
		LinkedList<Integer> lut=child.getCLBLUT(clbIndex);
		int val=lut.get(randomLutPos);
		lut.remove(randomLutPos);
		lut.add(randomLutPos,(val==0)?1:0);
		//System.out.println("For CLB "+clbIndex+" LUT at"+randomLutPos+" flipped.");
	}

}
