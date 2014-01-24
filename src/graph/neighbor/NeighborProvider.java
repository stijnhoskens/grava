package graph.neighbor;

import java.util.Set;

import node.Node;

public interface NeighborProvider<T extends Node> {

	/**
	 * Returns all neighbors of the given node.
	 */
	public Set<T> getNeighborsOf(T node);

}
