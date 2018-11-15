package hr.fer.zemris.optjava.rng;

import java.util.Queue;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.optjava.dz11.interfaces.IGrayScaleProvider;
import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

public class EVOThread2 extends Thread implements IRNGProvider, IGrayScaleProvider {

	private IRNG rng = new RNGRandomImpl();
	private Queue<Integer> taskQueue;
	private Queue<GASolution<int[]>> childQueue;
	private GrayScaleImage im;
	private IGAEvaluator<int[]> evaluator;	//TODO remove or implement
	
	
	public EVOThread2() {
	}

	public EVOThread2(Runnable target,Queue<Integer> taskQueue,Queue<GASolution<int[]>> childQueue) {
		super(target);
		this.taskQueue=taskQueue;
		this.childQueue=childQueue;
	}
	
	public EVOThread2(Runnable target) {
		super(target);
	}

	public EVOThread2(String name) {
		super(name);
	}

	public EVOThread2(ThreadGroup group, Runnable target) {
		super(group, target);
	}

	public EVOThread2(ThreadGroup group, String name) {
		super(group, name);
	}

	public EVOThread2(Runnable target, String name) {
		super(target, name);
	}

	public EVOThread2(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
	}

	public EVOThread2(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
	}

	@Override
	public IRNG getRNG() {
		return rng;
	}

	public Queue<Integer> getTaskQueue(){
		return taskQueue;
	}
	
	public Queue<GASolution<int[]>> getChildQueue(){
		return childQueue;
	}

	@Override
	public GrayScaleImage getGSImg(int width, int height) {
		if(im==null){
			im=new GrayScaleImage(width, height);
		}
		return im;
	}

	public IGAEvaluator<int[]> getPerosnalEvaluator() {
		return evaluator;
	}
	
}
