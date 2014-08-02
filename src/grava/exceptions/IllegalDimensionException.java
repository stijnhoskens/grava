package grava.exceptions;

public class IllegalDimensionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final int d0;
	private final int d1;

	public IllegalDimensionException(int dim0, int dim1) {
		d0 = dim0;
		d1 = dim1;
	}

	public int getDim0() {
		return d0;
	}

	public int getDim1() {
		return d1;
	}
}
