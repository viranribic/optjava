package hr.fer.zemris.optjava.dz12.algorithm.population.solution;

import java.util.LinkedList;

import hr.fer.zemris.optjava.dz12.interfaces.IRNG;
import hr.fer.zemris.optjava.dz12.util.random.RNG;

public class CLB {

	private LinkedList<Integer> inputIndexList;
	private LinkedList<Integer> LUT;
	private int indexInSequence;
	private int clbInputs;
	
	private CLB() {
	}
	public int getIndexInSequence() {
		return indexInSequence;
	}

	public void setIndexInSequence(int indexInSequence) {
		this.indexInSequence = indexInSequence;
	}

	public int getClbInputs() {
		return clbInputs;
	}

	public void setClbInputs(int clbInputs) {
		this.clbInputs = clbInputs;
	}

	public CLB(int clbInputs, int index) {
		this.clbInputs=clbInputs;
		this.indexInSequence=index;
		this.inputIndexList=new LinkedList<>();
		this.LUT=new LinkedList<>();
	}
	
	public void init(int numOfVars){
		IRNG rand=RNG.getRNG();
		int lutSize=(int)Math.pow(2, clbInputs);
		//initialise input sources
		for(int i=0;i<clbInputs;i++){
			int num=rand.nextInt(0,numOfVars+indexInSequence);
			inputIndexList.add(num);	//random input from one of the inputs and the previous CLBs
		}
		//initialise LUT values
		for(int i=0;i<lutSize;i++){
			LUT.add(rand.nextInt(0, 2));
		}
	}

	public LinkedList<Integer> getinputIndexList() {
		return inputIndexList;
	}

	public void setinputIndexList(LinkedList<Integer> inputIndexList) {
		this.inputIndexList = inputIndexList;
	}

	public LinkedList<Integer> getLUT() {
		return LUT;
	}

	public void setLUT(LinkedList<Integer> lUT) {
		LUT = lUT;
	}
	
	public CLB duplicate(){
		CLB clone =new CLB();
		clone.clbInputs=this.clbInputs;
		clone.indexInSequence=this.indexInSequence;
		
		clone.inputIndexList=new LinkedList<>();
		clone.LUT=new LinkedList<>();
		
		for(Integer i:this.inputIndexList){
			clone.inputIndexList.add(i);
		}
		for(Integer i:this.LUT){
			clone.LUT.add(i);
		}
		
		return clone;
	}
	
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append("---\n");
		sb.append("CLB:"+indexInSequence+"\n");
		sb.append("Input conf:\n");
		for(Integer i:inputIndexList){
			sb.append(i+" ");
		}
		sb.append("\nLUT conf:\n");
		
		for(Integer i:LUT){
			sb.append(i+" ");
		}
		sb.append("\n---\n");
		return sb.toString();
	}
}
