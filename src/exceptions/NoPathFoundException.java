package exceptions;

public class NoPathFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public NoPathFoundException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void printMessage() {
		System.out.println(message);
	}
	
}
