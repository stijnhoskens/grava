package graph;

import java.util.Set;

import node.Node;

import edge.OneWayEdge;

public interface Graph<T extends Node> {

	/**
	 * Returns true if the graph contains given node.
	 */
	public boolean containsNode(T node);

	/**
	 * Returns the neighbors to the given node.
	 */
	public Set<T> getNeighborsOf(T node);

	/**
	 * Returns the cost between the given nodes, if the graph is directed, it
	 * returns the cost from node0 to node1. If not reachable, returns
	 * Double.POSITIVE_INFINITY;
	 * 
	 * @deprecated use getEdgesFrom instead, whenever you need the cost, chances
	 *             are you will also need the corresponding neighbor.
	 */
	@Deprecated
	public double getCostBetween(T node0, T node1);

	/**
	 * Returns a set of one way edges to all the neighbors of the given node, it
	 * encapsulates both the neighbor and the cost, so no need for two separate
	 * methods.
	 */
	public Set<? extends OneWayEdge<T>> getEdgesFrom(T node);
}
