package grava.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import grava.edge.Link;
import static grava.util.SetUtils.*;

/**
 * This implementation of a graph is explicit in the sense that it explicitly
 * holds a set of vertices and a set of edges. This implementation is preferred
 * if regular access to the set of vertices and the set of edges is required.
 */
public class ExplicitGraph<V, E extends Link<V>> implements Graph<V, E> {

	private final Set<V> vertices = new HashSet<>();
	private final Set<E> edges = new HashSet<>();

	/**
	 * Constructs an explicit graph consisting of the given set of vertices and
	 * edges.
	 * 
	 * @param vertices
	 *            the initial set of vertices
	 * @param edges
	 *            the initial set of edges
	 */
	public ExplicitGraph(Set<V> vertices, Set<E> edges) {
		edges.forEach(this::addEdge);
		vertices.forEach(this::addVertex);
	}

	/**
	 * Constructs an explicit graph consisting of the given set of edges. The
	 * resulting graph contains all edges of the given set together with all
	 * vertices contained in these edges.
	 * 
	 * @param edges
	 *            the initial set of edges
	 */
	public ExplicitGraph(Set<E> edges) {
		this(Collections.emptySet(), edges);
	}

	/**
	 * Creates an empty explicit graph containing no vertices nor edges.
	 */
	public ExplicitGraph() {

	}

	@Override
	public Set<V> getVertices() {
		return Collections.unmodifiableSet(vertices);
	}

	@Override
	public void addVertex(V v) {
		vertices.add(v);
	}

	@Override
	public boolean removeVertex(V v) {
		if (!getVertices().contains(v))
			return false;
		new HashSet<>(getEdges()).stream().filter(e -> e.contains(v))
				.forEach(e -> removeEdge(e));
		vertices.remove(v);
		return true;
	}

	@Override
	public Set<E> getEdges() {
		return Collections.unmodifiableSet(edges);
	}

	@Override
	public void addEdge(E e) {
		edges.add(e);
		e.asSet().forEach(v -> vertices.add(v));
	}

	@Override
	public boolean removeEdge(E e) {
		return edges.remove(e);
	}

	@Override
	public boolean removeEdgeBetween(V u, V v) {
		Optional<E> optional = edgeBetween(u, v);
		optional.ifPresent(e -> removeEdge(e));
		return optional.isPresent();
	}

	@Override
	public boolean areNeighbours(V u, V v) {
		return edges.stream().anyMatch(
				e -> e.tails().contains(u) && e.asSet().equals(setOf(u, v)));
	}

	@Override
	public Set<V> neighboursOf(V v) {
		return unmodifiableSetOf(edgesOf(v).stream().map(Link::asSet)
				.flatMap(Set::stream).filter(u -> !u.equals(v)));
	}

	@Override
	public Set<E> edgesOf(V v) {
		return unmodifiableSetOf(edges.stream().filter(
				e -> e.tails().contains(v)));
	}

	@Override
	public Optional<E> edgeBetween(V u, V v) {
		return edgesOf(u).stream().filter(e -> e.asSet().equals(setOf(u, v)))
				.findAny();
	}

}