package hr.fer.zemris.optjava.dz12;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import hr.fer.zemris.optjava.dz12.algorithm.CLBAlgorithm;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolution;
import hr.fer.zemris.optjava.dz12.algorithm.population.solution.CLBSolutionPack;
import hr.fer.zemris.optjava.dz12.util.threads.EVOThread;

public class CLBSolver {

	public static IntMonitor gBestFound=new IntMonitor();
	public static CLBSolution gBest=new CLBSolution();
	//
	public static final int THREAD_NUM=4;
	
	public static void main(String[] args) {
		if(args.length!=3){
			System.out.println("Broj ulaznih parametara mora biti 3:\n\tBroj ulaza CLDova\n\tMaksimalniBrojCLBova\n\tLogicka formula\n");
			return;
		}
		int clbInputNum=Integer.parseInt(args[0]);
		int clbMaxNum=Integer.parseInt(args[1]);
		String logicFunction=args[2];
		
		Queue<CLBSolutionPack> queue12=new LinkedBlockingQueue<>();
		Queue<CLBSolutionPack> queue23=new LinkedBlockingQueue<>();
		Queue<CLBSolutionPack> queue34=new LinkedBlockingQueue<>();
		Queue<CLBSolutionPack> queue41=new LinkedBlockingQueue<>();
		
		EVOThread[] threads=new EVOThread[THREAD_NUM];
		
		threads[0]=new EVOThread(new CLBAlgorithm(clbInputNum,clbMaxNum,logicFunction), queue41, queue12,gBestFound,gBest);
		threads[1]=new EVOThread(new CLBAlgorithm(clbInputNum,clbMaxNum,logicFunction), queue12, queue23,gBestFound,gBest);
		threads[2]=new EVOThread(new CLBAlgorithm(clbInputNum,clbMaxNum,logicFunction), queue23, queue34,gBestFound,gBest);
		threads[3]=new EVOThread(new CLBAlgorithm(clbInputNum,clbMaxNum,logicFunction), queue34, queue41,gBestFound,gBest);
		
		threads[0].setName("EVOThread 1");
		threads[1].setName("EVOThread 2");
		threads[2].setName("EVOThread 3");
		threads[3].setName("EVOThread 4");
		
		
		threads[0].start();
		threads[1].start();
		threads[2].start();
		threads[3].start();
		
		synchronized (gBestFound) {
			while(gBestFound.getValue()!=THREAD_NUM){
				try {
					gBestFound.wait();
				} catch (InterruptedException e) {
					// thread interrupted
					e.printStackTrace();
				}
			}
		}
		
		try {
			threads[0].join();
			threads[1].join();
			threads[2].join();
			threads[3].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(gBest);
		return;
	}
	
	public static class IntMonitor{
		private int i;
		
		public void inc(){
			i++;
		}
		
		public int getValue(){
			return i;
		}
	}
}
