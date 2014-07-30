package grava.walk;

import grava.edge.Link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Walk<V, E extends Link<V>> {

	private final List<V> vertices;
	private final List<E> edges;

	public Walk(V start) {
		vertices = Collections.singletonList(start);
		edges = Collections.emptyList();
	}

	public List<V> getVertices() {
		return Collections.unmodifiableList(vertices);
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

	public void extend(E e, V v) {
		edges.add(e);
		vertices.add(v);
	}

	public Walk<V, E> getExtended(E e, V v) {
		Walk<V, E> walk = new Walk<>(v(), e());
		walk.extend(e, v);
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
	 */
	public Walk<V, E> subWalk(int from, int to) {
		List<V> v = v().subList(from, to + 1);
		List<E> e = e().subList(from, to);
		return new Walk<V, E>(v, e);
	}

	private Walk(List<V> vertices, List<E> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	private List<V> v() {
		return new ArrayList<>(vertices);
	}

	private List<E> e() {
		return new ArrayList<>(edges);
	}
}
