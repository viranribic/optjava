package hr.fer.zemris.optjava.dz4.part2.function;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz4.part1.interfaces.IFunction;
import hr.fer.zemris.optjava.dz4.part2.runtimeExceptions.BoxFillFitnessException;

/**
 * BoxFill problem fitness function.
 * 
 * @author Viran
 *
 */
public class BoxFillFitness implements IFunction {
	// Disk fragmentation algorithm?

	// Stick @index:i has length stickLength= [15, 1, 20, 5, 4, 14, 7, 10, 1, 3,
	// 13, 1, 5, 13, 7, 2, 16, 4, 1, 5, 4, 5, 6, 1, 3, 1, 1, 1, 16, 13, 1, 1]

	// Solution has a vector of length equal to the length of all sticks
	// with values corresponding their placement in container[0, 1, 2, 3, 4, 5,
	// 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
	// 25, 26, 27, 28, 29, 30, 31]

	// Solution vector acts as a mapping operator between position on which the
	// stick is situated and its length.
	private LinkedList<Double> stickLength;
	private int maxHeight;
	private int maxFrame;

	/**
	 * BoxFillFitness constructor.
	 * 
	 * @param stickLength
	 *            List of sticks lengths.
	 * @param maxHeight
	 *            Maximal height.
	 */
	public BoxFillFitness(LinkedList<Double> stickLength, int maxHeight) {
		this.stickLength = stickLength;
		this.maxHeight = maxHeight;
		this.maxFrame = stickLength.size();

	}

	/*
	 * First try at modelling fitness only by taking into account the length of
	 * filled places.
	 */
	// public double valueAt(double[] value) {
	// double[] containerArrangement=new double[value.length];
	// int stickPosition;
	// int stickLength;
	// //find the sum of lengths on all positions
	// for(int i=0;i<value.length;i++){
	// stickPosition=(int)value[i];
	// stickLength=this.stickLength.get(i).intValue();
	// containerArrangement[stickPosition]+=stickLength;
	// if(containerArrangement[stickPosition]>maxHeight)
	// throw new BoxFillFitnessException("Sum of lengths exceeds the container
	// boundary."); // if the hard condition is not met, return the fitness as
	// if the maximal (unreal) arrangement has been calculated
	// }
	//
	//
	// int end=containerArrangement.length-1;
	// int freeSpacesFromEnd=0;
	// for(int i=end;i>=0;i--){
	// if(containerArrangement[i]==0)
	// freeSpacesFromEnd++;
	// else
	// break;
	// }
	//
	// return maxFrame-freeSpacesFromEnd;
	// }

	/**
	 * Number of sticks.
	 * 
	 * @return
	 */
	public int numberOfFillElements() {
		return stickLength.size();
	}

	/**
	 * Get the list containing stick lengths for needed evaluations.
	 * 
	 * @return Stick length list.
	 */
	public LinkedList<Double> getStickLengthList() {
		LinkedList<Double> copyList = new LinkedList<>();
		for (Double d : stickLength)
			copyList.add(d);
		return copyList;
	}

	/**
	 * Get the maximal allowed height in the container.
	 * 
	 * @return Maximal allowed height.
	 */
	public int getMaxHeight() {
		return maxHeight;
	}

	@Override
	public double valueAt(double[] value) {
		double[] containerArrangement = new double[value.length];
		int stickPosition;
		int stickLength;
		// find the sum of lengths on all positions
		for (int i = 0; i < value.length; i++) {
			stickPosition = (int) value[i];
			stickLength = this.stickLength.get(i).intValue();
			containerArrangement[stickPosition] += stickLength;
			if (containerArrangement[stickPosition] > maxHeight)
				throw new BoxFillFitnessException("Sum of lengths exceeds the container boundary."); // if
																										// the
																										// hard
																										// condition
																										// is
																										// not
																										// met,
																										// return
																										// the
																										// fitness
																										// as
																										// if
																										// the
																										// maximal
																										// (unreal)
																										// arrangement
																										// has
																										// been
																										// calculated
		}
		double resultingFitness = 0;
		// scale that value
		for (int i = 0; i < value.length; i++) {
			containerArrangement[i] /= maxHeight;
			resultingFitness += Math.pow(containerArrangement[i], 2);
		}

		// Calculate the number of bins: from the end count the number of bins
		// with the sum=0/one
		// without sticks
		int end = containerArrangement.length - 1;
		int freeSpacesFromEnd = 0;
		for (int i = end; i >= 0; i--) {
			if (containerArrangement[i] == 0)
				freeSpacesFromEnd++;
			else
				break;
		}
		int numOfBins = maxFrame - freeSpacesFromEnd;
		return resultingFitness / numOfBins;
	}

}
