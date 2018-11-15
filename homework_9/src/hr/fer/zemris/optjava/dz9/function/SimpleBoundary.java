package hr.fer.zemris.optjava.dz9.function;

import hr.fer.zemris.optjava.dz9.interfaces.IBoundary;

/**
 * SimpleBoundary class for testing if a number is contained in the defined
 * interval.
 * 
 * @author Viran
 *
 */
public class SimpleBoundary implements IBoundary {

	private double min;
	private double max;
	private boolean maxInclusive;
	private boolean minInclusive;

	/**
	 * Simple boundary constructor.
	 * 
	 * @param min
	 *            Lower boundary.
	 * @param max
	 *            Upper boundary.
	 * @param minInclusive
	 *            Lower boundary inclusive.
	 * @param maxInclusive
	 *            Upper boundary inclusive.
	 */
	public SimpleBoundary(double min, double max, boolean minInclusive, boolean maxInclusive) {
		this.max = max;
		this.min = min;
		this.maxInclusive = maxInclusive;
		this.minInclusive = minInclusive;
	}

	@Override
	public boolean isContained(double x) {
		if (minInclusive && maxInclusive)
			return (min <= x && x <= max) ? true : false;
		else if (minInclusive && !maxInclusive)
			return (min <= x && x < max) ? true : false;
		else if (!minInclusive && maxInclusive)
			return (min < x && x <= max) ? true : false;
		else
			return (min < x && x < max) ? true : false;

	}

	@Override
	public double getMin() {
		return min;
	}

	@Override
	public double getMax() {
		return max;
	}

}
