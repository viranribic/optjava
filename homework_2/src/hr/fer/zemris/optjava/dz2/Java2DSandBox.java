package hr.fer.zemris.optjava.dz2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Jama.Matrix;

public class Java2DSandBox {

	
	private static final int IMG_HEIGHT = 400;
	private static final int IMG_WIDTH = 400;

	private static final double MAX_X = 5;
	private static final double MIN_X = -5;
	private static final double MAX_Y = 5;
	private static final double MIN_Y = -5;
	
	
	public static void main(String[] args) {
		BufferedImage img=new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = img.createGraphics();

		
		Matrix grid=new Matrix(IMG_WIDTH,IMG_HEIGHT);
		double maxValue=0,minValue=0;
		IFunction function=new Function1();
		for(int i=0;i<IMG_WIDTH;i++){
			for(int j=0;j<IMG_HEIGHT;j++){
				
				Matrix pos=matrixToGrid(i, j);
				System.out.println(pos.get(0, 0)+" "+pos.get(1, 0));
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
		
		graphics.setColor(Color.GRAY);
		graphics.drawLine(IMG_WIDTH/2, 0, IMG_WIDTH/2, IMG_HEIGHT);
		graphics.drawLine(0, IMG_HEIGHT/2, IMG_WIDTH, IMG_HEIGHT/2);
		
		
		try {
		    // retrieve image
		    File outputfile = new File("saved.png");
		    ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
		    
		}
	}

	private static Matrix matrixToGrid(int i, int j) {
		Matrix pos=new Matrix(2,1);
		double x=2*MAX_X/IMG_WIDTH*i+MIN_X;
		double y=2*MIN_Y/IMG_HEIGHT*j+MAX_Y;
		pos.set(0, 0, x);
		pos.set(1, 0, y);
		return pos;
	}
}
