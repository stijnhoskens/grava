package grava.edge;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents an edge or link between two vertices.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public class Edge<V> implements Link<V> {

	private Set<V> vertices;

	/**
	 * Constructs an edge between the given vertices.
	 * 
	 * @param u
	 *            the first vertex
	 * @param v
	 *            the second vertex
	 */
	public Edge(V u, V v) {
		vertices = Collections.unmodifiableSet(Stream.of(u, v).collect(
				Collectors.toSet()));
	}

	@Override
	public Set<V> asSet() {
		return vertices;
	}

	@Override
	public boolean contains(V v) {
		return vertices.contains(v);
	}

	@Override
	public Set<V> tails() {
		return vertices;
	}

}
