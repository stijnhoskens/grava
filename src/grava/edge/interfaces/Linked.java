package grava.edge.interfaces;

import java.util.Set;

/**
 * Provides an interface for linkages between vertices (read: edges). These
 * links contain two vertices.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public interface Linked<V> {

	/**
	 * Returns the linked vertices as a set.
	 * 
	 * @return the linked vertices as a set
	 */
	Set<V> asSet();

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
