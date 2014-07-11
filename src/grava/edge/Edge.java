package grava.edge;

import grava.exceptions.LoopException;
import grava.util.SetUtils;

import java.util.Set;

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
	 * @throws LoopException
	 *             if the created edge forms a loop
	 */
	public Edge(V u, V v) throws LoopException {
		if (u.equals(v))
			throw new LoopException(u);
		vertices = SetUtils.unmodifiableSetOf(u, v);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((vertices == null) ? 0 : vertices.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<?> other = (Edge<?>) obj;
		if (vertices == null) {
			if (other.vertices != null)
				return false;
		} else if (!vertices.equals(other.vertices))
			return false;
		return true;
	}

}
