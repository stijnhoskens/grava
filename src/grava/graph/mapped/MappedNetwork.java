package grava.graph.mapped;

import grava.graph.WeightedDirectedEdge;
import grava.graph.WeightedGraph;

import java.util.Optional;
import java.util.Set;

public class MappedNetwork<V> extends
		AbstractMapped<V, WeightedDirectedEdge<V>> implements
		WeightedGraph<V, WeightedDirectedEdge<V>> {

	@Override
	public void addEdge(WeightedDirectedEdge<V> e) {
		verticesToEdges.addValue(e.getTail(), e);
	}

	@Override
	public boolean removeEdge(WeightedDirectedEdge<V> e) {
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

	@Override
	public Optional<WeightedDirectedEdge<V>> edgeBetween(V u, V v) {
		return edgesOf(u).stream().filter(e -> e.getHead().equals(v)).findAny();
	}

	@Override
	public Set<WeightedDirectedEdge<V>> edgesOf(V v) {
		return verticesToEdges.get(v);
	}

}
