package grava.graph.mapped;

import grava.edge.interfaces.Linked;

public class MappedGraph<V, E extends Linked<V>> extends AbstractMapped<V, E> {

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
