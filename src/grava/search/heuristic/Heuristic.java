package grava.search.heuristic;

public interface Heuristic<V> {

	double h(V vertex, V end);

	double h(V vertex);

}
