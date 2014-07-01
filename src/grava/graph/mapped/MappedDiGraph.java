package grava.graph.mapped;

import java.util.Set;

import grava.edge.interfaces.Directed;
import grava.graph.Graph;

/**
 * Similar to MappedGraph, but suited for directed edges.
 * 
 * @see MappedGraph
 */

public class MappedDiGraph<V, E extends Directed<V>> extends
		AbstractMapped<V, E> implements Graph<V, E> {

	/**
	 * Constructs a mapped graph consisting of the given set of vertices and
	 * edges.
	 * 
	 * @param vertices
	 *            the initial set of vertices
	 * @param edges
	 *            the initial set of edges
	 */
	public MappedDiGraph(Set<V> vertices, Set<E> edges) {
		super(vertices, edges);
	}

	/**
	 * Constructs a mapped graph consisting of the given set of edges. The
	 * resulting graph contains all edges of the given set together with all
	 * vertices contained in these edges.
	 * 
	 * @param edges
	 *            the initial set of edges
	 */
	public MappedDiGraph(Set<E> edges) {
		super(edges);
	}

	/**
	 * Creates an empty mapped graph containing no vertices nor edges.
	 */
	public MappedDiGraph() {

	}

	@Override
	public void addEdge(E e) {
		verticesToEdges.addValue(e.getTail(), e);
	}

	@Override
	public boolean removeEdge(E e) {
		return verticesToEdges.removeValue(e.getTail(), e);
	}

	@Override
	public boolean removeEdgeBetween(V u, V v) {
		if (areNeighbours(u, v)) {
			verticesToEdges.get(u).removeIf(e -> e.getHead().equals(v));
			return true;
		}
		return false;
	}
}
