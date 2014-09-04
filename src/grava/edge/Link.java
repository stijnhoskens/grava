package grava.edge;

import grava.util.Pair;

import java.util.Set;

/**
 * Provides an interface for linkages between vertices (read: edges). These
 * links contain two vertices. A requirement of these links is that they
 * shouldn't allow loops.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public interface Link<V> {

	/**
	 * Returns the linked vertices as a set. This set is unmodifiable.
	 * 
	 * @return the linked vertices as a set
	 */
	Set<V> asSet();

	/**
	 * Returns the linked vertices as a pair.
	 * 
	 * @return the linked vertices as a pair
	 */
	Pair<V, V> asPair();

	/**
	 * Returns the set of tails of the link. These are the vertices through
	 * which the link can be entered. In reality this will be a set containing
	 * the tail vertex for arcs, and a set containing both vertices for edges.
	 * 
	 * @return the tails as a set
	 */
	Set<V> tails();

	/**
	 * Returns true iff the given vertex is contained in the link, or in other
	 * words, if the given vertex is an end-vertex of the edge.
	 * 
	 * @param v
	 *            the vertex which presence is to be tested
	 * @return true if the edge contains the given vertex
	 */
	boolean contains(V v);
}
