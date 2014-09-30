package grava.graph;

import grava.edge.Link;
import grava.search.Searchable;

import java.util.Optional;
import java.util.Set;

/**
 * A graph contains a set of vertices and a set of edges. These edges connect
 * two vertices or nodes with each other. Graphs using this representation
 * remain simple, meaning they have no parallel edges or loops. If parallel
 * edges or loops are to be added, they are ignored. Looped edges can't be
 * created anyway, and parallel edges are regarded the same so they are omitted
 * in a set.
 *
 * @param <V>
 *            The type of vertices, this can be any type.
 * @param <E>
 *            The type of edges, being either directed or non-directed, weighted
 *            or non-weighted.
 */
public interface Graph<V, E extends Link<V>> extends Searchable<V, E> {

	/**
	 * Returns the set of vertices. This set is assumed to be unmodifiable, all
	 * adjustments should be done using addVertex and removeVertex.
	 * 
	 * @return the set of vertices.
	 */
	Set<V> getVertices();

	/**
	 * Adds the given vertex to the graph. If the vertex happens to be already
	 * in the graph, nothing happens.
	 * 
	 * @param v
	 *            the vertex to be added
	 */
	void addVertex(V v);

	/**
	 * Removes the given vertex. If the given vertex is somehow connected
	 * through some edges, those edges are also removed.
	 * 
	 * @param v
	 *            the vertex to be removed
	 * @return true iff an actual element was removed.
	 */
	boolean removeVertex(V v);

	/**
	 * Returns the set of edges. This set is assumed to be unmodifiable, all
	 * adjustments should be done using addEdge and removeEdge.
	 * 
	 * @return the set of edges
	 */
	Set<E> getEdges();

	/**
	 * Adds the given edge to the graph. If the edge happens to be already in
	 * the graph, nothing happens. If one of the vertices did not appear in the
	 * vertex set before, it is added. This way, a connected graph can be
	 * constructed by only using this method.
	 * 
	 * @param e
	 *            the edge to be added
	 */
	void addEdge(E e);

	/**
	 * Removes the given edge.
	 * 
	 * @param e
	 *            the edge to be removed
	 * @return true iff an actual element was removed.
	 */
	boolean removeEdge(E e);

	/**
	 * Removes the edge between the given vertices. In case of a directed graph,
	 * the first argument has to be the tail, the second the head.
	 * 
	 * @note This method is also the main reason why the graph represented by
	 *       this class has to be simple. In the presence of parallel edges this
	 *       method has a non-determined behavior.
	 * 
	 * @param u
	 *            the first end-vertex (in case of a digraph, the tail)
	 * @param v
	 *            the second end-vertex (in case of a digraph, the head)
	 * @return true iff an actual element was removed
	 */
	boolean removeEdgeBetween(V u, V v);

	/**
	 * Returns the set of all neighbours of the given vertices. In other words,
	 * for every u in the result, areNeighbours(v,u) == true. And none of the
	 * other vertices not contained in the set have an edge to v. This set is
	 * assumed to be unmodifiable.
	 * 
	 * @param v
	 *            the vertex whose neighbours are to be retrieved
	 * @return the set of all neighbours of the given vertex
	 */
	Set<V> neighboursOf(V v);

	/**
	 * Tests whether there exist an edge between the given vertices. In case of
	 * a directed graph, the first argument has to be the tail, the second the
	 * head. So there has to be a directed edge leading from the first vertex to
	 * the second one.
	 * 
	 * @param u
	 *            the first end-vertex (in case of a digraph, the tail)
	 * @param v
	 *            the second end-vertex (in case of a digraph, the head)
	 * @return true if the two are neighbours
	 */
	boolean areNeighbours(V u, V v);

	/**
	 * Returns an optional edge between the given vertices. This optional
	 * embodies the fact that there might be an edge between two vertices.
	 * 
	 * @param u
	 *            the first end-vertex (in case of a digraph, the tail)
	 * @param v
	 *            the second end-vertex (in case of a digraph, the head)
	 * @return an optional possibly containing the edge between the two given
	 *         vertices
	 */
	Optional<E> edgeBetween(V u, V v);

	/**
	 * Returns the degree of the given vertex, in other words, it returns the
	 * number of neighbours of the given vertex.
	 * 
	 * @param v
	 *            the vertex whose degree is to be returned
	 * @return the degree of v
	 */
	int degreeOf(V v);

}
