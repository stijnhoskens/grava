package grava.edge;

import grava.exceptions.LoopException;

import java.util.Set;

/**
 * Represents an edge or link between two vertices.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public class Edge<V> extends AbstractLink<V> {

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
		super(u, v);
	}

	@Override
	public Set<V> tails() {
		return vertices;
	}

}
