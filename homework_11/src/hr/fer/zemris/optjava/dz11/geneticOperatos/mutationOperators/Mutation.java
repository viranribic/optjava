package hr.fer.zemris.optjava.dz11.geneticOperatos.mutationOperators;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.geneticAlgorithm.solution.GAImgSolution;
import hr.fer.zemris.optjava.dz11.interfaces.IMutationOp;
import hr.fer.zemris.optjava.dz11.util.GrayCoder;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Mutation implements IMutationOp {

	private IRNG rand;

	public Mutation(IRNG rand) {
		this.rand = rand;
	}

	@Override
	public GASolution<int[]> mutation(GASolution<int[]> child) {
		return strategyC(child);
	}

	private GASolution<int[]> strategyC(GASolution<int[]> child) {
		IRNG rand = RNG.getRNG();
		int numbOfRectangles = (int) ((child.getData().length - 1) / 5);
		int offset = rand.nextInt(0, 25);
		for (int i = 0; i < numbOfRectangles; i++) {
			int randInt = rand.nextInt(0, 10);
			if (randInt == 0) {
				child.getData()[i * 5 + 3] += offset;
				if (child.getData()[i * 5 + 3] > 70) {
					child.getData()[i * 5 + 3] = 70;
				}
			} else if (randInt == 1) {
				child.getData()[i * 5 + 3] -= offset;
				if (child.getData()[i * 5 + 3] < 0) {
					child.getData()[i * 5 + 3] = 0;
				}
			} else if (randInt == 2) {
				child.getData()[i * 5 + 4] += offset;
				if (child.getData()[i * 5 + 4] > 45) {
					child.getData()[i * 5 + 4] = 45;
				}

			} else if (randInt == 3) {
				child.getData()[i * 5 + 4] -= offset;
				if (child.getData()[i * 5 + 4] < 0) {
					child.getData()[i * 5 + 4] = 0;
				}

			} else if (randInt == 4) {
				child.getData()[i * 5 + 1] -= offset;
				if (child.getData()[i * 5 + 1] < 0) {
					child.getData()[i * 5 + 1] = 0;
				}

			} else if (randInt == 5) {
				child.getData()[i * 5 + 1] += offset;
				if (child.getData()[i * 5 + 1] > 200) {
					child.getData()[i * 5 + 1] = 200;
				}

			} else if (randInt == 6) {
				child.getData()[i * 5 + 2] -= offset;
				if (child.getData()[i * 5 + 2] < 0) {
					child.getData()[i * 5 + 2] = 0;
				}

			} else if (randInt == 7) {
				child.getData()[i * 5 + 2] += offset;
				if (child.getData()[i * 5 + 2] > 133) {
					child.getData()[i * 5 + 2] = 133;
				}

			} else if (randInt == 8) {
				child.getData()[i * 5 + 5] += offset;
				if (child.getData()[i * 5 + 5] > 256) {
					child.getData()[i * 5 + 5] = 256;
				}

			} else if (randInt == 9) {
				child.getData()[i * 5 + 5] -= offset;
				if (child.getData()[i * 5 + 5] < 0) {
					child.getData()[i * 5 + 5] = 0;
				}

			}
		}
		return child;
	}

	private GASolution<int[]> strategyB(GASolution<int[]> child) {
		return child;

	}

	private GASolution<int[]> strategyA(GASolution<int[]> child) {
		int[] childVector = child.getData();

		int maxHeight = ((GAImgSolution) child).getMaxHeight();
		int maxWidth = ((GAImgSolution) child).getMaxWidth();

		// process bgc

		childVector[0] = GrayCoder.decodeGray(executeMutation(GrayCoder.encodeGray(childVector[0]), Byte.SIZE));

		// process data

		int squareNum = (int) ((childVector.length - 1) / 5);

		for (int i = 0; i < squareNum; i++) {

			// crossover 1st component - x value
			int xoverX = GrayCoder
					.decodeGray(executeMutation(GrayCoder.encodeGray(childVector[1 + i * 5 + 0]), Integer.SIZE - 1)); // TODO
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
			int xoverY = GrayCoder
					.decodeGray(executeMutation(GrayCoder.encodeGray(childVector[1 + i * 5 + 1]), Integer.SIZE - 1)); // TODO
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
			int xoverWidth = GrayCoder
					.decodeGray(executeMutation(GrayCoder.encodeGray(childVector[1 + i * 5 + 2]), Integer.SIZE - 1)); // TODO
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
			int xoverHeigh = GrayCoder
					.decodeGray(executeMutation(GrayCoder.encodeGray(childVector[1 + i * 5 + 3]), Integer.SIZE - 1)); // TODO
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

			if (xoverX + xoverWidth > maxWidth)
				xoverWidth = maxWidth - xoverX;

			if (xoverY + xoverHeigh > maxHeight)
				xoverHeigh = maxHeight - xoverY;

			// crossover fifth component - square colour
			int xoverColor = GrayCoder
					.decodeGray(executeMutation(GrayCoder.encodeGray(childVector[1 + i * 5 + 4]), Byte.SIZE));

			childVector[1 + i * 5 + 0] = xoverX;
			childVector[1 + i * 5 + 1] = xoverY;
			childVector[1 + i * 5 + 2] = xoverWidth;
			childVector[1 + i * 5 + 3] = xoverHeigh;
			childVector[1 + i * 5 + 4] = xoverColor;

		}

		return child;
	}

	private int executeMutation(int number, int size) {
		int randPos = rand.nextInt(0, size);
		int mask = 0;
		for (int i = 0; i < size; i++) {
			mask = mask << 1;
			if (i == randPos)
				mask = mask | 1;
		}

		return mask ^ number;
	}

}
