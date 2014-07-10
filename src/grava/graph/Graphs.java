package grava.graph;

import java.util.HashSet;
import java.util.Set;

import grava.edge.Link;

public class Graphs {

	public static boolean isNullGraph(Graph<?, ?> graph) {
		return graph.getEdges().isEmpty();
	}

	public static <V> boolean isComplete(Graph<V, ?> graph) {
		int nbOfVertices = graph.getVertices().size();
		return graph
				.getVertices()
				.stream()
				.allMatch(v -> graph.neighboursOf(v).size() == nbOfVertices - 1);
	}

	public static <V, E extends Link<V>> boolean isSubgraphOf(
			Graph<V, E> subgraph, Graph<V, E> graph) {
		return graph.getVertices().containsAll(subgraph.getVertices())
				&& graph.getEdges().containsAll(subgraph.getEdges());
	}

	public static <V, E extends Link<V>> Graph<V, E> subgraphInducedByVertices(
			Set<V> vertices, Graph<V, E> graph) {
		Set<E> edgesOfVertices = vertices.stream().map(v -> graph.edgesOf(v))
				.collect(HashSet::new, Set::addAll, Set::addAll);
		return new MappedGraph<V, E>(edgesOfVertices);
	}

	public static <V, E extends Link<V>> Graph<V, E> subgraphInducedByEdges(
			Set<E> edges, Graph<V, E> graph) {
		return new MappedGraph<V, E>(edges);
	}

	public static <V> boolean isClique(Set<V> vertices, Graph<V, ?> graph) {
		return isComplete(subgraphInducedByVertices(vertices, graph));
	}
}
