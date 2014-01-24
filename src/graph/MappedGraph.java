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
public class MappedGraph<T extends Node, S extends Edge<T>>
		implements
			Graph<T, S> {

	public final Map<T, Set<S>> edges;

	public MappedGraph(Map<T, Set<S>> edges) {
		this.edges = edges;
	}

	public MappedGraph() {
		this.edges = new HashMap<>();
	}

	public MappedGraph(Graph<T, S> graph, T seed) {
		GraphExplorer<T, S> explorer = new GraphExplorer<>(graph, seed);
		this.edges = explorer.getNodeMapping();
	}

	public MappedGraph(GraphExplorer<T, S> explorer) {
		this.edges = explorer.getNodeMapping();
	}

	public void addNode(T node) {
		if (edges.containsKey(node))
			return;
		edges.put(node, new HashSet<S>());
	}

	public void putEdge(T node, S edge) {
		Set<S> existingEdges = edges.get(node);
		if (existingEdges == null) {
			Set<S> nodeEdges = new HashSet<>();
			nodeEdges.add(edge);
			edges.put(node, nodeEdges);
		} else
			existingEdges.add(edge);
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		Set<S> nodeEdges = edges.get(node);
		if (nodeEdges == null)
			return null;
		Set<T> neighbors = new HashSet<>();
		for (S edge : nodeEdges)
			neighbors.add(edge.getDestination());
		return neighbors;
	}

	@Override
	public Set<S> getEdgesFrom(T node) {
		Set<S> nodeEdges = edges.get(node);
		if (nodeEdges == null)
			return null;
		return Collections.unmodifiableSet(nodeEdges);
	}

	/**
	 * Use this method in other graph implementations to get all the nodes after
	 * exploring it.
	 * 
	 * @return An unmodifiable view of the node set.
	 */
	Set<T> getNodes() {
		return Collections.unmodifiableSet(edges.keySet());
	}

	/**
	 * Use this method in other graph implementations to get all the edges after
	 * exploring it.
	 * 
	 * @return An unmodifiable view of the edge set.
	 */
	Set<S> getEdges() {
		Set<S> edgeSet = new HashSet<>();
		for (Set<S> mapValue : edges.values())
			edgeSet.addAll(mapValue);
		return Collections.unmodifiableSet(edgeSet);
	}
}
