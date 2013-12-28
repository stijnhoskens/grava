package graph;

import java.util.HashSet;
import java.util.Set;

import node.ValueNode;

import edge.OneWayEdge;

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
public class DynamicGraph<T extends ValueNode> implements Graph<T> {

	private final NeighborProvider<T> neighborProvider;

	/**
	 * Use this constructor if you want nodes be made dynamically.
	 * 
	 * @param provider
	 *            Dynamically provides all neighbors while searching for them.
	 */
	public DynamicGraph(NeighborProvider<T> provider) {
		this.neighborProvider = provider;
	}

	@Override
	public boolean containsNode(T node) {
		return false;
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		Set<T> neighbors = new HashSet<>();
		for (OneWayEdge<T> edge : getEdgesFrom(node))
			neighbors.add(edge.getNode2());
		return neighbors;
	}

	@Override
	public double getCostBetween(T node0, T node1) {
		for (OneWayEdge<T> edge : getEdgesFrom(node0))
			if(edge.getNode2().equals(node1))
				return edge.getCost();
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public Set<OneWayEdge<T>> getEdgesFrom(T node) {
		return neighborProvider.getEdgesFrom(node);
	}

}
