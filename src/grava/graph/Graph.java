package grava.graph;

import grava.edge.interfaces.Linked;

import java.util.Optional;
import java.util.Set;

/**
 * A graph contains a set of vertices and a set of edges. These edges connect
 * two vertices or nodes with each other. Graphs using this representation
 * should remain simple, meaning they have no parallel edges or loops.
 * Otherwise, correctness is not guaranteed.
 *
 * @param <V>
 *            The type of vertices, this can be any type.
 * @param <E>
 *            The type of edges, being either directed or non-directed.
 */
public interface Graph<V, E extends Linked<V>> {

	/**
	 * Returns the set of vertices.
	 */
	Set<V> getVertices();

	/**
	 * Adds the given vertex to the graph. If the vertex happens to be already
	 * in the graph, nothing happens.
	 */
	void addVertex(V v);

	/**
	 * Removes the given vertex. Returns true iff an actual element was removed.
	 */
	boolean removeVertex(V v);

	/**
	 * Returns true iff the vertex is contained within the graph
	 */
	boolean containsVertex(V v);

	/**
	 * Returns the set of edges.
	 */
	Set<E> getEdges();

	/**
	 * Adds the given edge to the graph. If the edge happens to be already in
	 * the graph, nothing happens. If one of the vertices did not appear in the
	 * vertex set before, it is added. This way, a connected graph can be
	 * constructed by only using this method.
	 */
	void addEdge(E e);

	/**
	 * Removes the given edge. Returns true iff an actual element was removed.
	 */
	boolean removeEdge(E e);

	/**
	 * Removes the edge between the given vertices. Returns true iff an actual
	 * element was removed.
	 */
	boolean removeEdgeBetween(V u, V v);

	/**
	 * Returns true iff there exist an edge between the given vertices.
	 */
	boolean areNeighbours(V u, V v);

	/**
	 * Returns the set of all neighbours of the given vertices. In other words,
	 * for every u in the result, areNeighbours(v,u) == true. And none of the
	 * other vertices not contained in the set have an edge to v.
	 */
	Set<V> neighboursOf(V v);

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
