package graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import node.Node;
import edge.BiDirectionalEdge;

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
public class ExplicitGraph<T extends Node>
		implements
			Graph<T, BiDirectionalEdge<T>> {

	private final Set<T> nodes = new HashSet<>();
	private final Set<BiDirectionalEdge<T>> edges = new HashSet<>();

	public ExplicitGraph() {
		// basic constructor
	}

	/**
	 * Initializes the graph with all its nodes and edges already provided.
	 * 
	 * @param nodes
	 * @param edges
	 */
	public ExplicitGraph(Set<T> nodes, Set<BiDirectionalEdge<T>> edges) {
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

	public void addEdge(BiDirectionalEdge<T> edge) {
		edges.add(edge);
	}

	public void addEdges(Collection<BiDirectionalEdge<T>> edges) {
		edges.addAll(edges);
	}

	protected BiDirectionalEdge<T> getEdgeBetween(T node0, T node1) {
		for (BiDirectionalEdge<T> edge : getEdgesFrom(node0))
			if (edge.equals(node1))
				return edge;
		return null;
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		Set<T> neighbors = new HashSet<T>();
		for (BiDirectionalEdge<T> edge : getEdgesFrom(node))
			neighbors.add(edge.getNode2());
		return neighbors;
	}

	@Override
	public boolean containsNode(T node) {
		return nodes.contains(node);
	}

	@Override
	public double getCostBetween(T node0, T node1) {
		BiDirectionalEdge<T> edge = getEdgeBetween(node0, node1);
		if (edge == null)
			return Double.POSITIVE_INFINITY;
		else
			return edge.getCost1to2();
	}

	@Override
	public Set<BiDirectionalEdge<T>> getEdgesFrom(T node) {
		Set<BiDirectionalEdge<T>> nodeEdges = new HashSet<BiDirectionalEdge<T>>();
		for (BiDirectionalEdge<T> edge : edges) {
			if (node.equals(edge.getNode1()))
				nodeEdges.add(edge);
			else if (node.equals(edge.getNode2()) && !edge.isDirected())
				nodeEdges.add(edge.switchNodes());
		}
		return nodeEdges;
	}

}
