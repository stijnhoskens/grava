package grava.exception;

public class NoSuchVertexException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Object vertex;
	
	public NoSuchVertexException(Object vertex) {
		this.vertex = vertex;
	}
	
	public Object getVertex() {
		return vertex;
	}

}
