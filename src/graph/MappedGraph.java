package graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import node.Node;
import util.MultiMap;
import edge.Edge;
import edge.ExplicitEdge;

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

	public final MultiMap<T, S> edges;

	public MappedGraph(MultiMap<T, S> edges) {
		this.edges = edges;
	}

	public MappedGraph(GraphExplorer<T, S> explorer) {
		this.edges = explorer.getNodeMapping();
	}

	public MappedGraph(Map<T, Set<S>> edges) {
		this(new MultiMap<>(edges));
	}

	public MappedGraph() {
		this(new MultiMap<T, S>());
	}

	public MappedGraph(Graph<T, S> graph, T seed) {
		this(new GraphExplorer<>(graph, seed));
	}

	public void addNode(T node) {
		if (edges.containsKey(node))
			return;
		edges.addKey(node);
	}

	public void putEdge(T node, S edge) {
		edges.addValue(node, edge);
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
	public Set<T> getNodes() {
		return Collections.unmodifiableSet(edges.keySet());
	}

	/**
	 * Use this method in other graph implementations to get all the edges after
	 * exploring it.
	 * 
	 * @return An unmodifiable view of the edge set.
	 */
	public Set<ExplicitEdge<T, S>> getEdges() {
		Set<ExplicitEdge<T, S>> explicit = new HashSet<>();
		for (Entry<T, Set<S>> entry : edges.entrySet()) {
			T node1 = entry.getKey();
			for (S edge : entry.getValue()) {
				T node2 = edge.getDestination();
				S otherEdge = edgeBetween(node2, node1);
				if (otherEdge == null)
					explicit.add(new ExplicitEdge<T, S>(node1, edge));
				else {
					ExplicitEdge<T, S> explEdge = new ExplicitEdge<T, S>(edge,
							otherEdge);
					if (!explicit.contains(explEdge.switchNodes()))
						// Optimization, the set will only contain one explicit
						// edge between two nodes.
						explicit.add(explEdge);
				}
			}
		}
		return explicit;
	}
	/**
	 * Returns null if there is no such edge.
	 */
	private S edgeBetween(T node1, T node2) {
		for (S edge : edges.get(node1))
			if (edge.getDestination().equals(node2))
				return edge;
		return null;
	}
}
