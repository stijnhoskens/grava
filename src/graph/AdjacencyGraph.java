package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import node.Node;
import util.AdjacencyMatrix;
import edge.Edge;

/**
 * Uses an ordered list for its nodes and a corresponding adjacency graph to
 * represent its edges. An implementation to convert from other graphs is not
 * provided, since all other graphs work with a node set instead of a list.
 * There is no way of knowing which node maps to which row in the matrix.
 * 
 * @See AdjacencyMatrix
 * 
 * @author Stijn
 * 
 * @param <T>
 */
public class AdjacencyGraph<T extends Node> implements Graph<T, Edge<T>> {

	private List<T> nodes;
	private AdjacencyMatrix matrix;

	public AdjacencyGraph(List<T> nodes, AdjacencyMatrix matrix) {
		this.nodes = nodes;
		this.matrix = matrix;
	}

	public AdjacencyGraph(Graph<T, Edge<T>> graph, T seed) {
		// Creates an instance by first creating a mapped graph, which is used
		// to extract a node list, and corresponding entries in the adjacency
		// matrix.
		MappedGraph<T> mapped = new MappedGraph<T>(graph, seed);
		nodes = new ArrayList<>(mapped.getNodes());
		matrix = new AdjacencyMatrix(nodes.size());
		for (T node : nodes)
			for (Edge<T> edge : mapped.getEdgesFrom(node))
				matrix.set(indexOf(node), indexOf(edge.getNode2()),
						edge.getCost());
	}

	protected boolean containsNode(T node) {
		return nodes.contains(node);
	}

	@Override
	public Set<T> getNeighborsOf(T node) {
		if (!containsNode(node))
			return null;
		Set<T> neighbors = new HashSet<>();
		double[] matrixRow = matrix.getRow(indexOf(node));
		for (int i = 0; i < matrixRow.length; i++)
			if (matrixRow[i] != Double.POSITIVE_INFINITY)
				neighbors.add(nodes.get(i));
		return neighbors;
	}

	@Override
	public double getCostBetween(T node0, T node1) {
		if (!(containsNode(node0) && containsNode(node1)))
			return Double.POSITIVE_INFINITY;
		return matrix.get(indexOf(node0), indexOf(node1));
	}

	@Override
	public Set<Edge<T>> getEdgesFrom(T node) {
		if (!containsNode(node))
			return null;
		Set<Edge<T>> edges = new HashSet<>();
		double[] matrixRow = matrix.getRow(indexOf(node));
		for (int i = 0; i < matrixRow.length; i++)
			if (matrixRow[i] != Double.POSITIVE_INFINITY)
				edges.add(new Edge<T>(nodes.get(i), matrixRow[i]));
		return edges;
	}

	private int indexOf(T node) {
		return nodes.indexOf(node);
	}

}
