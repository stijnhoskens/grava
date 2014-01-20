package graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import node.Node;
import edge.Edge;

/**
 * Uses a map to map nodes to their corresponding edges, which in turn have a
 * destination node. This implementation is comparable to an adjacency list.
 * 
 * @author Stijn
 * 
 * @param <T>
 */
public class MappedGraph<T extends Node> implements Graph<T, Edge<T>> {

	public final Map<T, Set<Edge<T>>> edges;

	public MappedGraph(Map<T, Set<Edge<T>>> edges) {
		this.edges = edges;
	}

	public MappedGraph() {
		this.edges = new HashMap<>();
	}

	public MappedGraph(Graph<T, Edge<T>> graph, T seed) {
		GraphExplorer<T, Edge<T>> explorer = new GraphExplorer<>(graph, seed);
		this.edges = explorer.getNodeMapping();
	}

	public void addNode(T node) {
		edges.put(node, new HashSet<Edge<T>>());
	}

	public void putEdge(T node, Edge<T> edge) {
		Set<Edge<T>> existingEdges = edges.get(node);
		if (existingEdges == null) {
			Set<Edge<T>> nodeEdges = new HashSet<>();
			nodeEdges.add(edge);
			edges.put(node, nodeEdges);
		} else
			existingEdges.add(edge);
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		Set<Edge<T>> nodeEdges = edges.get(node);
		if (nodeEdges == null)
			return null;
		Set<T> neighbors = new HashSet<>();
		for (Edge<T> edge : nodeEdges)
			neighbors.add(edge.getNode2());
		return neighbors;
	}

	@Override
	public double getCostBetween(T node0, T node1) {
		for (Edge<T> edge : edges.get(node0))
			if (edge.getNode2().equals(node1))
				return edge.getCost();
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public Set<Edge<T>> getEdgesFrom(T node) {
		Set<Edge<T>> nodeEdges = edges.get(node);
		if (nodeEdges == null)
			return null;
		return Collections.unmodifiableSet(nodeEdges);
	}

	Set<T> getNodes() {
		return Collections.unmodifiableSet(edges.keySet());
	}

}
