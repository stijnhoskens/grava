package graph;

import java.util.List;

public interface NodeFactory<T extends Node> {

	/**
	 * Returns all neighbours to the given node.
	 * 
	 * @param node
	 *            the node to which the result should be neighbours to.
	 * @return those neighbours
	 * @note make sure to implement hashCode & equals on those nodes.
	 */
	public List<T> createNeighbourNodes(T node);

	public double getCostBetween(T node1, T node2);

}
