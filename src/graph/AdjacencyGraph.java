package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import node.Node;
import util.AdjacencyMatrix;
import edge.WeightedEdge;

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
public class AdjacencyGraph<T extends Node>
		implements
			Graph<T, WeightedEdge<T>> {

	private List<T> nodes;
	private AdjacencyMatrix matrix;

	public AdjacencyGraph(List<T> nodes, AdjacencyMatrix matrix) {
		this.nodes = nodes;
		this.matrix = matrix;
	}

	public AdjacencyGraph(Graph<T, WeightedEdge<T>> graph, T seed) {
		this(new GraphExplorer<T, WeightedEdge<T>>(graph, seed));
	}

	public AdjacencyGraph(GraphExplorer<T, WeightedEdge<T>> explorer) {
		MappedGraph<T, WeightedEdge<T>> mapped = new MappedGraph<>(explorer);
		nodes = new ArrayList<>(mapped.getNodes());
		matrix = new AdjacencyMatrix(nodes.size());
		for (T node : nodes)
			for (WeightedEdge<T> edge : mapped.getEdgesFrom(node))
				matrix.set(indexOf(node), indexOf(edge.getDestination()),
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
	public Set<WeightedEdge<T>> getEdgesFrom(T node) {
		if (!containsNode(node))
			return null;
		Set<WeightedEdge<T>> edges = new HashSet<>();
		double[] matrixRow = matrix.getRow(indexOf(node));
		for (int i = 0; i < matrixRow.length; i++)
			if (matrixRow[i] != Double.POSITIVE_INFINITY)
				edges.add(new WeightedEdge<T>(nodes.get(i), matrixRow[i]));
		return edges;
	}

	private int indexOf(T node) {
		return nodes.indexOf(node);
	}

}
