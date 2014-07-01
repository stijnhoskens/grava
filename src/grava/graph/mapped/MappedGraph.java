package grava.graph.mapped;

import java.util.Set;

import grava.edge.interfaces.Link;

/**
 * A concrete implementation of the graph interface having each vertex map to
 * their corresponding edges. This implementation is preferred if frequent
 * access to neighbours or edges of vertices is required. This is the case for
 * search algorithms etc. Use with caution, as this implementation can't be used
 * for digraphs, use the MappedDiGraph instead.
 * 
 * @see MappedDiGraph
 */
public class MappedGraph<V, E extends Link<V>> extends AbstractMapped<V, E> {

	/**
	 * Constructs a mapped graph consisting of the given set of vertices and
	 * edges.
	 * 
	 * @param vertices
	 *            the initial set of vertices
	 * @param edges
	 *            the initial set of edges
	 */
	public MappedGraph(Set<V> vertices, Set<E> edges) {
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
	public MappedGraph(Set<E> edges) {
		super(edges);
	}

	/**
	 * Creates an empty mapped graph containing no vertices nor edges.
	 */
	public MappedGraph() {

	}

	@Override
	public void addEdge(E e) {
		e.asSet().forEach(v -> verticesToEdges.addValue(v, e));
	}

	@Override
	public boolean removeEdge(E e) {
		return e.asSet().stream()
				.allMatch(v -> verticesToEdges.removeValue(v, e));
	}

	@Override
	public boolean removeEdgeBetween(V u, V v) {
		if (areNeighbours(u, v)) {
			verticesToEdges.get(v).removeIf(e -> e.contains(u));
			verticesToEdges.get(u).removeIf(e -> e.contains(v));
			return true;
		}
		return false;
	}

}
