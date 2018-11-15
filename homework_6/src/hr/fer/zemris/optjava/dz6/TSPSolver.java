package hr.fer.zemris.optjava.dz6;

import java.util.List;

import hr.fer.zemris.optjava.dz6.algorithm.MinMaxAntSysAlg;
import hr.fer.zemris.optjava.dz6.algorithm.util.domain.AntAlgDomain;
import hr.fer.zemris.optjava.dz6.util.Node;
import hr.fer.zemris.optjava.dz6.util.TSPInputParser;

/**
 * Class built for solving the travelling salesman problem using min max ant
 * system algorithm.
 * 
 * @author Viran
 *
 */
public class TSPSolver {

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Potrebno unjeti 4 argumenta: \n" + "1. Datoteka s opisom TPS problema\n" + ""
					+ "2. veličina liste kandidata\n" + "" + "3. broj mrava u koloniji\n" + ""
					+ "4. Maksimalni broj generacija.\n");
			return;
		}

		String srcFile = args[0];
		int candidateListSize = Integer.parseInt(args[1]);
		int antNum = Integer.parseInt(args[2]);
		int maxIter = Integer.parseInt(args[3]);

		List<Node> graphNodes = TSPInputParser.getGraph(srcFile);
		AntAlgDomain antDomain = new AntAlgDomain(graphNodes, candidateListSize);
		MinMaxAntSysAlg algorithm = new MinMaxAntSysAlg(antDomain, antNum, maxIter);
		algorithm.run();
	}
}
