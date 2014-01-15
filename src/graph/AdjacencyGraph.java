package graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import node.Node;
import util.AdjacencyMatrix;
import edge.Edge;

/**
 * Uses an ordered list for its nodes and a corresponding adjacency graph to represent its edges.
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
