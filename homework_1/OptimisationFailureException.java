package hr.fer.zemris.trisat;

/**
 * OptimisationFailureException models the behaviour of the algorithm in case of failure.
 * @author Viran
 *
 */
public class OptimisationFailureException extends RuntimeException {

	/**
	 * Constructor with the error message.
	 * @param string Error message.
	 */
	public OptimisationFailureException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;

}
