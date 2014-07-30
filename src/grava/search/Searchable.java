package grava.search;

import java.util.Set;

import grava.edge.Link;

/**
 * Represents a class of objects which can be searched by a search algorithm
 * (e.g. depth first, A*,...). A graph is one such example, but the concept can
 * be much wider. For instance the 9-puzzle can also be represented by a
 * Searchable.
 * 
 * @see SearchStrategy
 *
 * @param <V>
 *            The type of vertices, this can be any type.
 * @param <E>
 *            The type of edges, being either directed or non-directed, weighted
 *            or non-weighted.
 */
public interface Searchable<V, E extends Link<V>> {

	/**
	 * Returns all edges containing the given vertex. In other words, for each e
	 * in the result, e.contains(v), and none of the other edges do. In case of
	 * a directed graph, the set contains all edges having the given vertex as
	 * its tail. This set is assumed to be unmodifiable.
	 * 
	 * @param v
	 *            the vertex whose edges are to be retrieved
	 * @return the set of edges of the given vertex
	 */
	Set<E> edgesOf(V v);

}
