package grava.edge.interfaces;

/**
 * Provides an interface for weighted and directed edges or weighted arcs. These
 * weighted arcs have an associated weight represented as a double and a head
 * and a tail.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public interface WeightedDirected<V> extends Weighted<V>, Directed<V> {

}
