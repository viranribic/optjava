package hr.fer.zemris.optjava.rng;

import java.util.Queue;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.interfaces.IGrayScaleProvider;
import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

public class EVOThread1 extends Thread implements IRNGProvider, IGrayScaleProvider {

	private IRNG rng = new RNGRandomImpl();
	private Queue<GASolution<int[]>> evalNeededQueue;
	private Queue<GASolution<int[]>> evalSetQueue;
	private GrayScaleImage im;

	
	public EVOThread1() {
	}

	public EVOThread1(Runnable target,Queue<GASolution<int[]>> evalNeededQueue,Queue<GASolution<int[]>> evalSetQueue) {
		super(target);
		this.evalNeededQueue=evalNeededQueue;
		this.evalSetQueue=evalSetQueue;
	}
	
	public EVOThread1(Runnable target) {
		super(target);
	}

	public EVOThread1(String name) {
		super(name);
	}

	public EVOThread1(ThreadGroup group, Runnable target) {
		super(group, target);
	}

	public EVOThread1(ThreadGroup group, String name) {
		super(group, name);
	}

	public EVOThread1(Runnable target, String name) {
		super(target, name);
	}

	public EVOThread1(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
	}

	public EVOThread1(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
	}

	@Override
	public IRNG getRNG() {
		return rng;
	}

	public Queue<GASolution<int[]>> getEvalNeededQueue(){
		return evalNeededQueue;
	}
	
	public Queue<GASolution<int[]>> getEvalSetQueue(){
		return evalSetQueue;
	}

	@Override
	public GrayScaleImage getGSImg(int width, int height) {
		if(im==null){
			im=new GrayScaleImage(width, height);
		}
		return im;
	}
	
}
