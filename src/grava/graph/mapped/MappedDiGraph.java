package grava.graph.mapped;

import grava.graph.DirectedEdge;
import grava.graph.Graph;

import java.util.Optional;
import java.util.Set;

public class MappedDiGraph<V> extends AbstractMapped<V, DirectedEdge<V>>
		implements Graph<V, DirectedEdge<V>> {

	@Override
	public void addEdge(DirectedEdge<V> e) {
		verticesToEdges.addValue(e.getTail(), e);
	}

	@Override
	public boolean removeEdge(DirectedEdge<V> e) {
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

	Optional<DirectedEdge<V>> edgeBetween(V u, V v) {
		return edgesOf(u).stream().filter(e -> e.getHead().equals(v)).findAny();
	}

	Set<DirectedEdge<V>> edgesOf(V v) {
		return verticesToEdges.get(v);
	}

}
