package hr.fer.zemris.trisat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * TriSatSolver class for solving 3-sat problem using various algorithm approaches.
 * @author Viran
 *
 */
public class TriSatSolver {
	

	private static int varNum;
	private static int clauseNum;
	private static int algorithmNum;
	private static Clause[] inputClauses;
	
	public static void main(String[] args) {
		
		if(args.length != 2){
			System.out.println("Neispravan broj ulaznih argumenata.\n Program prima dva argumenta:\n\tIndeks zeljenog algoritma.\n\tPutanju do datoteke sa problemom.");
			return;
		}
		
		
		try{
			algorithmNum=Integer.parseInt(args[0]);
		}catch(NumberFormatException e){
			System.out.println(e.getLocalizedMessage());
		}
		
		InputFileParser parser=new InputFileParser(args[1]);
		inputClauses=parser.proccessFile();

		
		switch(algorithmNum){
			case(1):{
				ResultLog log=new SequentialSearchAlgorithm(inputClauses, varNum).run();
				if(log.size()==0)
					System.out.println("null");
				else
					for(BitVector v:log){	//in case we stored multiple vectors
						System.out.println(v);
					}
				break;
			}
			case(2):{
				ResultLog log=new IterateSearchAlgorithm(inputClauses, varNum).run();
				
				if(log.getGlobalOptimum()==false)
					System.out.println("Nije pronadjeno rjesenje koje zadovoljava formulu.");
				else{
					System.out.println("Pronadjeno je rjesenje koje zadovoljava formulu:");
					for(BitVector v:log){
						System.out.println(v);
					}				}
				break;
			}
			case(3):{
				ResultLog log=new ExtIterativeSearchAlgorithm(inputClauses, varNum).run();
				if(log.getGlobalOptimum()==false)
					System.out.println("Nije pronadjeno rjesenje koje zadovoljava formulu.");
				else{
					System.out.println("Pronadjeno je rjesenje koje zadovoljava formulu:");
					for(BitVector v:log){
						System.out.println(v);
					}				}
				break;
			}
			default:{
				System.out.println("Redni broj algoritma nije podrzan.");
			}
		}
	}
	
	/**
	 * Parser from extracting information from a file.
	 * @author Viran
	 *
	 */
	private static class InputFileParser{
		
		private String fileSrc;
		
		/**
		 * Assign the path string to this object.
		 * @param string Source file.
		 */
		public InputFileParser(String string) {
			this.fileSrc=string;
		}
		
		/**
		 * Analyse the file and return the clauses contained in it.
		 * @return List of clauses in form appropriate for solving.
		 */
		private Clause[] proccessFile(){
			TSSDataSteam inputData=new TSSDataSteam(fileSrc);
			int clausePos=0;	//index of the next generated clause in the specific array
			while(true){
				String line=inputData.readLine();
				
				if(line.startsWith("c"))
					continue;
				else if(line.startsWith("%"))
					break;
				else if(line.startsWith("p")){
					String[] config=line.split(" ");
					
					varNum=Integer.parseInt(config[2]);
					clauseNum=Integer.parseInt(config[config.length-1]);
					inputClauses=new Clause[clauseNum];
				}else{
					//Expected format: x y z 0
					String[] clauseS=line.trim().split(" ");
					
					int[] indexes=new int[clauseS.length-1]; 
					for(int i=0; i<clauseS.length-1;i++){
						indexes[i]=Integer.parseInt(clauseS[i]);
					}
					
					inputClauses[clausePos++]=new Clause(indexes);
				}
			}
			
			inputData.close();
			
			return inputClauses;
		}
	}
	
	/**
	 * Representation of file from which we extrude the needed data.
	 * @author Viran
	 *
	 */
	private static class TSSDataSteam{
		private BufferedReader inputFile;
		
		/**
		 * Constructor which opens file.
		 * @param src 
		 */
		public TSSDataSteam(String src) {
			try {
				inputFile= new BufferedReader(
						new InputStreamReader(
								new BufferedInputStream(
										new FileInputStream(src)),"UTF-8"));
			} catch (UnsupportedEncodingException|FileNotFoundException e) {
				e.getLocalizedMessage();
				System.exit(-1);
			} ;
		}
		
		/**
		 * Reads one line from the file.
		 * @return The next line in file.
		 */
		public String readLine(){
			if(inputFile==null){
				throw new NullPointerException("Zadana datoteka nije uspjesno otvorena");
			}
			
			try {
				return inputFile.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * Close this file.
		 */
		public void close(){
			try {
				inputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
