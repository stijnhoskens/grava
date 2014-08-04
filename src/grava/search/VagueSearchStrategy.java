package grava.search;

import grava.edge.Link;
import grava.walk.Walk;

import java.util.Optional;
import java.util.function.Predicate;

public interface VagueSearchStrategy<V, E extends Link<V>> extends
		SearchStrategy<V, E> {

	Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination);

}
