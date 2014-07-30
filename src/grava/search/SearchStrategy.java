package grava.search;

import java.util.Optional;

import grava.edge.Link;
import grava.walk.Walk;

public interface SearchStrategy<V, E extends Link<V>> {

	Optional<Walk<V, E>> findPath(Searchable<V, E> searchable, V start, V end);

}
