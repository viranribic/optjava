package test;

import hr.fer.zemris.optjava.rng.EVOThread1;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Test2 {
	public static void main(String[] args) {
		Runnable job = new Runnable() {
			@Override
			public void run() {
				IRNG rng = RNG.getRNG();
				for (int i = 0; i < 20; i++) {
					System.out.println("Cur thread:"+Thread.currentThread()+"\n"+i+"\n"+rng.nextInt(-5, 5)+"\n");
				}
			}
		};
		EVOThread1 thread1 = new EVOThread1(job);
		thread1.start();
		EVOThread1 thread2= new EVOThread1(job);
		thread2.start();
		EVOThread1 thread3 = new EVOThread1(job);
		thread3.start();
		
	}
}