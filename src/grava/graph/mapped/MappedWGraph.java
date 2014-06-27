package grava.graph.mapped;

import java.util.Optional;
import java.util.Set;

import grava.graph.WeightedEdge;
import grava.graph.WeightedGraph;

public class MappedWGraph<V> extends
		AbstractMappedUndirected<V, WeightedEdge<V>> implements
		WeightedGraph<V, WeightedEdge<V>> {

	@Override
	public Optional<WeightedEdge<V>> edgeBetween(V u, V v) {
		return super.edgeBetween(u, v);
	}

	@Override
	public Set<WeightedEdge<V>> edgesOf(V v) {
		return super.edgesOf(v);
	}

}
