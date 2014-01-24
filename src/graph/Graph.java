package graph;

import java.util.Set;

import node.Node;
import edge.Edge;
import graph.neighbor.NeighborProvider;

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
			NeighborProvider<T> {

	/**
	 * Returns the neighbors to the given node. Returns null whenever node is
	 * not in the graph in the first place.
	 */
	public Set<T> getNeighborsOf(T node);

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
