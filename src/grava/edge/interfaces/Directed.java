package grava.edge.interfaces;

/**
 * Provides an interface for directed edges. These directed edges have a
 * distinct tail and head.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public interface Directed<V> extends Link<V> {

	/**
	 * Returns the tail.
	 * 
	 * @return the tail
	 */
	V getTail();

	/**
	 * Returns the head.
	 * 
	 * @return the head
	 */
	V getHead();

}
