package hr.fer.zemris.optjava.dz2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import javax.imageio.ImageIO;

import Jama.Matrix;

/**
 * Class containing numerical optimisation algorithms.
 * @author Viran
 *
 */
public class NumOptAlgorithms {

	private static final double EPSILON = 0.0001;
	private static final int IMG_WIDTH = 400;
	private static final int IMG_HEIGHT = 400;
	private static final double MAX_X = 5;
	private static final double MIN_X = -5;
	private static final double MAX_Y = 5;
	private static final double MIN_Y = -5;
	
	
	/**
	 * Gradient descent is a first-order optimisation algorithm. 
	 * To find a local minimum of a function using gradient descent, one takes steps proportional to the negative of the gradient 
	 * (or of the approximate gradient) of the function at the current point. 
	 * If instead one takes steps proportional to the positive of the gradient,
	 * one approaches a local maximum of that function; the procedure is then known as gradient ascent.
	 * <br>Source: <a href="https://en.wikipedia.org/wiki/Gradient_descent"> Wikipedia</a>
	 * @param function Function which optimum we search for.
	 * @param maxIterations Maximal number of iterations this algorithm can afford.
	 * @param startingPoint Starting point of the algorithm.
	 * @param outImg Image on which should be drawn (optional).
	 * @param draw Flag if the drawing process should be executed or not.
	 * @return Resulting vector.
	 */
	public static Matrix GradientDescent(IFunction function, int maxIterations, double[] startingPoint, BufferedImage outImg,boolean draw){
		
		BufferedImage img=outImg;
		
		if(draw)
		img=plotFunction(img,function);			//Add the function to the grid
		
		Matrix x=new Matrix(function.domainDimension(), 1);
		
		
		if(startingPoint!=null){
			for(int i=0;i<startingPoint.length;i++){
				x.set(i, 0, startingPoint[i]);
			}
		}else{
			Random rand=new Random();
			for(int i=0;i<function.domainDimension();i++)
				x.set(i, 0, rand.nextDouble()*(MAX_X)-MIN_Y);  		
		}
		
		Matrix xBefore=x.copy();
		if(draw)
		img=plotTrajectory(img,x,xBefore);	//Write the first trajectory=a dot
		int iteration=maxIterations;
		
		DecimalFormat format=new DecimalFormat("#.##");
		
		while(iteration>0){
			if(stopCondition(x,function))	//provjeri je li dobar uvjet zaustavljanja
				break;
			else{
				Matrix d=function.gradCalc(x).times(-1);
				double lambdaUp=calculateLambdaUp(x,d,function);
				double lambda=bisectionMethod(0, lambdaUp, x, d,function);
				
				//Mark the current position before moving ahead
				xBefore=x.copy();
				
				x=d.times(lambda).plus(x);
				
				//refresh the trajectory
				if(draw)
				img=plotTrajectory(img,x,xBefore);
				//System.out.println("Iteration: "+(maxIterations-iteration+1)+" Value x:"++" Value y:"+format.format(x.get(1, 0)));
				System.out.print("Iteration: "+(maxIterations-iteration+1)+" Value: ");
				for(int i=0;i<x.getRowDimension();i++){
					System.out.print(format.format(x.get(i, 0))+" ");
				}
				System.out.println();
			}
			iteration--;
		}
		if(draw){
			try {
				// retrieve image
				File outputfile = new File("OptimisationTrajectory.png");
				ImageIO.write(img, "png", outputfile);
			} catch (IOException e) {
		    
			}
		}
		
		return x;
	}
	



	
	
	/**
	 * In numerical analysis, Newton's method (also known as the Newton–Raphson method), is a method for finding 
	 * successively better approximations to the roots (or zeroes) of a real-valued function.
	 * <br>Source:<a href="https://en.wikipedia.org/wiki/Newton%27s_method"> Wikipedia </a>
	 * @param function Function which optimum we search for.
	 * @param maxIterations Maximal number of iterations this algorithm can afford.
	 * @param startingPoint Starting point of the algorithm.
	 * @param outImg Image on which should be drawn (optional).
	 * @param draw Flag if the drawing process should be executed or not.
	 * @return Resulting vector.
	 */
	public static Matrix NewstonsMethod(IHFunction function, int maxIterations, double[] startingPoint, BufferedImage outImg, boolean draw){

		BufferedImage img=outImg;
		if(draw)
		img=plotFunction(img,function);			//Add the function to the grid
		
		Matrix x=new Matrix(function.domainDimension(), 1);
		
		if(startingPoint!=null){
			for(int i=0;i<startingPoint.length;i++){
				x.set(i, 0, startingPoint[i]);
			}
		}else{
			Random rand=new Random();
			for(int i=0;i<function.domainDimension();i++)
				x.set(i, 0, rand.nextDouble()*10-5);  		
		}
		
		
		
		Matrix xBefore=x.copy();
		if(draw)
		img=plotTrajectory(img,x,xBefore);	//Write the first trajectory=a dot
		int iteration=maxIterations;
		
		DecimalFormat format=new DecimalFormat("#.##");
		
		
		while(iteration>0){
			if(stopCondition(x,function))	//provjeri je li dobar uvjet zaustavljanja
				break;
			else{
				Matrix d=function.HessianMatrix(x).times(-1).inverse().times(function.gradCalc(x));
				double lambdaUp=calculateLambdaUp(x,d,function);
				double lambda=bisectionMethod(0, lambdaUp, x, d,function);
				//Mark the current position before moving ahead
				xBefore=x.copy();
				
				x=d.times(lambda).plus(x);
				
				//refresh the trajectory
				if(draw)
				img=plotTrajectory(img,x,xBefore);
				System.out.print("Iteration: "+(maxIterations-iteration+1)+" Value: ");
				for(int i=0;i<x.getRowDimension();i++){
					System.out.print(format.format(x.get(i, 0))+" ");
				}
				System.out.println();
			}
			iteration--;
		}
		
		if(draw){
			try {
				// retrieve image
				File outputfile = new File("OptimisationTrajectory.png");
				ImageIO.write(img, "png", outputfile);
			} catch (IOException e) {
		    
			}
		}
		
		return x;
	}
	
	



	/**
	 * Stop condition for this optimisation. Gradient in current point is equal to zero.
	 * @param x Current position on domain.
	 * @param function Function we optimise.
	 * @return True if the stop condition is met, false otherwise.
	 */
	private static boolean stopCondition(Matrix x, IFunction function) {
		Matrix grad=function.gradCalc(x);
		for(int i=0;i<grad.getColumnDimension();i++){
			if(! ( Math.abs(grad.get(i, 0)) <(0+EPSILON)) )
				return false;
		}
			
		return true;
	}




	
	
	/**
	 * Calculates the upper boundary of lambda scalar we use in function optimisation.
	 * @param x Vector of the current best position.
	 * @param d Vector of direction in which the function is decreasing.
	 * @param function Function we optimise.
	 * @return Upper lambda boundary.
	 */
	private static double calculateLambdaUp(Matrix x, Matrix d,IFunction function) {
		double lambdaUp=1;
		while(true){
			
			Matrix dTheta=function.gradCalc(d.times(lambdaUp).plus(x)).transpose().times(d);
			if(dTheta.get(0, 0)>=0)
				return lambdaUp;
			else
				lambdaUp*=2;
		}
	}
	
	/**
	 * The bisection method in mathematics is a root-finding method that repeatedly bisects an interval 
	 * and then selects a subinterval in which a root must lie for further processing. 
	 * It is a very simple and robust method, but it is also relatively slow. Because of this,
	 * it is often used to obtain a rough approximation to a solution which is then used as a starting point for 
	 * more rapidly converging methods.
	 * <br>Source:<a href="https://en.wikipedia.org/wiki/Bisection_method">Wikipedia</a>
	 * 
	 * @param lambdaLow Lower limit for finding the value.
	 * @param lambdaUp Upper limit for finding the value.
	 * @param x Vector of the current best position.
	 * @param d Vector of direction in which the function is decreasing.
	 * @param function Function we optimise.
	 * @return The found value.
	 */
	private static double bisectionMethod(double lambdaLow, double lambdaUp, Matrix x, Matrix d, IFunction function){
		//Korak 0: postavi k=0 postavi [lambda]l=[lambdaLower] te [lambda]u=[lambdaUpper]
		//Korak k: postavi [lambda*]=([lambda]l+[lambda]u)/2 i izracunaj d fi([lambda])/d[lambda] u [lambda*]
		//		Ako je der >0 redefiniraj [lambda]u=[lambda], k=k+1
		//		Ako je der <0 redefiniraj [lambda]l=[lambda], k=k+1
		//		Ako je der cca 0 -> stani jer smo dosli do min dovoljno blizu
		
		double lambResult=0;
		while(true){
			lambResult=(lambdaLow+lambdaUp)/2;
			//d {THETA(lambda)}/d{lambda}
			Matrix dTheta=function.gradCalc(d.times(lambResult).plus(x)).transpose().times(d);
			double theta=dTheta.get(0, 0);
			if(theta>0+EPSILON){
				lambdaUp=lambResult;
			}else if(theta<0-EPSILON){
				lambdaLow=lambResult;
			}else{
				return lambResult;
			}
			
		}
	}
	
	
	
	/**
	 * Plot the scalar function on a 2D field using colours as function value representation.
	 * @param img Image on which we plot.
	 * @param function Function we plot.
	 * @return BufferedIMage object.
	 */
	private static BufferedImage plotFunction(BufferedImage img,
			IFunction function) {
		Graphics2D graphics = img.createGraphics();

		
		Matrix grid=new Matrix(IMG_WIDTH,IMG_HEIGHT);
		double maxValue=0,minValue=0;
		for(int i=0;i<IMG_WIDTH;i++){
			for(int j=0;j<IMG_HEIGHT;j++){
				
				Matrix pos=matrixToGrid(i, j);
				
				double f= function.functionCalc(pos);
				if(i==0 && j==0){
					maxValue=f;
					minValue=f;
				}else{
					if(maxValue<f)
						maxValue=f;
					if(minValue>f)
						minValue=f;
				}
				
				grid.set(i,j,f);
			}
		}
		
		
		double funcValRange=maxValue-minValue;
		int red=0,green=0,blue=0;
		
		for(int i=0;i<IMG_WIDTH;i++){
			for(int j=0;j<IMG_HEIGHT;j++){
				double f=grid.get(i, j);
				//scale f-minValue in comparison to range
				//divide the range of function into 5 sections
				//	min VAL: 255-0-0
				//	sec1: inc g	*initsec: 255-0-0
				//	sec2: dec r	*initsec: 255-255-0
				//	sec3: inc b	*initsec: 0-255-0
				//	sec4: dec g	*initsec: 0-255-255
				//	sec5: inc r	*initsec: 0-0-255
				//	max VAL: 128-0-255
				//	total of 1148 values
				//
				//	
				//
				
				if((f-minValue)>0 && (f-minValue)<funcValRange *0.2){
					//inc g
					red=255;
					green=0;
					blue=0;
					
					double scaledF=f-0;
					double scaleRGB=scaledF/(funcValRange *0.2)*255;
					
					green+=(int)scaleRGB;
				}else if((f-minValue)>funcValRange *0.2 && (f-minValue)<funcValRange *0.4){
					//dec r
					red=255;
					green=255;
					blue=0;
					
					double scaledF=f-funcValRange *0.2;
					double scaleRGB=scaledF/(funcValRange *0.2)*255;
					
					red-=(int)scaleRGB;
				}else if((f-minValue)>funcValRange *0.4 && (f-minValue)<funcValRange *0.6){
					//inc b
					red=0;
					green=255;
					blue=0;
					
					double scaledF=f-funcValRange *0.4;
					double scaleRGB=scaledF/(funcValRange *0.2)*255;
					
					blue+=(int)scaleRGB;
				}else if((f-minValue)>funcValRange *0.6 && (f-minValue)<funcValRange *0.8){
					//dec g
					red=0;
					green=255;
					blue=255;
					
					double scaledF=f-funcValRange *0.6;
					double scaleRGB=scaledF/(funcValRange *0.2)*255;
					
					green-=(int)scaleRGB;
				}else if((f-minValue)>funcValRange *0.8 && (f-minValue)<funcValRange *1){
					//inc r
					red=0;
					green=0;
					blue=255;
					
					double scaledF=f-funcValRange *0.8;
					double scaleRGB=scaledF/(funcValRange *0.2)*128;
					
					red+=(int)scaleRGB;
				}
				
				int rgb = red;			//red value
				rgb = (rgb << 8) + green;	//green value	
				rgb = (rgb << 8) + blue;	//blue value
				
				img.setRGB(i, j,rgb);
				
			}
		}
		//ovo bi mozda trebao dodati na kraj.
		graphics.setColor(Color.BLACK);
		graphics.drawLine(IMG_WIDTH/2, 0, IMG_WIDTH/2, IMG_HEIGHT);
		graphics.drawLine(0, IMG_HEIGHT/2, IMG_WIDTH, IMG_HEIGHT/2);
		return img;
	}
	
	/**
	 * Convert a matrix coordinate to a point in 2-dimensional field.
	 * @param i Matrix row coordinate.
	 * @param j Matrix column coordinate.
	 * @return 2-dimensional field representation to the matrix position.
	 */
	private static Matrix matrixToGrid(int i, int j) {
		Matrix pos=new Matrix(2,1);
		double x=2*MAX_X/IMG_WIDTH*i+MIN_X;
		double y=2*MIN_Y/IMG_HEIGHT*j+MAX_Y;
		pos.set(0, 0, x);
		pos.set(1, 0, y);
		return pos;
	}
	
	/**
	 * Convert grid coordinates to matrix position. 
	 * @param x Grid coordinates, double values.
	 * @return Matrix position, integer values.
	 */
	private static double[] gridToMatrix(Matrix x) {
		double[] result=new double[x.getRowDimension()];
		result[0]=x.get(0, 0)-MIN_X;
		result[0]=IMG_WIDTH/MAX_X/2*result[0];
		result[1]=x.get(1, 0)-MAX_Y;
		result[1]=IMG_HEIGHT/MIN_Y/2*result[1];
		return result;
	}
	
	/**
	 * Draw the trajectory on the output image.
	 * @param img Output image.
	 * @param x Current position.
	 * @param xBefore Last position.
	 * @return BufferedImage object.
	 */
	private static BufferedImage plotTrajectory(BufferedImage img, Matrix x,
			Matrix xBefore) {
		Graphics2D graphics = img.createGraphics();
		
		double[] pt1=gridToMatrix(x);
		double[] pt2=gridToMatrix(xBefore);
		
		int x1=(int)pt1[0];
		int y1=(int)pt1[1];
		int x2=(int)pt2[0];
		int y2=(int)pt2[1];
		
		graphics.setColor(Color.GRAY);
		graphics.drawLine(x1, y1, x2, y2);
		return img;
	}
}
