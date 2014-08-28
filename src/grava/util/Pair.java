package grava.util;

public class Pair<F, S> {

	private final F f;
	private final S s;

	public Pair(F first, S second) {
		f = first;
		s = second;
	}

	public F getFirst() {
		return f;
	}

	public S getSecond() {
		return s;
	}
}
