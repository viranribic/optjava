package data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class InputDataParser{

	public static DataContainer parseFile(String fileSrc, int l, int dataSize) {
		DataContainer data=new DataContainer(l);
		double min=0,max=0;
		LinkedList<Double> elements=new LinkedList<>();
		try(BufferedReader input=new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(fileSrc)), "UTF-8"))){
			
			
			while(true){
				String inputLine=input.readLine();
				if(inputLine==null)
					break;
				double element=Double.parseDouble(inputLine.trim());
				if(elements.size()==0){
					min=element;
					max=element;
				}else{
					if(min>element){
						min=element;
					}
					if(max<element){
						max=element;
					}
				}
				elements.add(element);
			}	
		}
		catch(IOException ignorable )  {
			
		}
		
		if(dataSize==-1)
			dataSize=elements.size();
		if(dataSize<0 || dataSize>elements.size()-l){
			throw new IllegalArgumentException("Learning sample size provided is too large.\nMaximal allowed number of samples is "+(elements.size()-l)+ ".");
		}
		
		for(int i=0;i<dataSize;i++){
			data.add( ( (elements.get(i)-min)/(max-min) )*2-1 );
		}
		
		return data;
	}
	
	
}