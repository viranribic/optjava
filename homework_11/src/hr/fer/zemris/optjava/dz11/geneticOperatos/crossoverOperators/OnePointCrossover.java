package hr.fer.zemris.optjava.dz11.geneticOperatos.crossoverOperators;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.constants.ConstantsTask;
import hr.fer.zemris.optjava.dz11.geneticAlgorithm.solution.GAImgSolution;
import hr.fer.zemris.optjava.dz11.interfaces.ICrossoverOp;
import hr.fer.zemris.optjava.dz11.util.GrayCoder;
import hr.fer.zemris.optjava.rng.IRNG;

public class OnePointCrossover implements ICrossoverOp {

	private IRNG rand;

	/**
	 * TPointCrossover constructor.
	 * 
	 * @param numberOfCrossoverPoints
	 *            Number of crossover points.
	 */
	public OnePointCrossover(IRNG rand) {
		this.rand = rand;
	}

	@Override
	public GASolution<int[]> crossover(GASolution<int[]> parentA, GASolution<int[]> parentB) {
		return xoverStrategyThree(parentA, parentB);
	}

	private GASolution<int[]> xoverStrategyThree(GASolution<int[]> parentA, GASolution<int[]> parentB) {
		int numOfrect = (int) ((parentA.getData().length - 1) / 5);
		int point = rand.nextInt(0, numOfrect);
		int[] childV = parentA.getData().clone();

		GASolution<int[]> child = new GAImgSolution(numOfrect, ((GAImgSolution) parentA).getMaxWidth(),
				((GAImgSolution) parentA).getMaxHeight());
		child.fitness = 0;

		for (int i = point; i < numOfrect; i++) {
			child.getData()[i * 5 + 1] = parentB.getData()[i * 5 + 1];
			child.getData()[i * 5 + 2] = parentB.getData()[i * 5 + 2];
			child.getData()[i * 5 + 3] = parentB.getData()[i * 5 + 3];
			child.getData()[i * 5 + 4] = parentB.getData()[i * 5 + 4];
			child.getData()[i * 5 + 5] = parentB.getData()[i * 5 + 5];

		}
		((GAImgSolution)child).setVector(childV);
		return child;
	}

	private GASolution<int[]> xoverStrategyTwo(GASolution<int[]> parentA, GASolution<int[]> parentB) {
		int[] betterVector;
		int[] worstVector;

		if (parentA.fitness > parentB.fitness) {
			betterVector = parentA.getData();
			worstVector = parentB.getData();
		} else {
			betterVector = parentB.getData();
			worstVector = parentA.getData();
		}

		int[] xoverVector = new int[betterVector.length];

		int maxHeight = ((GAImgSolution) parentA).getMaxHeight();
		int maxWidth = ((GAImgSolution) parentA).getMaxWidth();

		int squareNum = (int) ((betterVector.length - 1) / 5);

		double betterProbability = rand.nextDouble();

		if (betterProbability < ConstantsTask.XOVER_TAKE_BETTER_PROB) {
			xoverVector[0] = betterVector[0];
		} else {
			xoverVector[0] = worstVector[0];
		}

		// TODO choose one component per element.
		// TODO take care of the width and height as follows:
		// 1. make a probability selection
		// 2. if the given value is too large take the other
		// 3. if the other is large as well take the length as a random number
		// between here and possible space
		for (int i = 0; i < squareNum; i++) {

			int xoverX = 0;
			int xoverY = 0;
			int xoverWidth = 0;
			int xoverHeigh = 0;
			int xoverColor = 0;

			// choose x
			betterProbability = rand.nextDouble();
			if (betterProbability < ConstantsTask.XOVER_TAKE_BETTER_PROB) {
				xoverX = betterVector[1 + i * 5 + 0];
			} else {
				xoverX = worstVector[1 + i * 5 + 0];
			}

			int widthRange = maxWidth - xoverX;

			// choose y
			betterProbability = rand.nextDouble();
			if (betterProbability < ConstantsTask.XOVER_TAKE_BETTER_PROB) {
				xoverY = betterVector[1 + i * 5 + 1];
			} else {
				xoverY = worstVector[1 + i * 5 + 1];
			}

			int heightRange = maxHeight - xoverY;

			// choose width
			if (betterVector[1 + i * 5 + 2] < widthRange && worstVector[1 + i * 5 + 2] < widthRange) {
				betterProbability = rand.nextDouble();
				if (betterProbability < ConstantsTask.XOVER_TAKE_BETTER_PROB) {
					xoverWidth = betterVector[1 + i * 5 + 2];

				} else {
					xoverWidth = worstVector[1 + i * 5 + 2];
				}
			} else {
				if (betterVector[1 + i * 5 + 2] < widthRange)
					xoverWidth = betterVector[1 + i * 5 + 2];
				else if (worstVector[1 + i * 5 + 2] < widthRange)
					xoverWidth = worstVector[1 + i * 5 + 2];
				else {
					if (widthRange != 0) {
						xoverWidth = rand.nextInt(0, widthRange);

					} else {
						xoverWidth = 0;
					}
				}

			}

			// choose height
			if (betterVector[1 + i * 5 + 3] < heightRange && worstVector[1 + i * 5 + 3] < heightRange) {
				betterProbability = rand.nextDouble();
				if (betterProbability < ConstantsTask.XOVER_TAKE_BETTER_PROB) {
					xoverHeigh = betterVector[1 + i * 5 + 3];

				} else {
					xoverHeigh = worstVector[1 + i * 5 + 3];
				}
			} else {
				if (betterVector[1 + i * 5 + 3] < heightRange)
					xoverHeigh = betterVector[1 + i * 5 + 3];
				else if (worstVector[1 + i * 5 + 3] < heightRange)
					xoverHeigh = worstVector[1 + i * 5 + 3];
				else {
					if (heightRange != 0) {
						xoverHeigh = rand.nextInt(0, heightRange);
					} else {
						xoverHeigh = 0;
					}
				}
			}

			// choose back colour
			betterProbability = rand.nextDouble();
			if (betterProbability < ConstantsTask.XOVER_TAKE_BETTER_PROB) {
				xoverColor = betterVector[1 + i * 5 + 4];
			} else {
				xoverColor = worstVector[1 + i * 5 + 4];
			}

			xoverVector[1 + i * 5 + 0] = xoverX;
			xoverVector[1 + i * 5 + 1] = xoverY;
			xoverVector[1 + i * 5 + 2] = xoverWidth;
			xoverVector[1 + i * 5 + 3] = xoverHeigh;
			xoverVector[1 + i * 5 + 4] = xoverColor;
		}

		GAImgSolution child = new GAImgSolution(squareNum, maxWidth, maxHeight);
		child.setVector(xoverVector);
		return child;
	}

	private int executeCrossOver(int numberA, int numberB, int length) {
		int maskA = 0;
		int maskB = 0;
		int randPos = rand.nextInt(0, length);

		// prepare masks
		for (int i = 0; i < length; i++) {
			if (i < randPos) {
				maskA = maskA << 1;
				maskA = (maskA | 1);
			} else {
				maskB = maskB << 1;
				maskB = (maskB | 1);
			}
		}

		// mask numbers
		numberA &= maskA;
		numberB &= maskB;
		// add the numbers
		int xoverNum = numberA | numberB;
		return xoverNum;
	}

	private GAImgSolution xoverStrategyOne(GASolution<int[]> parentA, GASolution<int[]> parentB) {
		int[] aVector = parentA.getData();
		int[] bVector = parentB.getData();
		int[] xoverVector = new int[aVector.length];

		int maxHeight = ((GAImgSolution) parentA).getMaxHeight();
		int maxWidth = ((GAImgSolution) parentA).getMaxWidth();

		// process bgc

		xoverVector[0] = GrayCoder.decodeGray(
				executeCrossOver(GrayCoder.encodeGray(aVector[0]), GrayCoder.encodeGray(bVector[0]), Byte.SIZE));
		// process data

		int squareNum = (int) ((aVector.length - 1) / 5);

		for (int i = 0; i < squareNum; i++) {

			// crossover 1st component - x value
			int xoverX = GrayCoder.decodeGray(executeCrossOver(GrayCoder.encodeGray(aVector[1 + i * 5 + 0]),
					GrayCoder.encodeGray(bVector[1 + i * 5 + 0]), Integer.SIZE)); // TODO
																					// this
																					// should
																					// be
																					// bounded
																					// to
																					// the
																					// maximal
																					// number
																					// of
																					// bits
																					// required

			// crossover second component - y value
			int xoverY = GrayCoder.decodeGray(executeCrossOver(GrayCoder.encodeGray(aVector[1 + i * 5 + 1]),
					GrayCoder.encodeGray(bVector[1 + i * 5 + 1]), Integer.SIZE)); // TODO
																					// this
																					// should
																					// be
																					// bounded
																					// to
																					// the
																					// maximal
																					// number
																					// of
																					// bits
																					// required

			if (xoverX > maxWidth)
				xoverX = maxWidth;

			if (xoverY > maxHeight)
				xoverY = maxHeight;

			// crossover third component - width
			int xoverWidth = GrayCoder.decodeGray(executeCrossOver(GrayCoder.encodeGray(aVector[1 + i * 5 + 2]),
					GrayCoder.encodeGray(bVector[1 + i * 5 + 2]), Integer.SIZE)); // TODO
																					// size
																					// should
																					// be
																					// bounded
																					// to
																					// the
																					// max
																					// leftover
																					// bit
																					// space
			// crossover fourth component - height
			int xoverHeigh = GrayCoder.decodeGray(executeCrossOver(GrayCoder.encodeGray(aVector[1 + i * 5 + 3]),
					GrayCoder.encodeGray(bVector[1 + i * 5 + 3]), Integer.SIZE)); // TODO
																					// size
																					// should
																					// be
																					// bounded
																					// to
																					// the
																					// max
																					// leftover
																					// bit
																					// space
			// crossover fifth component - square colour
			int xoverColor = GrayCoder.decodeGray(executeCrossOver(GrayCoder.encodeGray(aVector[1 + i * 5 + 4]),
					GrayCoder.encodeGray(bVector[1 + i * 5 + 4]), Byte.SIZE));

			xoverVector[1 + i * 5 + 0] = xoverX;
			xoverVector[1 + i * 5 + 1] = xoverY;
			xoverVector[1 + i * 5 + 2] = xoverWidth;
			xoverVector[1 + i * 5 + 3] = xoverHeigh;
			xoverVector[1 + i * 5 + 4] = xoverColor;

		}
		GAImgSolution child = new GAImgSolution(squareNum, maxWidth, maxHeight);
		child.setVector(xoverVector);

		return child;
	}

}
