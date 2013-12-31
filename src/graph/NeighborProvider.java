package graph;

import java.util.Set;

import node.Node;

import edge.Edge;

public interface NeighborProvider<T extends Node, S extends Edge<T>> {

	/**
	 * Returns all one way edges starting from the given node, this encapsulates
	 * both the cost and the neighbor the node can go to, returns null if there
	 * is no such node.
	 */
	public Set<S> getEdgesFrom(T node);

}
