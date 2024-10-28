package exception;

public class LimitLoginException extends Exception{

	private static final long serialVersionUID = 1L;

	private static final String CustomMessage =  "Login error, you have surpassed the 3 attempts.";
	
	// Our custom message in case user fails 3 times in a row.
	public LimitLoginException() {
		super(CustomMessage);
	}
}
