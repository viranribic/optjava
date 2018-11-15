package hr.fer.zemris.optjava.dz6.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Class used for parsing the input file into graph nodes.
 * @author Viran
 *
 */
public class TSPInputParser {

	/**
	 * Get the list of nodes as described in the input file.
	 * @param srcFile Path  to file source.
	 * @param candidateListSize Size of the candidate lsit
	 * @return
	 */
	public static List<Node> getGraph(String srcFile) {

		String line;
		List<Node> graphNodes=new LinkedList<Node>();
		
		try (BufferedReader input = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(new FileInputStream(srcFile)), "UTF-8"))) {

			while (true) {
				line = input.readLine();
				if(line==null)
					break;
				if (line.equals(""))
					continue;
				
				if(Character.isAlphabetic(line.charAt(0)))
					continue;
				else{
					String[] values=line.trim().split("\\s+");
					Node node=new Node(Integer.parseInt(values[0]),Double.parseDouble(values[1]),Double.parseDouble(values[2]));
					graphNodes.add(node);
				}

			}

		} catch (IOException e) {

		}
		
		return graphNodes;
	}

	
}
