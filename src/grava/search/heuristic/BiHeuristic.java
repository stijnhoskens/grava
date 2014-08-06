package grava.search.heuristic;

import java.util.function.ToDoubleBiFunction;

public abstract class BiHeuristic<V> implements Heuristic<V>,
		ToDoubleBiFunction<V, V> {

	private V end;

	public BiHeuristic(V end) {
		this.end = end;
	}

	public double applyAsDouble(V vertex) {
		return applyAsDouble(vertex, end);
	}

}
