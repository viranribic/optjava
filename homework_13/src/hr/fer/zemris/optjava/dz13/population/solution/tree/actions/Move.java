package hr.fer.zemris.optjava.dz13.population.solution.tree.actions;

import hr.fer.zemris.optjava.dz13.population.solution.tree.ETreeLabel;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;
import hr.fer.zemris.optjava.dz13.problemComponents.World;

public class Move extends SolNode {

	public Move(int atDepth) {
		this.label=ETreeLabel.MOVE;
		this.depths=new int [0];
		this.children=new SolNode[0];
		this.thisDepth=atDepth;
		this.nodesBeneathCount=0;

	}
	@Override
	public void evaluate(World w) {
		w.move();
	}

	public SolNode duplicate(){
		Move clone = new Move(thisDepth);
		for(int i=0;i<children.length;i++){
			clone.children[i]=this.children[i].duplicate();
		}
		clone.depths=this.depths.clone();
		clone.nodesBeneathCount=this.nodesBeneathCount;
		return clone;
	}
	
}
