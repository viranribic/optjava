package hr.fer.zemris.optjava.dz4.part2.solution;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import hr.fer.zemris.optjava.dz4.part2.solution.BoxFillSolution.BoxFillBucket.BoxFillStick;

/**
 * BoxFill solution class.
 * 
 * @author Viran
 *
 */
public class BoxFillSolution {
	public LinkedList<BoxFillBucket> container = new LinkedList<>();
	public double fitness;

	/**
	 * Get this solution fitness.
	 * 
	 * @return Solution fitness.
	 */
	public double getFitness() {
		return fitness;
	}

	/**
	 * Remove any empty buckets.
	 */
	public void removeEmptyBuckets() {
		for (int i = 0; i < container.size(); i++) {
			if (container.get(i).fill == 0)
				container.remove(i);
		}
	}

	/**
	 * Get the number of bins this solution has.
	 * 
	 * @return Number of bins contained.
	 */
	public int getNumOfBins() {
		return container.size();
	}

	/**
	 * Duplicate this solution.
	 * 
	 * @return Clone of this solution.
	 */
	public BoxFillSolution duplicate() {
		BoxFillSolution clone = new BoxFillSolution();
		clone.container = new LinkedList<>();
		for (BoxFillBucket bucket : this.container) {
			clone.container.add(bucket.duplicate());
		}
		clone.fitness = this.fitness;
		return clone;
	}

	/**
	 * Extrude the buckets from this solution.
	 * 
	 * @param startingPoint
	 *            First bucked, starting point, inclusive.
	 * @param endingPoint
	 *            Last bucket, ending point, inclusive.
	 * @return All buckets fount in this interval.
	 */
	public BoxFillBucket[] getBuckets(int startingPoint, int endingPoint) {
		if (startingPoint < 0 || endingPoint > container.size() || endingPoint < startingPoint)
			throw new IllegalArgumentException("Bad getBuckets method starting and ending point.");
		BoxFillBucket[] bucketsFromTo = new BoxFillBucket[endingPoint - startingPoint + 1];
		for (int i = startingPoint, j = 0; i <= endingPoint; i++, j++) {
			bucketsFromTo[j] = this.container.get(i).duplicate();
		}
		return bucketsFromTo;
	}

	/**
	 * Insert the list of buckets from the given position.
	 * 
	 * @param insertingBuckets
	 *            Buckets list.
	 * @param startingPoint
	 *            Position on which we insert buckets.
	 */
	public void insertAtCrossoverPoint(BoxFillBucket[] insertingBuckets, int startingPoint) {
		LinkedList<BoxFillStick> unassignedSticks = new LinkedList<>();
		HashSet<String> labelsToBeDestroyed = new HashSet<>();
		// find the duplicated sticks
		for (int i = 0; i < insertingBuckets.length; i++) {
			labelsToBeDestroyed.addAll(insertingBuckets[i].getContainedLabels());
		}

		// add the buckets where they need to be
		for (int i = 0, j = startingPoint; i < insertingBuckets.length; i++, j++) {
			container.add(j, insertingBuckets[i]);
		}

		// remove
		for (int i = 0; i < container.size(); i++)
			if (i > startingPoint && i < (startingPoint + insertingBuckets.length))
				continue;
			else {
				if (container.get(i).hasAny(labelsToBeDestroyed))
					unassignedSticks.addAll(container.get(i).recoverAndDelete());
			}
		this.removeEmptyBuckets();

		// assign the unassignedSticks
		if(unassignedSticks.size()!=0)
			reinsertSticks(unassignedSticks);
	}

	/**
	 * Destroy the current bin configuration at given the given index,
	 * preserving the elements contained.
	 * 
	 * @param atPosition
	 *            Bucked at position we want to destroy.
	 * @return The unassigned sticks remained as the result of bin destruction.
	 */
	public LinkedList<BoxFillStick> destroyBin(int atPosition) {
		LinkedList<BoxFillStick> unassignedSticks = container.get(atPosition).recoverAndDelete();
		this.removeEmptyBuckets();
		return unassignedSticks;
	}

	/**
	 * Reinsert all unassigned sticks.
	 * 
	 * @param unassignedSticks
	 *            unassignedSticks
	 * @return True if the operation succeeded, false otherwise.
	 */
	public void reinsertSticks(LinkedList<BoxFillStick> unassignedSticks) {
		int positionsPassed=0;
		while (true) {
			BoxFillStick unassignedStick = unassignedSticks.get(positionsPassed++);
			LinkedList<BoxFillStick> moreUnassigned=reinsertStickToContaier(unassignedStick, this.container);
			if(moreUnassigned!=null)
				unassignedSticks.addAll(moreUnassigned);
			if(positionsPassed==unassignedSticks.size())
				break;
		}
		
		//add the rest 
		for(int i=0;i<unassignedSticks.size();i++)
			fitFirstDescendingHeuristic(unassignedSticks.get(i),this.container);
	}

	/**
	 * Reinsert an unassigned stick into the given container.
	 * 
	 * @param unassignedStick
	 *            Unassigned stick.
	 * @param container
	 *            Container to which we add the stick.
	 */
	private LinkedList<BoxFillStick> reinsertStickToContaier(BoxFillStick unassignedStick, LinkedList<BoxFillBucket> container) {
		for (int i = 0; i < container.size(); i++) {
			BoxFillBucket curBucket = container.get(i);
			LinkedList<BoxFillStick> unassigned= replacement(unassignedStick, curBucket);
			if (unassigned.size()!=0)
				return unassigned;
		}
		return null;
	}

	/**
	 * Try to find the first bucket in which the stick can fit, or add it in a newly created one.
	 * @param unassignedStick Stick we want to add.
	 * @param container Container in which we add the stick.
	 */
	private void fitFirstDescendingHeuristic(BoxFillStick unassignedStick, LinkedList<BoxFillBucket> container) {
		for(int i=0;i<container.size();i++){
			BoxFillBucket curBucket= container.get(i);
			boolean additionSuccess=curBucket.addStick(unassignedStick);
			if(additionSuccess)
				return;
		}
		BoxFillBucket newBucket=new BoxFillBucket(container.get(0).maxCapacity);
		newBucket.addStick(unassignedStick);
		container.add(newBucket);
		
	}

	/**
	 * Try to replace the given element with the one in the current box.
	 * 
	 * @param unassignedStick
	 *            Unassigned stick.
	 * @param curBucket
	 *            Bucket in which we want to make the replacement.
	 * @return True if the operation succeeded, false otherwise.
	 */
	private LinkedList<BoxFillStick> replacement(BoxFillStick unassignedStick, BoxFillBucket curBucket) {
		curBucket.sortThisBucketDesc();
		LinkedList<BoxFillStick> unassignedSticks= new LinkedList<>();
		
		//try substituting with one
		for(int i=0;i<curBucket.sticks.size();i++){
			BoxFillStick curStick=curBucket.sticks.get(i);
			int curStickLength=(int)curStick.length;
			if(unassignedStick.length>curStickLength && (curBucket.size()-curStickLength+unassignedStick.length)<=curBucket.maxCapacity){
				curBucket.removeStick(curStick.label);
				curBucket.addStick(unassignedStick);
				unassignedSticks.add(curStick);
			}
		}

		return unassignedSticks;
	}

	/**
	 * BoxFillBucket represents a single gene in the chromosome. It is a bounded
	 * container for the BoxFillStick class.
	 * 
	 * @author Viran
	 *
	 */
	public static class BoxFillBucket implements Comparable<BoxFillBucket> {
		public LinkedList<BoxFillStick> sticks = new LinkedList<>();
		private double maxCapacity;
		private double fill;
		
		/**
		 * Sort this bucket in descending order.
		 */
		public void sortThisBucketDesc() {
			sortThisBucketAsc();
			Collections.reverse(sticks);
		}

		/**
		 * Return the size of this bucket measured in partial lengths.
		 * @return Total length of this bucker.
		 */
		public int size() {
			return (int)(fill*maxCapacity);
		}

		/**
		 * Sort this bucket in ascending order.
		 */
		public void sortThisBucketAsc() {
			Collections.sort(sticks);

		}

		/**
		 * BoxFillBucket constructor.
		 * 
		 * @param maxCapacity
		 *            Maximal bucket capacity.
		 */
		public BoxFillBucket(double maxCapacity) {
			this.maxCapacity = maxCapacity;
			this.fill = 0;
		}

		/**
		 * Remove first stick in the bucket.
		 * 
		 * @return Removed stick.
		 */
		public BoxFillStick removeFirstStick() {
			BoxFillStick rmv = sticks.removeFirst();
			fill -= rmv.getLength();
			return rmv;
		}

		/**
		 * Add a new stick to this bucket.
		 * 
		 * @param stick
		 *            Adding element.
		 * @return True if successful, false otherwise.
		 */
		public boolean addStick(BoxFillStick stick) {
			boolean result = false;
			if (fill + stick.getLength() <= maxCapacity) {
				result = sticks.add(stick);
				if (result)
					fill += stick.getLength();
			}
			return result;
		}

		/**
		 * Remove the stick with the given label from this bin.
		 * 
		 * @param inputLabel
		 *            Label of the stick we want to remove.
		 * @return Returns the removed stick.
		 */
		public BoxFillStick removeStick(String inputLabel) {
			for (int i = 0; i < sticks.size(); i++)
				if (sticks.get(i).label.equals(inputLabel)) {
					BoxFillStick rmv = sticks.remove(i);
					fill -= rmv.getLength();
					return rmv;
				}
			return null;
		}

		/**
		 * Get the fill percentage of this bucket.
		 * 
		 * @return Fill percentage.
		 */
		public double filledPercentage() {
			return fill / maxCapacity;
		}

		/**
		 * Recover the original bucket list and clear the bucket.
		 * 
		 * @return All bucket elements in a list.
		 */
		public LinkedList<BoxFillStick> recoverAndDelete() {
			LinkedList<BoxFillStick> recoverAndDelete = new LinkedList<>(sticks);
			this.fill = 0;
			this.sticks = new LinkedList<>();
			return recoverAndDelete;
		}

		@Override
		public int compareTo(BoxFillBucket o) {
			return Double.compare(fill, o.fill);
		}

		/**
		 * Stick representation of a bucket element.
		 * 
		 * @author Viran
		 *
		 */
		public static class BoxFillStick implements Comparable<BoxFillStick> {
			private double length;
			private String label;

			/**
			 * BoxFillStick constructor.
			 * 
			 * @param label
			 *            Label for this stick.
			 * @param length
			 *            Length of this stick.
			 */
			public BoxFillStick(String label, double length) {
				this.label = label;
				this.length = length;
			}

			/**
			 * Get stick length.
			 * 
			 * @return Length.
			 */
			public double getLength() {
				return length;
			}

			/**
			 * Get string label.
			 * 
			 * @return Get label.
			 */
			public String getLabel() {
				return label;
			}

			@Override
			public int compareTo(BoxFillStick o) {
				return Double.compare(this.length, o.length);
			}

			/**
			 * Duplicate this stick.
			 * 
			 * @return Clone of this stick.
			 */
			public BoxFillStick duplicate() {
				return new BoxFillStick(this.label, this.length);
			}
		}

		/**
		 * Duplicate this bucket.
		 * 
		 * @return Clone of this bucket.
		 */
		public BoxFillBucket duplicate() {
			BoxFillBucket clone = new BoxFillBucket(this.maxCapacity);
			clone.maxCapacity = this.maxCapacity;
			clone.fill = this.fill;
			clone.sticks = new LinkedList<>();
			for (BoxFillStick s : this.sticks) {
				clone.sticks.add(s.duplicate());
			}
			return clone;
		}

		/**
		 * Makes a set of labels contained in this bucket.
		 * 
		 * @return Set of contained labels.
		 */
		public HashSet<String> getContainedLabels() {
			HashSet<String> containedLabels = new HashSet<>();
			for (int i = 0; i < sticks.size(); i++) {
				containedLabels.add(sticks.get(i).label);
			}
			return containedLabels;
		}

		/**
		 * Return true if this bucket has any elements contained in the list.
		 * 
		 * @param labels
		 *            List of labels we are interested in finding in this
		 *            bucket.
		 * @return True if the condition is met, false otherwise.
		 */
		public boolean hasAny(HashSet<String> labels) {
			for (String s : labels) {
				for (int i = 0; i < sticks.size(); i++) {
					if (sticks.get(i).equals(s))
						return true;
				}
			}
			return false;
		}

	}

}
