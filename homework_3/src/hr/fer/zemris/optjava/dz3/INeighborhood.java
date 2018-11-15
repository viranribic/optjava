package hr.fer.zemris.optjava.dz3;

/**
 * Interface for generating a neighbourhood of a object.
 * @author Viran
 *
 * @param <T> Type of object which neighbourhood we generate.
 */
public interface INeighborhood<T extends SingleObjectSolution> {
	/**
	 * Generates a random neighbour of the given value.
	 * @param value Element which neighbour we want ot find.
	 * @return Randomly selected neighbour.
	 */
	public T randomNeighbour(T value);
}
