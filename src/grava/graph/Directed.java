package grava.graph;

/**
 * Provides an interface for directed edges (either weighted or non-weighted).
 * These directed edges have a distinct tail and head.
 *
 * @param <V>
 *            The vertices which it connects.
 */
public interface Directed<V> {

	/**
	 * Returns the tail.
	 */
	V getTail();

	/**
	 * Returns the head.
	 */
	V getHead();

}
