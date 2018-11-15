package hr.fer.zemris.optjava.dz13.problemComponents;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.optjava.dz13.util.AlgConst;

public class World {
	private int[][] originalMatrix;
	private int[][] worldMatrix;
	private int rowSpan;
	private int colSpan;
	private int foodCollected;
	private int foodOnMap;
	private int stepsTaken;
	private int maxSteps; 
	private Ant ant;

	public World(String inputFile) {
		try(BufferedReader in=new BufferedReader(new InputStreamReader(new BufferedInputStream( new FileInputStream(inputFile)), "UTF-8"))){
			String dimString=in.readLine();
			String[] dimList=dimString.trim().split("x");
			rowSpan=Integer.parseInt(dimList[0]);
			colSpan=Integer.parseInt(dimList[0]);
			originalMatrix=new int[rowSpan][colSpan];
			worldMatrix=new int[rowSpan][colSpan];
			
			for(int i=0;i<rowSpan;i++){
				String matrixRow=in.readLine();
				for(int j=0;j<colSpan;j++){
					if(matrixRow.charAt(j)=='1')
						worldMatrix[i][j]=originalMatrix[i][j]=1;
					else
						worldMatrix[i][j]=originalMatrix[i][j]=0;
				}
			}
		}catch(IOException ignorable){
			System.out.println("File parsing failed. Exiting.");
			System.exit(-1);
		}
		this.foodCollected=0;
		this.foodOnMap=AlgConst.FOOD_ON_MAP;
		
		this.stepsTaken=0;
		this.maxSteps=AlgConst.AVEAILABLE_STEPS;
		
		this.ant=new Ant();
		ant.initAnt();
	}

	public void move() {
		if(stepsTaken>=maxSteps)
			return;
		int[] next=new int[2];
		calculateNext(ant,next);
		//move
		ant.i=next[0];
		ant.j=next[1];
		
		//if food is on the position pick it up
		if(worldMatrix[ant.i][ant.j]==1){
			worldMatrix[ant.i][ant.j]=0;
			foodCollected++;
		}
		stepsTaken++;
	}
	
	private void calculateNext(Ant a,int[] next) {
		int offsetI=0;
		int offsetJ=0;
		//determinate agent offset
		if(a.d==EDirection.NORTH){
			offsetI=-1;
			offsetJ=0;
		}else if(a.d==EDirection.WEST){
			offsetI=0;
			offsetJ=-1;
		}else if(a.d==EDirection.SOUTH){
			offsetI=1;
			offsetJ=0;
		}else if(a.d==EDirection.EAST){
			offsetI=0;
			offsetJ=1;
		}
		//calculate next position
		next[0]=a.i+offsetI;
		next[1]=a.j+offsetJ;
		if(next[0]==33 || next[1]==33)
			System.out.println("HALT!");
		if(next[0]<0)
			next[0]=rowSpan+next[0];
		else if(next[0]>=rowSpan)
			next[0]=next[0]-rowSpan;
		
		if(next[1]<0)
			next[1]=colSpan+next[1];
		else if(next[1]>=colSpan)
			next[1]=next[1]-colSpan;
		return;
	}

	public void resetWorld(){
		for(int i=0;i<rowSpan;i++)
			for(int j=0;j<colSpan;j++)
				if(originalMatrix[i][j]==1)
				worldMatrix[i][j]=originalMatrix[i][j];
	}

	public boolean foodAhead() {
		int[] next=new int[2];
		calculateNext(ant,next);
		if(worldMatrix[next[0]][next[1]]==1)
			return true;
		else
			return false;
	}

	public int getFoodCollected() {
		return foodCollected;
	}

	public void resetFoodCollected() {
		this.foodCollected = 0;
	}

	public boolean hasMoreIterations() {
		return stepsTaken<maxSteps;
	}

	public boolean foundAllPieces() {
		return foodCollected==foodOnMap;
	}

	public void rotateLeft() {
		if(stepsTaken>=maxSteps)
			return;
		if(ant.d==EDirection.NORTH)
			ant.d=EDirection.EAST;
		else if(ant.d==EDirection.EAST)
			ant.d=EDirection.SOUTH;
		else if(ant.d==EDirection.SOUTH)
			ant.d=EDirection.WEST;
		else if(ant.d==EDirection.WEST)
			ant.d=EDirection.NORTH;
		stepsTaken++;
	}

	public void rotateRight() {
		if(stepsTaken>=maxSteps)
			return;
		if(ant.d==EDirection.NORTH)
			ant.d=EDirection.WEST;
		else if(ant.d==EDirection.EAST)
			ant.d=EDirection.NORTH;
		else if(ant.d==EDirection.SOUTH)
			ant.d=EDirection.EAST;
		else if(ant.d==EDirection.WEST)
			ant.d=EDirection.SOUTH;
		stepsTaken++;
		
	}
	
	public void resetAgent() {
		ant.initAnt();
	}
	
	public void resetStepsTaken() {
		this.stepsTaken=0;
	}
	
	public int getRowSpan(){
		return rowSpan;
	}
	
	public int getColSpan(){
		return colSpan;
	}
	
	public int[][] getWorld(){
		return worldMatrix;
	}
	
	public Ant getAnt(){
		return ant;
	}
	
}
