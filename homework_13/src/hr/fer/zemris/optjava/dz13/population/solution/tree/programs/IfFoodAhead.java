package hr.fer.zemris.optjava.dz13.population.solution.tree.programs;

import hr.fer.zemris.optjava.dz13.population.solution.tree.ETreeLabel;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;
import hr.fer.zemris.optjava.dz13.problemComponents.World;

public class IfFoodAhead extends SolNode {

	public IfFoodAhead(int atDepth) {
		this.label=ETreeLabel.IF_FOOD_AHEAD;
		this.depths=new int [2];
		this.children=new SolNode[2];
		this.thisDepth=atDepth;
		this.nodesBeneathCount=0;

	}
	
	@Override
	public void evaluate(World w) {
		if(w.foodAhead()){
			children[0].evaluate(w);
		}else{
			children[1].evaluate(w);			
		}
	}

	public SolNode duplicate(){
		IfFoodAhead clone = new IfFoodAhead(thisDepth);
		for(int i=0;i<children.length;i++){
			clone.children[i]=this.children[i].duplicate();
		}
		clone.depths=this.depths.clone();
		clone.nodesBeneathCount=this.nodesBeneathCount;
		return clone;
	}
}
