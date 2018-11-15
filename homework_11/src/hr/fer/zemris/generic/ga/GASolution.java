package hr.fer.zemris.generic.ga;


public abstract class GASolution<T> implements Comparable<GASolution<T>> {
	protected T data;
	public double fitness;

	public boolean poisounus=false;
	
	public GASolution() {
	}

	public T getData() {
		return data;
	}

	public abstract GASolution<T> duplicate();

	public abstract void init();
	
	@Override
	public int compareTo(GASolution<T> o) {
		return -Double.compare(this.fitness, o.fitness);
	}
	
	public boolean isPoisounus() {
		return poisounus;
	}
	
	public void makePoisounus(boolean b){
		this.poisounus=b;
	}
}