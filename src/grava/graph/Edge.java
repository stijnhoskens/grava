package grava.graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an edge or 'connection' between two vertices.
 *
 * @param <V>
 *            The vertices which it connects.
 */
public class Edge<V> {

	protected V tail;
	protected V head;
	private Set<V> vertices;

	private Edge(Set<V> vertices) {
		this.vertices = Collections.unmodifiableSet(vertices);
	}

	/**
	 * Creates an edge between the given vertices.
	 */
	public Edge(V u, V v) {
		this(new HashSet<>(Arrays.asList(u, v)));
		tail = u;
		head = v;
	}

	/**
	 * Returns the edge as a set, containing both vertices.
	 */
	public Set<V> asSet() {
		return vertices;
	}

	/**
	 * Returns whether it is adjacent to the given edge. Two edges are adjacent
	 * if they contain a common vertex. In other words, this method returns true
	 * iff the size of the intersection of both edges as a set is larger than 0.
	 */
	public boolean isAdjacentTo(Edge<V> other) {
		return vertices.stream().anyMatch(u -> other.asSet().contains(u));
	}

	/**
	 * Returns true if the edge has the given vertex as an end-vertex.
	 */
	public boolean contains(V vertex) {
		return vertices.contains(vertex);
	}
}
