package grava.graph.mapped;

import grava.graph.Edge;

import java.util.Optional;
import java.util.Set;

abstract class AbstractMappedUndirected<V, E extends Edge<V>> extends
		AbstractMapped<V, E> {

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

	Optional<E> edgeBetween(V u, V v) {
		return edgesOf(u).stream().filter(e -> e.contains(v)).findAny();
	}

	Set<E> edgesOf(V v) {
		return verticesToEdges.get(v);
	}

}
