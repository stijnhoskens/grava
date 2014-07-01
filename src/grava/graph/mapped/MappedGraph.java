package grava.graph.mapped;

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
