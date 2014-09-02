package grava.walk;

import grava.edge.Link;
import grava.exceptions.IllegalWalkException;
import static grava.util.CollectionUtils.setOf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Walk<V, E extends Link<V>> {

	protected final List<V> vertices;
	protected final List<E> edges;

	public Walk(V start) {
		vertices = Collections.singletonList(start);
		edges = Collections.emptyList();
	}

	public List<V> getVertices() {
		return Collections.unmodifiableList(vertices);
	}

	public int length() {
		return vertices.size();
	}

	public List<E> getEdges() {
		return Collections.unmodifiableList(edges);
	}

	public V beginVertex() {
		return vertices.get(0);
	}

	public V endVertex() {
		return vertices.get(vertices.size() - 1);
	}

	public boolean contains(V v) {
		return vertices.contains(v);
	}

	public void extend(E e, V v) throws IllegalWalkException {
		if (!isProperExtension(e, v))
			throw new IllegalWalkException(this, e, v);
		edges.add(e);
		vertices.add(v);
	}

	public void extend(E e) throws IllegalWalkException {
		if (!isProperExtension(e))
			throw new IllegalWalkException(this, e);
		V v = e.asSet().stream().filter(u -> !u.equals(endVertex())).findAny()
				.get();
		extend(e, v);
	}

	public Walk<V, E> getExtended(E e, V v) throws IllegalWalkException {
		if (!isProperExtension(e, v))
			throw new IllegalWalkException(this, e, v);
		Walk<V, E> walk = new Walk<>(vCopy(), eCopy());
		walk.extend(e, v);
		return walk;
	}

	public Walk<V, E> getExtended(E e) throws IllegalWalkException {
		if (!isProperExtension(e))
			throw new IllegalWalkException(this, e);
		Walk<V, E> walk = new Walk<>(vCopy(), eCopy());
		walk.extend(e);
		return walk;
	}

	public boolean isTrail() {
		return new HashSet<>(edges).size() == edges.size();
	}

	public boolean isPath() {
		return new HashSet<>(vertices).size() == vertices.size();
	}

	public boolean isClosed() {
		return beginVertex().equals(endVertex());
	}

	public boolean isCircuit() {
		return isTrail() && isClosed();
	}

	public boolean isCycle() {
		return isClosed() && subWalk(0, vertices.size() - 2).isPath();
	}

	/**
	 * Returns a subwalk containing the walk starting in the vertex specified by
	 * from, and ending in the vertex specified by to. Both these vertices are
	 * inclusive.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             when the given indices provide an illegal range.
	 */
	public Walk<V, E> subWalk(int from, int to)
			throws IndexOutOfBoundsException {
		List<V> v = vCopy().subList(from, to + 1);
		List<E> e = eCopy().subList(from, to);
		return new Walk<V, E>(v, e);
	}

	private Walk(List<V> vertices, List<E> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	private List<V> vCopy() {
		return new ArrayList<>(vertices);
	}

	private List<E> eCopy() {
		return new ArrayList<>(edges);
	}

	private boolean isProperExtension(E e) {
		return e.tails().contains(endVertex());
	}

	private boolean isProperExtension(E e, V v) {
		return isProperExtension(e) && setOf(v, endVertex()).equals(e.asSet());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edges == null) ? 0 : edges.hashCode());
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
		Walk<?, ?> other = (Walk<?, ?>) obj;
		if (edges == null) {
			if (other.edges != null)
				return false;
		} else if (!edges.equals(other.edges))
			return false;
		if (vertices == null) {
			if (other.vertices != null)
				return false;
		} else if (!vertices.equals(other.vertices))
			return false;
		return true;
	}
}
