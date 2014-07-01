package grava.graph.mapped;

import grava.edge.interfaces.Directed;
import grava.graph.Graph;

public class MappedDiGraph<V, E extends Directed<V>> extends
		AbstractMapped<V, E> implements Graph<V, E> {

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
