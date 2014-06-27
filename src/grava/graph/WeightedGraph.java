package grava.graph;

import java.util.Optional;
import java.util.Set;

/**
 * Represents a graph having edges which have a weight assigned to it (used in
 * optimal path finding etc.). This has the added functionality of querying the
 * edges themselves (so we can find out the weights).
 *
 * @param <V>
 *            The vertices, this can be any type.
 * @param <E>
 *            The weighted edges, being either directed or non-directed.
 */
public interface WeightedGraph<V, E extends WeightedEdge<V>> extends
		Graph<V, E> {

	/**
	 * Returns all edges containing the given vertex. In other words, for each e
	 * in the result, e.contains(v), and none of the other edges do.
	 */
	Set<E> edgesOf(V v);

	/**
	 * Returns an optional edge between the given vertices.
	 */
	Optional<E> edgeBetween(V u, V v);

}
