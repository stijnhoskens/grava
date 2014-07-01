package grava.graph.mapped;

import grava.edge.interfaces.Linked;
import grava.graph.Graph;
import grava.util.MultiMap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

abstract class AbstractMapped<V, E extends Linked<V>> implements Graph<V, E> {

	protected MultiMap<V, E> verticesToEdges = new MultiMap<>();

	/**
	 * Constructs a mapped graph consisting of the given set of vertices and
	 * edges.
	 */
	public AbstractMapped(Set<V> vertices, Set<E> edges) {
		edges.forEach(this::addEdge);
		vertices.forEach(this::addVertex);
	}

	/**
	 * Constructs a mapped graph consisting of the given set of vertices. The
	 * resulting graph contains no edges.
	 */
	public AbstractMapped(Set<V> vertices) {
		this(vertices, Collections.emptySet());
	}

	/**
	 * Creates an empty mapped graph containing no vertices nor edges.
	 */
	public AbstractMapped() {

	}

	@Override
	public Set<V> getVertices() {
		return verticesToEdges.keySet();
	}

	@Override
	public void addVertex(V v) {
		if (verticesToEdges.containsKey(v))
			return;
		verticesToEdges.addKey(v);
	}

	@Override
	public boolean removeVertex(V v) {
		return verticesToEdges.remove(v) != null;
	}

	@Override
	public boolean containsVertex(V v) {
		return verticesToEdges.containsKey(v);
	}

	@Override
	public Set<E> getEdges() {
		return verticesToEdges.values().stream()
				.collect(HashSet::new, Set::addAll, Set::addAll);
	}

	@Override
	public Optional<E> edgeBetween(V u, V v) {
		return edgesOf(u).stream().filter(e -> e.contains(v)).findAny();
	}

	@Override
	public Set<E> edgesOf(V v) {
		return verticesToEdges.get(v);
	}

	@Override
	public boolean areNeighbours(V u, V v) {
		return edgeBetween(u, v).isPresent();
	}

	@Override
	public Set<V> neighboursOf(V v) {
		return edgesOf(v).stream().map(Linked::asSet).filter(u -> !u.equals(v))
				.collect(HashSet::new, Set::addAll, Set::addAll);
	}

}
