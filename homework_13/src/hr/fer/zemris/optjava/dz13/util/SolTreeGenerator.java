package hr.fer.zemris.optjava.dz13.util;

import java.util.Random;

import hr.fer.zemris.optjava.dz13.population.solution.tree.ETreeLabel;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;
import hr.fer.zemris.optjava.dz13.population.solution.tree.actions.Left;
import hr.fer.zemris.optjava.dz13.population.solution.tree.actions.Move;
import hr.fer.zemris.optjava.dz13.population.solution.tree.actions.Right;
import hr.fer.zemris.optjava.dz13.population.solution.tree.programs.IfFoodAhead;
import hr.fer.zemris.optjava.dz13.population.solution.tree.programs.Prog2;
import hr.fer.zemris.optjava.dz13.population.solution.tree.programs.Prog3;

public class SolTreeGenerator {

	private static Random rand = new Random();
	private static int totalNodesProduced = 0;
	private static int maxNodeCount = 0;
	private static int needToMakeNodeCount = 0;

	public static SolNode genRandTree(int maxDepth, boolean isFull, int maxNodeCount) {
		resetTotalNodesCount();
		setMaxNodeCount(maxNodeCount);
		SolNode root = randomFunctionNode(0);
		int curDepth = 0;
		buildTree(root, curDepth, maxDepth, isFull);
		countNodesBeneath(root);
		return root;
	}

	public static int getTotalNodesCount() {
		return totalNodesProduced;
	}

	private static void setMaxNodeCount(int max) {
		maxNodeCount = max;
		needToMakeNodeCount = 1;
	}

	private static void resetTotalNodesCount() {
		totalNodesProduced = 0;
	}

	private static void incrementTotalNodesCount() {
		totalNodesProduced++;
	}

	public static int countNodesBeneath(SolNode node) {
		if (node.children.length == 0) {
			return 1;
		}

		for (int i = 0; i < node.children.length; i++) {
			node.nodesBeneathCount += countNodesBeneath(node.children[i]);
		}
		return node.nodesBeneathCount + 1;

	}

	private static int buildTree(SolNode parentNode, int curDepth, int maxDepth, boolean isFull) {
		// ramped-half-and-half
		if (parentNode.label == ETreeLabel.LEFT || parentNode.label == ETreeLabel.RIGHT
				|| parentNode.label == ETreeLabel.MOVE || curDepth == maxDepth) {
			return 1; // possibly integer -> for max depth beneath this node
		}

		boolean onlyTerminal = false;
		if (curDepth == maxDepth - 1) {
			onlyTerminal = true;
		}

		if (isFull) {
			full(parentNode, curDepth, onlyTerminal);
		} else {
			grow(parentNode, curDepth, onlyTerminal);
		}

		// generate it's children
		int maxDepthBenethe = 0;
		for (int i = 0; i < parentNode.children.length; i++) {
			parentNode.depths[i] = buildTree(parentNode.children[i], curDepth + 1, maxDepth, isFull);
			if (i == 0)
				maxDepthBenethe = parentNode.depths[i];
			else if (maxDepthBenethe < parentNode.depths[i])
				maxDepthBenethe = parentNode.depths[i];
		}
		return maxDepthBenethe + 1;
	}

	private static void full(SolNode parentNode, int curDepth, boolean onlyTerminal) {

		// generate children nodes
		for (int i = 0; i < parentNode.children.length; i++) {
			if (onlyTerminal)
				parentNode.children[i] = randomTerminalNode(curDepth + 1);
			else
				parentNode.children[i] = randomFunctionNode(curDepth + 1);
		}

	}

	private static void grow(SolNode parentNode, int curDepth, boolean onlyTerminal) {

		// generate children nodes
		for (int i = 0; i < parentNode.children.length; i++) {
			if (onlyTerminal)
				parentNode.children[i] = randomTerminalNode(curDepth + 1);
			else
				parentNode.children[i] = randomNode(curDepth + 1);
		}

	}

	private static SolNode randomTerminalNode(int nodeDepth) {
		int choice = rand.nextInt(AlgConst.ACTION_NODES_COUNT);
		
		switch (choice) {
		case 0: {
			incrementTotalNodesCount();
			needToMakeNodeCount--;
			return new Left(nodeDepth);
		}
		case 1: {
			incrementTotalNodesCount();
			needToMakeNodeCount--;
			return new Right(nodeDepth);
		}
		case 2: {
			incrementTotalNodesCount();
			needToMakeNodeCount--;
			return new Move(nodeDepth);
		}

		default: {
			return null;
		}
		}
	}

	private static SolNode randomFunctionNode(int nodeDepth) {
		
		
		if(totalNodesProduced+needToMakeNodeCount==maxNodeCount ){
			return randomTerminalNode(nodeDepth);
		}
		int choice = 0;
		if(totalNodesProduced+needToMakeNodeCount+3<=maxNodeCount)
			choice = rand.nextInt(AlgConst.PROG_NODES_COUNT); 
		else if (totalNodesProduced+needToMakeNodeCount+2<=maxNodeCount) 
			choice = rand.nextInt(AlgConst.PROG_NODES_COUNT-1); 
		else
			return randomTerminalNode(nodeDepth);
		
		switch (choice) {
		case 0: {
			incrementTotalNodesCount();
			needToMakeNodeCount--;
			needToMakeNodeCount+=2;
			return new IfFoodAhead(nodeDepth);
		}
		case 1: {
			incrementTotalNodesCount();
			needToMakeNodeCount--;
			needToMakeNodeCount+=2;
			return new Prog2(nodeDepth);
		}
		case 2: {
			incrementTotalNodesCount();
			needToMakeNodeCount--;
			needToMakeNodeCount+=3;
			return new Prog3(nodeDepth);
		}
		default:
			return null;
		}
	}

	private static SolNode randomNode(int nodeDepth) {

		boolean choice = rand.nextBoolean();
		if(choice)
			return randomFunctionNode(nodeDepth);
		else
			return randomTerminalNode(nodeDepth);
		

	}

	public static SolNode getRandFinalNodes() {
		return randomTerminalNode(0);
	}
}
