package graph;

import java.util.Set;

import node.Node;

import edge.Edge;

/**
 * Represents a graph, with certain nodes and inter-connecting edges.
 * 
 * @see AdjacencyGraph
 * @see DynamicGraph
 * @see ExplicitGraph
 * @see LinkedGraph
 * @see MappedGraph
 * 
 * @author Stijn
 * 
 * @param <T>
 *            The nodes used in your implementation.
 * @param <S>
 *            The edges used.
 */
public interface Graph<T extends Node, S extends Edge<T>>
		extends
			NeighborProvider<T, S> {

	/**
	 * Returns the neighbors to the given node. Returns null whenever node is
	 * not in the graph in the first place.
	 */
	public Set<T> getNeighborsOf(T node);

	/**
	 * Returns the cost between the given nodes, if the graph is directed, it
	 * returns the cost from node0 to node1. If not reachable, or one of the two
	 * nodes is not even in the graph, returns Double.POSITIVE_INFINITY.
	 * 
	 * @deprecated use getEdgesFrom instead, whenever you need the cost, chances
	 *             are you will also need the corresponding neighbor.
	 */
	@Deprecated
	public double getCostBetween(T node0, T node1);

	/**
	 * Returns a set of one way edges to all the neighbors of the given node, it
	 * encapsulates both the neighbor and the cost, so no need for two separate
	 * methods. Returns null if node is not in the graph.
	 * 
	 * @note Both this method and getNeighborsOf have their uses, the neighbors
	 *       method will be used by a blind search algorithm, where the cost
	 *       computation is unnecessary.
	 */
	public Set<S> getEdgesFrom(T node);
}
