package grava.search.heuristic;

public class NoHeuristic<V> implements Heuristic<V> {

	@Override
	public double applyAsDouble(V value) {
		return 0;
	}

}
