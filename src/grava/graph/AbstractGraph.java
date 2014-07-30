package grava.graph;

import grava.edge.Link;
import grava.search.AbstractSearchable;

import java.util.Optional;
import java.util.Set;

public abstract class AbstractGraph<V, E extends Link<V>> extends
		AbstractSearchable<V, E> implements Graph<V, E> {

	/**
	 * Constructs a graph consisting of the set of vertices and edges contained
	 * by the given graph.
	 * 
	 * @param graph
	 *            the graph containing the set of vertices and edges
	 */
	public AbstractGraph(Graph<V, E> graph) {
		this(graph.getVertices(), graph.getEdges());
	}

	/**
	 * Constructs a graph consisting of the given set of vertices and edges.
	 * 
	 * @param vertices
	 *            the initial set of vertices
	 * @param edges
	 *            the initial set of edges
	 */
	public AbstractGraph(Set<V> vertices, Set<E> edges) {
		edges.forEach(this::addEdge);
		vertices.forEach(this::addVertex);
	}

	@Override
	public boolean removeEdgeBetween(V u, V v) {
		Optional<E> optional = edgeBetween(u, v);
		optional.ifPresent(e -> removeEdge(e));
		return optional.isPresent();
	}

	@Override
	public boolean areNeighbours(V u, V v) {
		return edgeBetween(u, v).isPresent();
	}

}
