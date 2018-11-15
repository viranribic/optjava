package hr.fer.zemris.optjava.dz13.population.solution.tree;

import hr.fer.zemris.optjava.dz13.interfaces.ISolNode;

public abstract class SolNode implements ISolNode{

	public int thisDepth;
	public ETreeLabel label;
	public SolNode[] children;
	public int[] depths;
	public int nodesBeneathCount;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<thisDepth;i++)
			sb.append("-");
		sb.append(thisDepth+" | "+labelToString(this.label)+" | "+this.nodesBeneathCount+" | ");
		for(int i=0;i<depths.length;i++)
			sb.append("["+depths[i]+"]");
		sb.append("\n");
		for(int i=0;i<children.length;i++)
			sb.append(children[i]);
			//sb.append((children[i]!=null)?children[i]:"");
		return sb.toString();
	}
	
	public String labelToString(ETreeLabel label){
		if(label==ETreeLabel.IF_FOOD_AHEAD){
			return "IfFoodAhead()";
		}else if(label==ETreeLabel.PROG_2){
			return "Prog2()";
		}else if(label==ETreeLabel.PROG_3){
			return "Prog3()";
		}else if(label==ETreeLabel.LEFT){
			return "Left()";
		}else if(label==ETreeLabel.RIGHT){
			return "Right()";
		}else if(label==ETreeLabel.MOVE){
			return "Move()";
		}else 
			return "";
	}

	public abstract SolNode duplicate();
	
}
