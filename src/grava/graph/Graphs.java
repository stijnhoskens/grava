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
	 *            the graph to be tested whether it is a null graph
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
	 *            the graph to be tested of its completeness
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
	 *            the graph which is supposed to be the subgraph
	 * @param graph
	 *            the graph of which the first argument should be a subgraph
	 * @return true iff the first is a subgraph of the second
	 */
	public static <V, E extends Link<V>> boolean isSubgraphOf(
			Graph<V, E> subgraph, Graph<V, E> graph) {
		return graph.getVertices().containsAll(subgraph.getVertices())
				&& graph.getEdges().containsAll(subgraph.getEdges());
	}

	/**
	 * Returns the subgraph of the given graph induced by the given set of
	 * vertices. This means the resulting graph has the given vertex set as well
	 * as the edge set which consists of all edges having their endvertices in
	 * the vertex set.
	 * 
	 * @param vertices
	 *            the vertices that form the basis of the induced graph
	 * @param graph
	 *            the graph from which the subgraph should be induced
	 * @return the subgraph of graph induced by vertices
	 * @throws NoSuchInducedSubgraphException
	 *             when the set of vertices is no subset of the set of vertices
	 *             as specified by the graph
	 */
	public static <V, E extends Link<V>> Graph<V, E> subgraphInducedByVertices(
			Set<V> vertices, Graph<V, E> graph)
			throws NoSuchInducedSubgraphException {
		if (!graph.getVertices().containsAll(vertices))
			throw new NoSuchInducedSubgraphException();
		return new ExplicitGraph<V, E>(setOf(flattenedStream(
				vertices.stream().map(v -> graph.edgesOf(v))).filter(
				e -> vertices.containsAll(e.asSet()))));
	}

	/**
	 * Returns the subgraph of the given graph induced by the given set of
	 * edges. This means the resulting graph has the given edge set as well as
	 * the vertex set which consists of endvertices of the edge set.
	 * 
	 * @param edges
	 *            the edges that form the basis of the induced graph
	 * @param graph
	 *            the graph from which the subgraph should be induced
	 * @return the subgraph of graph induced by edges
	 * @throws NoSuchInducedSubgraphException
	 *             when the set of edges is no subset of the set of edges as
	 *             specified by the graph
	 */
	public static <V, E extends Link<V>> Graph<V, E> subgraphInducedByEdges(
			Set<E> edges, Graph<V, E> graph)
			throws NoSuchInducedSubgraphException {
		if (!graph.getEdges().containsAll(edges))
			throw new NoSuchInducedSubgraphException();
		return new ExplicitGraph<V, E>(edges);
	}

	/**
	 * Returns true iff the given set of vertices forms a clique within the
	 * given graph. In other words, the subgraph induced by those vertices is
	 * complete.
	 * 
	 * @param vertices
	 *            the set of vertices for which we have to test if it's a clique
	 * @param graph
	 *            the graph in which the vertices appear
	 * @return true iff vertices forms a clique in graph
	 */
	public static <V> boolean isClique(Set<V> vertices, Graph<V, ?> graph) {
		try {
			return isComplete(subgraphInducedByVertices(vertices, graph));
		} catch (NoSuchInducedSubgraphException e) {
			return false;
		}
	}

	/**
	 * Returns true iff the first graph equals the second with respect to their
	 * sets of vertices and edges.
	 * 
	 * @param g1
	 *            the first graph
	 * @param g2
	 *            the second
	 * @return true iff the vertices and edges are the same for both graphs
	 */
	public static <V, E extends Link<V>> boolean equals(Graph<V, E> g1,
			Graph<V, E> g2) {
		return g1.getVertices().equals(g2.getVertices())
				&& g1.getEdges().equals(g2.getEdges());
	}
}
