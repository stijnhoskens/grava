package grava.search.blind;

import grava.edge.Link;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.Optional;

public class DepthFirst<V, E extends Link<V>> extends AbstractBlind<V, E> {

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start, V end) {
		return findPath(graph, start, end, w -> q.addFirst(w));
	}

}
