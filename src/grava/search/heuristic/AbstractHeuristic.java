package grava.search.heuristic;

public abstract class AbstractHeuristic<V> implements Heuristic<V> {

	private V end;

	public AbstractHeuristic(V end) {
		this.end = end;
	}

	public double applyAsDouble(V vertex) {
		return h(vertex, end);
	}

	public abstract double h(V vertex, V end);

}
