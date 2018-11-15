package hr.fer.zemris.optjava.dz11;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.optjava.dz11.geneticAlgorithm.population.MulThreadGA2;
import hr.fer.zemris.optjava.dz11.geneticAlgorithm.solution.GAImgSolution;
import hr.fer.zemris.optjava.dz11.interfaces.IOptAlgorithm;

public class Pokretac2 {

	public static void main(String[] args) {
		if(args.length!=7){
			System.out.println("Treba unjeti 7 parametara:");
			return;
		}
		String pngSrcPath=args[0];
		int sqrNum=Integer.parseInt(args[1]);
		int popSize=Integer.parseInt(args[2]);
		int maxIter=Integer.parseInt(args[3]);
		double minFit=Double.parseDouble(args[4]);
		String optParamFilePath=args[5];
		String resPicFilePath=args[6];
		
		GrayScaleImage img=null;
		try {
			img = GrayScaleImage.load(new File(pngSrcPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IGAEvaluator<int[]> function=new Evaluator(img);
			
		IOptAlgorithm<GASolution<int[]>> algorithm=new MulThreadGA2(popSize,minFit,maxIter,function,pngSrcPath,sqrNum);
		GASolution<int[]> bestSolution=algorithm.run();
		
		saveParameters(optParamFilePath,bestSolution);
		savePicture(resPicFilePath,function,(GAImgSolution) bestSolution);
	}

	private static void savePicture(String path,IGAEvaluator<int[]> function, GAImgSolution bestSolution) {
		GrayScaleImage imgOut=new GrayScaleImage(bestSolution.getMaxWidth(), bestSolution.getMaxHeight());
		((Evaluator)function).draw(bestSolution, imgOut);
		try {
			imgOut.save(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void saveParameters(String path,GASolution<int[]> bestSolution) {
		try {

			
			File file = new File(path);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i=0;i<bestSolution.getData().length;i++)
				bw.write(bestSolution.getData()[i]+"\n");
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
