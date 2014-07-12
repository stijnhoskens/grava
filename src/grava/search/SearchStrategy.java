package grava.search;

import java.util.Optional;

import grava.edge.Link;
import grava.graph.Graph;
import grava.walk.Walk;

public interface SearchStrategy<V, E extends Link<V>> {

	Optional<Walk<V, E>> findPath(Graph<V, E> graph, V start, V end);

}
