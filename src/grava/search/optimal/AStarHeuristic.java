package grava.search.optimal;

import grava.search.heuristic.Heuristic;

public interface AStarHeuristic<V> extends Heuristic<V> {

	boolean isConsistent();

}
