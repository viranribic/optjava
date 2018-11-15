package hr.fer.zemris.optjava.dz13;


import hr.fer.zemris.optjava.dz13.algorithm.population.solution.GPSolution;
import hr.fer.zemris.optjava.dz13.population.solution.tree.ETreeLabel;
import hr.fer.zemris.optjava.dz13.population.solution.tree.SolNode;
import hr.fer.zemris.optjava.dz13.problemComponents.Ant;
import hr.fer.zemris.optjava.dz13.problemComponents.EDirection;
import hr.fer.zemris.optjava.dz13.problemComponents.World;

public class Simulation {

	private SolNode root;
	private World world;
	
	private static BooleanW monitor= new BooleanW();
	
	private AntTrailGUI antTrailGUI;
	public Simulation(String mapPath, GPSolution solution) {
		world = new World(mapPath);
		root = solution.getRoot();
	}

	public void run() {

		Thread drawingThread=new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(AntTrailGUI.class);
            }
        };
        drawingThread.start();
        antTrailGUI = AntTrailGUI.waitForAntTrailGUI();
		
		antTrailGUI.monitorLink(monitor);
		while (world.hasMoreIterations() && !world.foundAllPieces()) {
			continueProgram(root);
		}
		
	}

	private void continueProgram(SolNode node) {
		if (node.label == ETreeLabel.IF_FOOD_AHEAD) {
			if (world.foodAhead()) {
				continueProgram(node.children[0]);
			} else {
				continueProgram(node.children[1]);
			}
		} else if (node.label == ETreeLabel.PROG_2) {
			continueProgram(node.children[0]);
			continueProgram(node.children[1]);

		} else if (node.label == ETreeLabel.PROG_3) {
			continueProgram(node.children[0]);
			continueProgram(node.children[1]);
			continueProgram(node.children[2]);

		} else if (node.label == ETreeLabel.LEFT) {
			world.rotateLeft();
			draw();
		} else if (node.label == ETreeLabel.RIGHT) {
			world.rotateRight();
			draw();
		} else if (node.label == ETreeLabel.MOVE) {
			world.move();
			draw();
		}

	}

	private void draw() {
		// TODO Auto-generated method stub
		// get data
		int rows = world.getRowSpan();
		int cols = world.getColSpan();
		int[][] matrix = world.getWorld();
		Ant a = world.getAnt();

		StringBuilder sb=new StringBuilder();
		
		// draw them
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == a.i && j == a.j) {
					if (a.d == EDirection.EAST)
						sb.append("E");
					else if (a.d == EDirection.WEST)
						sb.append("W");
					else if (a.d == EDirection.NORTH)
						sb.append("N");
					else if (a.d == EDirection.SOUTH)
						sb.append("S");
				} else
					sb.append(matrix[i][j]);
			}
			sb.append("\n");
		}
		sb.append("\n");

		antTrailGUI.displayIteration(sb.toString());
		// wait for event to continue
		
		synchronized (monitor) {
			if(monitor.isB())
				System.exit(-1);
			try {
				monitor.wait();
				//System.out.println("Izaso.");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(monitor.isB())
				System.exit(-1);
		}
		
	}
	
	public static class BooleanW{
		private boolean b;
		
		public BooleanW() {
			b=false;
		}

		public boolean isB() {
			return b;
		}

		public void setB(boolean b) {
			this.b = b;
		}
		
		
	}
}
