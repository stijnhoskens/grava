package graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import node.Node;
import edge.ExplicitEdge;
import edge.Edge;

/**
 * This graph is explicit in the sense that it has an explicit set of both nodes
 * and edges. This is the more intuitive approach, but it will not work very
 * good.
 * 
 * @author Stijn
 * 
 * @param <T>
 */
public class ExplicitGraph<T extends Node, S extends Edge<T>>
		implements
			Graph<T, S> {

	private final Set<T> nodes = new HashSet<>();
	private final Set<ExplicitEdge<T, S>> edges = new HashSet<>();

	public ExplicitGraph() {
		// basic constructor
	}

	/**
	 * Initializes the graph with all its nodes and edges already provided.
	 * 
	 * @param nodes
	 * @param edges
	 */
	public ExplicitGraph(Set<T> nodes, Set<ExplicitEdge<T, S>> edges) {
		this.nodes.addAll(nodes);
		this.edges.addAll(edges);
	}

	public ExplicitGraph(Graph<T, S> graph, T seed) {
		this(new GraphExplorer<>(graph, seed));
	}

	public ExplicitGraph(GraphExplorer<T, S> explorer) {
		MappedGraph<T, S> mapped = new MappedGraph<>(explorer);
		nodes.addAll(mapped.getNodes());
		edges.addAll(mapped.getEdges());
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

	public void addEdge(ExplicitEdge<T, S> edge) {
		edges.add(edge);
	}

	public void addEdges(Collection<ExplicitEdge<T, S>> edges) {
		edges.addAll(edges);
	}

	protected Edge<T> getEdgeBetween(T node0, T node1) {
		for (Edge<T> edge : getEdgesFrom(node0))
			if (edge.getDestination().equals(node1))
				return edge;
		return null;
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		Set<T> neighbors = new HashSet<T>();
		for (Edge<T> edge : getEdgesFrom(node))
			neighbors.add(edge.getDestination());
		return neighbors;
	}

	@Override
	public Set<S> getEdgesFrom(T node) {
		Set<S> nodeEdges = new HashSet<>();
		for (ExplicitEdge<T, S> edge : edges) {
			if (node.equals(edge.getNode1()))
				nodeEdges.add(edge.getEdge1to2());
			else if (node.equals(edge.getNode2()) && !edge.isDirected())
				nodeEdges.add(edge.getEdge2to1());
		}
		return nodeEdges;
	}

}
