package hr.fer.zemris.optjava.dz13.algorithm.operators;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.population.solution.tree.ETreeLabel;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;
import hr.fer.zemris.optjava.dz13.util.AlgConst;
import hr.fer.zemris.optjava.dz13.util.SolTreeGenerator;

public class Mutation {

	private Random rand=new Random();
	
	public GPSolution mutate(GPSolution solution) {
		solution=solution.duplicate();
		SolNode sRoot=solution.getRoot();
		//System.out.println("Solution node:\n"+sRoot+"\n");
		
		//data for the main tree
		int solMaxDepth=getMaxDepth(solution);
		//System.out.println("Sol depth "+solMaxDepth);
		
		
		
//		//newly created nodes
//		int treeNodes=solution.getNodesInTree();
//		System.out.println("Nodes in this tree:"+treeNodes);
//		int maxAllowedNodes=AlgConst.MAX_NODE_COUNT-treeNodes;
//		System.out.println("That makes max allowed "+maxAllowedNodes);

		
		
		int randNewDepth=rand.nextInt((AlgConst.TOTAL_MAX_DEPTH-1)-1)+1; //AlgConst.TOTAL_MAX_DEPTH-1 -> max allowed depth, shifted by one 
		//System.out.println("Randomly chosen depth "+randNewDepth);
		SolNode newTree=null;
		int randSDepth;
		if(randNewDepth==1){
			//sub tree is only one node
			newTree=SolTreeGenerator.getRandFinalNodes();
			//System.out.println("Generated tree:\n"+newTree+"\n");
			int newTreeDpeth=getMaxDepth(newTree);
			int depthsLeft=AlgConst.TOTAL_MAX_DEPTH-newTreeDpeth;
			depthsLeft=(depthsLeft<solMaxDepth)?depthsLeft:solMaxDepth;
			randSDepth=rand.nextInt(depthsLeft-1)+1;
		}else{
			//generate subtree
			newTree=SolTreeGenerator.genRandTree(randNewDepth, false, AlgConst.MAX_NODE_COUNT);	
			//System.out.println("Generated tree:\n"+newTree+"\n");
			int newTreeDpeth=getMaxDepth(newTree);
			int depthsLeft=AlgConst.TOTAL_MAX_DEPTH-newTreeDpeth+1;
			depthsLeft=(depthsLeft<solMaxDepth)?depthsLeft:solMaxDepth;
			//if(depthsLeft-1<=0)
			//	System.out.println("HALT3! "+depthsLeft);
			randSDepth=rand.nextInt(depthsLeft-1)+1;
		}
		
	
		insertSubTree(sRoot, 0, randSDepth, newTree);
		
		int nodeCount=countNodes(sRoot);
		if(nodeCount<=AlgConst.MAX_NODE_COUNT){
			//ok let it pass
			solution.setNodesInTree(nodeCount);
			refreshDepths(sRoot);
			refreshNodeValues(sRoot, 0);
			//System.out.println("Tree after insertion:\n"+sRoot+"\n");
			return solution;
		}else{
			//System.out.println("Node count over the top.");
			return null;
		}
	}

	private boolean insertSubTree(SolNode nodeToInsert, int currentDepth, int targetDepth, SolNode subTreeRoot) {
		if(nodeToInsert.thisDepth==targetDepth-1){
			//find a random node and switch the elements
			if(nodeToInsert.children.length==0)
				return false;
			int randInt=rand.nextInt(nodeToInsert.children.length);
			//System.out.println("\nReplacing node:\n");
			//System.out.println(nodeToInsert.children[randInt]+"\n");
			//System.out.println("\nWith node:\n");
			//System.out.println(subTreeRoot+"\n");
			
			nodeToInsert.children[randInt]=subTreeRoot;
			return true;
		}else{
			boolean f=insertSubTree(nodeToInsert.children[randProcDir(nodeToInsert,targetDepth-currentDepth)],currentDepth+1, targetDepth,subTreeRoot);
			if(f){
				return true;
			}else{
				int randInt=rand.nextInt(nodeToInsert.children.length);				
				nodeToInsert.children[randInt]=subTreeRoot;
				return true;
			}
		}
		
	}
	
	private int randProcDir(SolNode node, int targetNodeDepth){
		Set<Integer> dirSet=new HashSet<>();
		for(int i=0;i<node.depths.length;i++)
			if(node.depths[i]>=targetNodeDepth) //must be -1 because of the root node
				dirSet.add(i);
		LinkedList<Integer> list=new LinkedList<Integer>(dirSet);
		if(list.size()==0)
			System.out.println("HALT2!");
		return list.get(rand.nextInt(list.size()));
	}

	private int getMaxDepth(GPSolution solution) {
		SolNode root=solution.getRoot();
		int maxD=0;
		for(int i=0;i<root.depths.length;i++){
			if(i==0)
				maxD=root.depths[i];
			else
				if(root.depths[i]>maxD)
					maxD=root.depths[i];
		}
		
		return maxD+1; 
		/*
		 * 0 | Prog3() | 8 | [2][2][1]
		 * -1 | Prog2() | 2 | [1][1]
		 * --2 | Move() | 0 |
		 * --2 | Right() | 0 |
		 * -1 | Prog3() | 3 | [1][1][1]
		 * --2 | Left() | 0 | 
		 * --2 | Left() | 0 | 
		 * --2 | Move() | 0 | 
		 * -1 | Left() | 0 | 
		 * 
		 *  maxD == 2
		 *  to include the root we add 1 more
		 */
	}

	private int getMaxDepth(SolNode root) {
		int maxD=0;
		for(int i=0;i<root.depths.length;i++){
			if(i==0)
				maxD=root.depths[i];
			else
				if(root.depths[i]>maxD)
					maxD=root.depths[i];
		}
		
		return maxD+1; 
		/*
		 * 0 | Prog3() | 8 | [2][2][1]
		 * -1 | Prog2() | 2 | [1][1]
		 * --2 | Move() | 0 |
		 * --2 | Right() | 0 |
		 * -1 | Prog3() | 3 | [1][1][1]
		 * --2 | Left() | 0 | 
		 * --2 | Left() | 0 | 
		 * --2 | Move() | 0 | 
		 * -1 | Left() | 0 | 
		 * 
		 *  maxD == 2
		 *  to include the root we add 1 more
		 */
	}
	
	private int countNodes(SolNode node) {
		if(node.children.length==0)
			return 1;
		int sum=0;
		for(int i=0;i<node.children.length;i++)
			sum+=countNodes(node.children[i]);
		return sum+1;
	}

	private int refreshDepths(SolNode node) {
		if (node.label == ETreeLabel.LEFT || node.label == ETreeLabel.RIGHT
				|| node.label == ETreeLabel.MOVE ) {
			return 1; // possibly integer -> for max depth beneath this node
		}
		int maxDepthBenethe = 0;
		for (int i = 0; i < node.children.length; i++) {
			node.depths[i] = refreshDepths(node.children[i]);
			if (i == 0)
				maxDepthBenethe = node.depths[i];
			else if (maxDepthBenethe < node.depths[i])
				maxDepthBenethe = node.depths[i];
		}
		return maxDepthBenethe + 1;
	}

	private void refreshNodeValues(SolNode node, int depth) {
		node.thisDepth=depth;
		for(int i=0;i<node.children.length;i++){
			refreshNodeValues(node.children[i], depth+1);
		}
		
	}

}
