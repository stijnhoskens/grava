package grava.graph;

import static grava.util.CollectionUtils.flattenedStream;
import static grava.util.CollectionUtils.setOf;
import grava.edge.Link;
import grava.exceptions.NoSuchInducedSubgraphException;

import java.util.Set;

public class Graphs {

	/**
	 * Returns true iff the given graph is the null graph. That is, the given
	 * graph has no edges.
	 * 
	 * @param graph
	 *            The graph to be tested whether it is a null graph
	 * @return true iff graph is the null graph
	 */
	public static boolean isNullGraph(Graph<?, ?> graph) {
		return graph.getEdges().isEmpty();
	}

	/**
	 * Returns true iff the given graph is a complete graph. That is, every pair
	 * of distinct vertices is connected by an edge in the given graph.
	 * 
	 * @param graph
	 *            The graph to be tested of its completeness
	 * @return true iff graph is complete
	 */
	public static <V> boolean isComplete(Graph<V, ?> graph) {
		int nbOfVertices = graph.getVertices().size();
		return graph
				.getVertices()
				.stream()
				.allMatch(v -> graph.neighboursOf(v).size() == nbOfVertices - 1);
	}

	/**
	 * Returns true iff the first argument is a subgraph of the second. That is,
	 * a graph is a subgraph of another, if the vertices and edges of the first
	 * are subsets of the vertices and edges of the second.
	 * 
	 * @param subgraph
	 *            The graph which is supposed to be the subgraph
	 * @param graph
	 *            The graph of which the first argument should be a subgraph
	 * @return true iff the first is a subgraph of the second
	 */
	public static <V, E extends Link<V>> boolean isSubgraphOf(
			Graph<V, E> subgraph, Graph<V, E> graph) {
		return graph.getVertices().containsAll(subgraph.getVertices())
				&& graph.getEdges().containsAll(subgraph.getEdges());
	}

	/**
	 * 
	 * @param vertices
	 * @param graph
	 * @return
	 * @throws NoSuchInducedSubgraphException
	 */
	public static <V, E extends Link<V>> Graph<V, E> subgraphInducedByVertices(
			Set<V> vertices, Graph<V, E> graph)
			throws NoSuchInducedSubgraphException {
		if (!graph.getVertices().containsAll(vertices))
			throw new NoSuchInducedSubgraphException();
		return new MappedGraph<V, E>(setOf(flattenedStream(
				vertices.stream().map(v -> graph.edgesOf(v))).filter(
				e -> vertices.containsAll(e.asSet()))));
	}

	public static <V, E extends Link<V>> Graph<V, E> subgraphInducedByEdges(
			Set<E> edges, Graph<V, E> graph)
			throws NoSuchInducedSubgraphException {
		if (!graph.getEdges().containsAll(edges))
			throw new NoSuchInducedSubgraphException();
		return new MappedGraph<V, E>(edges);
	}

	public static <V> boolean isClique(Set<V> vertices, Graph<V, ?> graph) {
		return isComplete(subgraphInducedByVertices(vertices, graph));
	}

	public static <V, E extends Link<V>> boolean equals(Graph<V, E> g1,
			Graph<V, E> g2) {
		return g1.getVertices().equals(g2.getVertices())
				&& g1.getEdges().equals(g2.getEdges());
	}
}
