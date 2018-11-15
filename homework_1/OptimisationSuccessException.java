package hr.fer.zemris.trisat;

/**
 * Exception used during optimisation evaluation.
 * @author Viran
 *
 */
public class OptimisationSuccessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message; 
	BitVector x;
	
	/**
	 * Constructor for this exception. If the formula requirements are met the fitting vector is sent as a part of this message.
	 * @param message Exception message.
	 * @param x BitVector which meets the formula requirements.
	 */
	
	public OptimisationSuccessException(String message, BitVector x) {
		this.message=message;
		this.x=x;
	}
}
