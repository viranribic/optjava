package hr.fer.zemris.optjava.dz13.algorithm.operators;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.population.solution.tree.ETreeLabel;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;
import hr.fer.zemris.optjava.dz13.util.AlgConst;

public class Crossover {

	private Random rand=new Random();
	
	public GPSolution crossover(GPSolution parentA, GPSolution parentB){
		//------------------------------------------------
		GPSolution child=parentA.duplicate();
		SolNode cRoot=child.getRoot();
		//System.out.println("Child tree:\n"+cRoot+"\n");
		SolNode pBRoot=parentB.getRoot();
		//System.out.println("ParentB tree:\n"+pBRoot+"\n");
		//------------------------------------------------
		
		//----------------------------------------------
		int childMaxDepth=getMaxDepth(cRoot);
		int pareBMaxDepth=getMaxDepth(pBRoot);
		
		//System.out.println("Child depth "+childMaxDepth);
		//System.out.println("ParentB depth "+pareBMaxDepth);
		//-----------------------------------------------
		// Perhaps it would be better to first select the target position in child and then make a subtree to substitute 
		//-----------------------------------------------
		int maxBDepthAllowed=(pareBMaxDepth<AlgConst.TOTAL_MAX_DEPTH-1)?pareBMaxDepth:AlgConst.TOTAL_MAX_DEPTH-1; //?
		//System.out.println("Between "+pareBMaxDepth+" and "+AlgConst.TOTAL_MAX_DEPTH+"-1 chosen is :"+maxBDepthAllowed);
		int randBDepth=rand.nextInt(maxBDepthAllowed-1)+1; //every node will have at least two levels of depth OK checked!
		//System.out.println("Random parent depth chosen:"+randBDepth);
		int targetPDepth=pareBMaxDepth-randBDepth;
		//System.out.println("That makes target depth at: "+targetPDepth);
		SolNode bSubTree=getCopyNode(pBRoot,0,targetPDepth);
		//System.out.println("Finally the subtree is: \n"+bSubTree+"\n");
		
		int maxCDepthAllowed;
		if(childMaxDepth+randBDepth-1<AlgConst.TOTAL_MAX_DEPTH){
			//you can put it anywhere
			maxCDepthAllowed=childMaxDepth;
		}else{
			int correction=(childMaxDepth+randBDepth-1)-AlgConst.TOTAL_MAX_DEPTH;
			maxCDepthAllowed=childMaxDepth-correction;
		}
		//System.out.println("Max depth for child crossover "+maxCDepthAllowed);
		int randCDepth=rand.nextInt(maxCDepthAllowed-1)+1; //every node will have at least two levels of depth OK checked!
		//System.out.println("Random child depth chosen:"+randCDepth);
		int targetCDepth=childMaxDepth-randCDepth;
		//System.out.println("That makes target depth at: "+targetCDepth);
		replaceSubnode(cRoot,bSubTree,0,targetCDepth);
		//System.out.println("Tree after substitution: \n"+cRoot+"\n");
		
		int nodeCount=countNodes(cRoot);
		if(nodeCount<=AlgConst.MAX_NODE_COUNT){
			//ok let it pass
			child.setNodesInTree(nodeCount);
			refreshDepths(cRoot);
			refreshNodeValues(cRoot, 0);
			return child;
		}else
			return null;
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

	private void replaceSubnode(SolNode node, SolNode bCopyNode, int currentDepth,int targetNodeDepth) {
		if(node.thisDepth==targetNodeDepth-1){
			//find a random node and switch the elements
			int randInt=rand.nextInt(node.children.length);
			//System.out.println("\nReplacing node:\n");
			//System.out.println(node.children[randInt]+"\n");
			//System.out.println("\nWith node:\n");
			//System.out.println(bCopyNode+"\n");
			
			node.children[randInt]=bCopyNode;
		}else{
			replaceSubnode(node.children[randProcDir(node,targetNodeDepth-currentDepth)],bCopyNode,currentDepth+1, targetNodeDepth);
		}
		
	}

	private int randProcDir(SolNode node, int targetNodeDepth){
		Set<Integer> dirSet=new HashSet<>();
		for(int i=0;i<node.depths.length;i++)
			if(node.depths[i]>=targetNodeDepth)
				dirSet.add(i);
		LinkedList<Integer> list=new LinkedList<Integer>(dirSet);
		return list.get(rand.nextInt(list.size()));
	}
	
	private int getMaxDepth(SolNode pBRoot) {
		int max=0;
		for(int i=0;i<pBRoot.depths.length;i++)
			if(i==0)
				max=pBRoot.depths[i];
			else
				if(max<pBRoot.depths[i])
					max=pBRoot.depths[i];
		
		return max+1;
	}

	private SolNode getCopyNode(SolNode node, int currentNodeDepth,int targetNodeDepth) {
		if(node.thisDepth==targetNodeDepth){
			return node.duplicate();
		}else{
			return getCopyNode(node.children[randProcDir(node,targetNodeDepth-currentNodeDepth)],currentNodeDepth+1, targetNodeDepth);
		}
	}
}
