package graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import node.Node;

import edge.OneWayEdge;
import edge.TwoWayEdge;

/**
 * This graph is explicit in the sense that it has an explicit set of both nodes
 * and edges. This is the more intuitive approach, but it will not work very
 * good.
 * 
 * @see LinkedGraph
 * @see DynamicGraph
 * 
 * @author Stijn
 * 
 * @param <T>
 */
public class ExplicitGraph<T extends Node> implements Graph<T>,
		NeighborProvider<T> {

	private final Set<T> nodes = new HashSet<>();
	private final Set<TwoWayEdge<T>> edges = new HashSet<>();

	public ExplicitGraph() {
		// basic constructor
	}

	/**
	 * Initializes the graph with all its nodes and edges already provided.
	 * 
	 * @param nodes
	 * @param edges
	 */
	public ExplicitGraph(Set<T> nodes, Set<TwoWayEdge<T>> edges) {
		this.nodes.addAll(nodes);
		this.edges.addAll(edges);
	}

	public Set<T> getNodes() {
		return Collections.unmodifiableSet(nodes);
	}

	public void addNode(T node) {
		nodes.add(node);
	}

	public void addNodes(Collection<T> nodes) {
		nodes.addAll(nodes);
	}

	public void addEdge(TwoWayEdge<T> edge) {
		edges.add(edge);
	}

	public void addEdge(T node1, T node2, double cost1to2, double cost2to1) {
		if (nodes.contains(node1) && nodes.contains(node2))
			addEdge(new TwoWayEdge<T>(node1, node2, cost1to2, cost2to1));
	}

	public void addEdge(T node1, T node2, double cost) {
		addEdge(node1, node2, cost, cost);
	}

	public void addEdge(T node1, T node2) {
		addEdge(node1, node2, 0d);
	}

	/**
	 * @param node
	 *            The one node which has the other nodes as neighbours
	 * @param neighbours
	 *            Those neighbours
	 * @param costs
	 *            a neighbours.size()x2 matrix, so that costs[0][0] is the cost
	 *            from the node to the first neighbour, and costs[0][1] is the
	 *            cost of the first neighbour to the node.
	 */
	public void addEdges(T node, List<T> neighbours, double[][] costs) {
		for (int i = 0; i < neighbours.size(); i++)
			addEdge(node, neighbours.get(i), costs[i][0], costs[i][1]);
	}

	/**
	 * Returns a set of edges with the given node as first node every time.
	 */
	protected Set<TwoWayEdge<T>> getEdgesOf(T node) {
		Set<TwoWayEdge<T>> nodeEdges = new HashSet<TwoWayEdge<T>>();
		for (TwoWayEdge<T> edge : edges) {
			if (node.equals(edge.getNode1()))
				nodeEdges.add(edge);
			else if (node.equals(edge.getNode2()) && !edge.isDirected())
				nodeEdges.add(edge.switchNodes());
		}
		return nodeEdges;
	}

	protected TwoWayEdge<T> getEdgeBetween(T node0, T node1) {
		for (TwoWayEdge<T> edge : getEdgesOf(node0))
			if (edge.equals(node1))
				return edge;
		return null;
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		Set<T> neighbors = new HashSet<T>();
		for (TwoWayEdge<T> edge : getEdgesOf(node))
			neighbors.add(edge.getNode2());
		return neighbors;
	}

	@Override
	public boolean containsNode(T node) {
		return nodes.contains(node);
	}

	@Override
	public double getCostBetween(T node0, T node1) {
		TwoWayEdge<T> edge = getEdgeBetween(node0, node1);
		if (edge == null)
			return Double.POSITIVE_INFINITY;
		else
			return edge.getCost1to2();
	}

	@Override
	public Set<OneWayEdge<T>> getEdgesFrom(T node) {
		Set<OneWayEdge<T>> edges = new HashSet<>();
		for (TwoWayEdge<T> edge : getEdgesOf(node))
			edges.add(new OneWayEdge<T>(edge.getNode2(), edge.getCost1to2()));
		return edges;
	}

}
