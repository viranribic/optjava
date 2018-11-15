package hr.fer.zemris.optjava.dz11.geneticAlgorithm.solution;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.constants.ConstantsTask;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class GAImgSolution extends GASolution<int[]> {

	private int maxWidth;
	private int maxHeight;
	
	public GAImgSolution(int squareNumber, int maxWidth, int maxHeight) {
		data=new int[1+5*squareNumber];
		this.maxWidth=maxWidth;
		this.maxHeight=maxHeight;
	}

	@Override
	public GASolution<int[]> duplicate() {
		GAImgSolution clone=new GAImgSolution(this.data.length,this.maxWidth,this.maxHeight);
		clone.fitness=this.fitness;
		clone.data=this.data.clone();
		return clone;
	}

	public void init() {
		IRNG rand=RNG.getRNG();
		data[0]=rand.nextInt(0, ConstantsTask.MAX_GREY_VAL);
		int squareNum=(int)((data.length-1)/5);
		for(int i=0;i<squareNum;i++){
			data[1+i*5+0]=rand.nextInt(0, maxWidth);
			data[1+i*5+1]=rand.nextInt(0, maxHeight);
			
			data[1+i*5+2]=rand.nextInt(data[1+i*5+0], maxWidth);
			data[i*5+3]=rand.nextInt(data[1+i*5+1], maxWidth);
			
			data[1+i*5+4]=rand.nextInt(0, ConstantsTask.MAX_GREY_VAL);
			
			
		}
	}

	public int getMaxWidth(){
		return maxWidth;
	}
	
	public int getMaxHeight(){
		return maxHeight;
	}

	public void setVector(int[] xoverVector) {
		this.data=xoverVector;
		
	}
	
	
}
