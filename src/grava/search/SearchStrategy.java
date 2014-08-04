package grava.search;

import grava.edge.Link;
import grava.walk.Walk;

import java.util.Optional;

public interface SearchStrategy<V, E extends Link<V>> {

	Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start, V end);

}
