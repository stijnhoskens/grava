package grava.exceptions;

public class LoopException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Object object;

	public LoopException(Object o) {
		this.object = o;
	}

	public Object getLoopedVertex() {
		return object;
	}

}
