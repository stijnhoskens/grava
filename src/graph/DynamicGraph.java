package graph;

import java.util.HashSet;
import java.util.Set;

import node.ValueNode;

import edge.Edge;

/**
 * This graph is dynamic in the sense that it builds up its node set in runtime,
 * using a given neighborProvider. It does not actually keep a set of nodes
 * already searched, as this could create a big memory waste.
 * 
 * Use this implementation if there are way too many nodes (for instance all
 * permutations in a certain game). Since these nodes are not kept, but instead
 * are created every time a node is needed, these nodes should implement equals
 * and hashcode. This is enforced by implementing the ValueNode interface.
 * 
 * @see NeighborProvider
 * @see ValueNode
 * 
 * @author Stijn
 * 
 * @param <T>
 */
public class DynamicGraph<T extends ValueNode, S extends Edge<T>>
		implements
			Graph<T, S> {

	private final NeighborProvider<T, S> neighborProvider;

	/**
	 * Use this constructor if you want nodes be made dynamically.
	 * 
	 * @param provider
	 *            Dynamically provides all neighbors while searching for them.
	 *            Supply a graph as an argument and this graph will act as a
	 *            shallow facade for the underlying graph. This approach is not
	 *            recommended as it creates unnecessary overhead.
	 */
	public DynamicGraph(NeighborProvider<T, S> provider) {
		this.neighborProvider = provider;
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		Set<S> edges = getEdgesFrom(node);
		if (edges == null)
			return null;
		Set<T> neighbors = new HashSet<>();
		for (Edge<T> edge : edges)
			neighbors.add(edge.getNode2());
		return neighbors;
	}

	@Override
	public double getCostBetween(T node0, T node1) {
		for (Edge<T> edge : getEdgesFrom(node0))
			if (edge.getNode2().equals(node1))
				return edge.getCost();
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public Set<S> getEdgesFrom(T node) {
		return neighborProvider.getEdgesFrom(node);
	}

}
