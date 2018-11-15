package hr.fer.zemris.optjava.dz13.population.solution.tree.programs;

import hr.fer.zemris.optjava.dz13.population.solution.tree.ETreeLabel;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;
import hr.fer.zemris.optjava.dz13.problemComponents.World;

public class Prog3 extends SolNode {

	public Prog3(int atDepth) {
		this.label=ETreeLabel.PROG_3;
		this.depths=new int [3];
		this.children=new SolNode[3];
		this.thisDepth=atDepth;
		this.nodesBeneathCount=0;

	}
	
	@Override
	public void evaluate(World w) {
		
		children[0].evaluate(w);
		children[1].evaluate(w);
		children[2].evaluate(w);
		
	}

	public SolNode duplicate(){
		Prog3 clone = new Prog3(thisDepth);
		for(int i=0;i<children.length;i++){
			clone.children[i]=this.children[i].duplicate();
		}
		clone.depths=this.depths.clone();
		clone.nodesBeneathCount=this.nodesBeneathCount;
		return clone;
	}
}
