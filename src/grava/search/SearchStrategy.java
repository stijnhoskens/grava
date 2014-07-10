package grava.search;

import grava.edge.Link;
import grava.graph.Graph;
import grava.walk.Walk;

public interface SearchStrategy<V, E extends Link<V>> {

	Walk<V, E> findPath(Graph<V, E> graph, V start, V end);

}
