package graph.neighbor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import node.Node;
import edge.WeightedEdge;

public class BasicWeightedGraph<T extends Node> extends BasicGraph<T, WeightedEdge<T>> {
	
	public BasicWeightedGraph(NeighborProvider<T> provider) {
		super(provider);
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		return Collections.unmodifiableSet(neighborProvider
				.getNeighborsOf(node));
	}

	@Override
	public Set<WeightedEdge<T>> getEdgesFrom(T node) {
		Set<WeightedEdge<T>> edges = new HashSet<>();
		for (T neighbor : getNeighborsOf(node))
			edges.add(new WeightedEdge<T>(neighbor));
		return Collections.unmodifiableSet(edges);
	}

}
