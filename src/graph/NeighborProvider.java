package graph;

import java.util.Set;

import node.Node;

import edge.OneWayEdge;

public interface NeighborProvider<T extends Node> {

	/**
	 * Returns all one way edges starting from the given node, this encapsulates
	 * both the cost and the neighbor the node can go to.
	 */
	public Set<OneWayEdge<T>> getEdgesFrom(T node);

}
