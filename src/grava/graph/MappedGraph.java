package grava.graph;

import grava.edge.Link;
import grava.util.MultiMap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A concrete implementation of the graph interface having each vertex map to
 * their corresponding edges. This implementation is preferred if frequent
 * access to neighbours or edges of vertices is required. This is the case for
 * search algorithms etc.
 * 
 * @see MappedDiGraph
 */
public class MappedGraph<V, E extends Link<V>> implements Graph<V, E> {

	private MultiMap<V, E> verticesToEdges = new MultiMap<>();

	/**
	 * Constructs a mapped graph consisting of the given set of vertices and
	 * edges.
	 * 
	 * @param vertices
	 *            the initial set of vertices
	 * @param edges
	 *            the initial set of edges
	 */
	public MappedGraph(Set<V> vertices, Set<E> edges) {
		edges.forEach(this::addEdge);
		vertices.forEach(this::addVertex);
	}

	/**
	 * Constructs a mapped graph consisting of the given set of edges. The
	 * resulting graph contains all edges of the given set together with all
	 * vertices contained in these edges.
	 * 
	 * @param edges
	 *            the initial set of edges
	 */
	public MappedGraph(Set<E> edges) {
		this(Collections.emptySet(), edges);
	}

	/**
	 * Creates an empty mapped graph containing no vertices nor edges.
	 */
	public MappedGraph() {

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
		return edgesOf(u)
				.stream()
				.filter(e -> e.asSet().equals(
						Stream.of(u, v).collect(Collectors.toSet()))).findAny();
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
		return edgesOf(v).stream().map(Link::asSet).filter(u -> !u.equals(v))
				.collect(HashSet::new, Set::addAll, Set::addAll);
	}

	@Override
	public void addEdge(E e) {
		e.tails().forEach(v -> verticesToEdges.addValue(v, e));
	}

	@Override
	public boolean removeEdge(E e) {
		return e.tails().stream()
				.allMatch(v -> verticesToEdges.removeValue(v, e));
	}

	@Override
	public boolean removeEdgeBetween(V u, V v) {
		Optional<E> optional = edgeBetween(u, v);
		optional.ifPresent(e -> removeEdge(e));
		return optional.isPresent();
	}

}
