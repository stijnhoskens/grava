package grava.search.heuristic;

import java.util.Comparator;

import grava.edge.Link;
import grava.search.AbstractSearch;
import grava.walk.Walk;

public abstract class AbstractHeuristic<V, E extends Link<V>> extends
		AbstractSearch<V, E> {

	protected final Heuristic<V> h;

	public AbstractHeuristic(Heuristic<V> h) {
		this.h = h;
	}

	protected Comparator<Walk<V, E>> heuristicComparator() {
		return Comparator.comparing(Walk::endVertex,
				heuristicVertexComparator());
	}

	protected Comparator<V> heuristicVertexComparator() {
		return Comparator.comparingDouble(h);
	}

	protected Comparator<Walk<V, E>> reversedHeuristicComparator() {
		return heuristicComparator().reversed();
	}
}
