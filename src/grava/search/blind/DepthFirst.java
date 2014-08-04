package grava.search.blind;

import grava.edge.Link;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.Optional;
import java.util.function.Predicate;

public class DepthFirst<V, E extends Link<V>> extends AbstractBlind<V, E> {

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		return findPath(graph, start, termination, w -> q.addFirst(w));
	}

}
