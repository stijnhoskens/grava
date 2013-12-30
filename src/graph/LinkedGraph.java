package graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import node.LinkedNode;
import edge.Edge;

public class LinkedGraph<T extends LinkedNode<T, S>, S extends Edge<T>>
		implements
			Graph<T, S> {

	private final Set<T> nodes = new HashSet<>();

	public LinkedGraph(Set<T> nodes) {
		this.nodes.addAll(nodes);
	}

	public LinkedGraph() {

	}

	public Set<T> getNodes() {
		return Collections.unmodifiableSet(nodes);
	}

	public void addNode(T node) {
		nodes.add(node);
	}

	public void addNodes(Collection<T> nodes) {
		nodes.addAll(nodes);
	}

	@Override
	public boolean containsNode(T node) {
		return nodes.contains(node);
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		return Collections.unmodifiableSet(node.getNeighbors());
	}

	@Override
	public double getCostBetween(T node0, T node1) {
		for (Edge<T> edge : node0.getEdges())
			if (edge.getNode2().equals(node1))
				return edge.getCost();
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public Set<S> getEdgesFrom(T node) {
		return Collections.unmodifiableSet(node.getEdges());
	}

}
