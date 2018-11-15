package hr.fer.zemris.optjava.dz13;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.population.solution.tree.ETreeLabel;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;


public class ProgramWriter {

	private static final int SPACING=2;
	private String outProgPath;
	private SolNode root;
	private List<String> lines = new LinkedList<>();
	
	public ProgramWriter(String outProgPath, GPSolution bestFoundSol) {
		this.outProgPath=outProgPath;
		this.root=bestFoundSol.getRoot();
	}

	public void run() {
		
		runTroughTree(root,0);
		
		Path file = Paths.get(outProgPath);
		try {
			Files. write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	private void runTroughTree(SolNode node, int spacings) {
		String spacing="";
		for(int i=0;i<spacings;i++)
			spacing+=" ";
		
		if(node.label==ETreeLabel.IF_FOOD_AHEAD){
			lines.add(spacing+"ifFoodAhead({");
			runTroughTree(node.children[0],spacings+SPACING);
			lines.add(spacing+"}else{");
			runTroughTree(node.children[1],spacings+SPACING);
			lines.add(spacing+"});");
			
		}else if(node.label==ETreeLabel.PROG_2){
			lines.add(spacing+"Prog2({");
			runTroughTree(node.children[0],spacings+SPACING);
			lines.add(spacing+"},{");
			runTroughTree(node.children[1],spacings+SPACING);
			lines.add(spacing+"});");
			
		}else if(node.label==ETreeLabel.PROG_3){
			lines.add(spacing+"Prog3({");
			runTroughTree(node.children[0],spacings+SPACING);
			lines.add(spacing+"},{");
			runTroughTree(node.children[1],spacings+SPACING);
			lines.add(spacing+"},{");
			runTroughTree(node.children[2],spacings+SPACING);
			lines.add(spacing+"});");
			
		}else if(node.label==ETreeLabel.LEFT){
			lines.add(spacing+"Left();");
			
		}else if(node.label==ETreeLabel.RIGHT){
			lines.add(spacing+"Right();");
			
		}else if(node.label==ETreeLabel.MOVE){
			lines.add(spacing+"Move();");
			
		}
		
	}
}
