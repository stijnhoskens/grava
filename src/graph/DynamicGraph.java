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
 * @see NeighborProvider
 * 
 * @see LinkedGraph
 * @see ExplicitGraph
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
	 */
	public DynamicGraph(NeighborProvider<T, S> provider) {
		this.neighborProvider = provider;
	}

	/**
	 * Returns false by default, since no data is held, except for the
	 * neighborProvider.
	 */
	@Override
	public boolean containsNode(T node) {
		return false;
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
