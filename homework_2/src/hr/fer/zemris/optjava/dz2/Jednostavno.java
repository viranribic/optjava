package hr.fer.zemris.optjava.dz2;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Starting class for numeric optimisation algorithms.
 * @author Viran
 *
 */
public class Jednostavno {
	private static String task;
	private static int maxIterations;
	private static double[] startingPoint;
	private static Random rand=new Random();
	
	private static final int UPPER_DOMAIN_BOUNDERY_X=5;
	private static final int LOWER_DOMAIN_BOUNDERY_X=-5;

	private static final int UPPER_DOMAIN_BOUNDERY_Y=5;
	private static final int LOWER_DOMAIN_BOUNDERY_Y=-5;
	
	
	private static final int IMG_WIDTH = 400;
	private static final int IMG_HEIGHT = 400;

	public static void main(String[] args) {
		
		if(args.length==2){
			task=args[0];
			maxIterations=Integer.parseInt(args[1]);
			startingPoint=new double[2];
			startingPoint[0]=2*UPPER_DOMAIN_BOUNDERY_X*rand.nextDouble()-LOWER_DOMAIN_BOUNDERY_X;
			startingPoint[1]=2*UPPER_DOMAIN_BOUNDERY_Y*rand.nextDouble()-LOWER_DOMAIN_BOUNDERY_Y;
		}
		
		if(args.length==4){
			task=args[0];
			maxIterations=Integer.parseInt(args[1]);
			startingPoint=new double[2];
			startingPoint[0]=Double.parseDouble(args[2]);
			startingPoint[1]=Double.parseDouble(args[3]);
		}

		if(args.length!=2 && args.length!=4){
			System.out.println("Invalid number of input arguments.");
			return;
		}
		
		BufferedImage img =new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		if(task.equals("1a")){
			
			IFunction function=new Function1();
			NumOptAlgorithms.GradientDescent(function, maxIterations, startingPoint,img,true);
			//show result
			
		}else if(task.equals("1b")){
			
			IHFunction function=new Function1();
			NumOptAlgorithms.NewstonsMethod(function, maxIterations,  startingPoint,img,true);
			//show result
		}else if(task.equals("2a")){
			
			IFunction function=new Function2();
			NumOptAlgorithms.GradientDescent(function, maxIterations,  startingPoint,img,true);
			//show result
		}else if(task.equals("2b")){
			
			IHFunction function=new Function2();
			NumOptAlgorithms.NewstonsMethod(function, maxIterations,  startingPoint,img,true);
			//show result
		}
		
	}
}
