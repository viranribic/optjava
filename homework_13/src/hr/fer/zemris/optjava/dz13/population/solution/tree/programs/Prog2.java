package hr.fer.zemris.optjava.dz13.population.solution.tree.programs;

import hr.fer.zemris.optjava.dz13.population.solution.tree.ETreeLabel;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;
import hr.fer.zemris.optjava.dz13.problemComponents.World;

public class Prog2 extends SolNode {

	public Prog2(int atDepth) {
		this.label=ETreeLabel.PROG_2;
		this.depths=new int [2];
		this.children=new SolNode[2];
		this.thisDepth=atDepth;
		this.nodesBeneathCount=0;

	}
	
	@Override
	public void evaluate(World w) {
		
		children[0].evaluate(w);
		children[1].evaluate(w);
		
	}

	public SolNode duplicate(){
		Prog2 clone = new Prog2(thisDepth);
		for(int i=0;i<children.length;i++){
			clone.children[i]=this.children[i].duplicate();
		}
		clone.depths=this.depths.clone();
		clone.nodesBeneathCount=this.nodesBeneathCount;
		return clone;
	}
}
