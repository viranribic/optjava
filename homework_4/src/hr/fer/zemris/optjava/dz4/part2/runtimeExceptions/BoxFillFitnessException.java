package hr.fer.zemris.optjava.dz4.part2.runtimeExceptions;

/**
 * BoxFill fitness exception.
 * @author Viran
 *
 */
public class BoxFillFitnessException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new runtime exception with null as its detail message. 
	 */
	public BoxFillFitnessException() {
		super();
	}

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 * @param message
	 */
	public BoxFillFitnessException(String message) {
		super(message);
	}
}
