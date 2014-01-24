package graph.neighbor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edge.Edge;
import node.Node;

public class BasicEdgeGraph<T extends Node> extends BasicGraph<T, Edge<T>> {
	
	public BasicEdgeGraph(NeighborProvider<T> provider) {
		super(provider);
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		return Collections.unmodifiableSet(neighborProvider
				.getNeighborsOf(node));
	}

	@Override
	public Set<Edge<T>> getEdgesFrom(T node) {
		Set<Edge<T>> edges = new HashSet<>();
		for (T neighbor : getNeighborsOf(node))
			edges.add(new Edge<T>(neighbor));
		return Collections.unmodifiableSet(edges);
	}

}
