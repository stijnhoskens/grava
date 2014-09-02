package grava.graph;

import grava.edge.Link;
import grava.util.MultiMap;
import static grava.util.CollectionUtils.*;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * A concrete implementation of the graph interface having each vertex map to
 * their corresponding edges. This implementation is preferred if frequent
 * access to neighbours or edges of vertices is required. This is the case for
 * search algorithms etc.
 */
public class MappedGraph<V, E extends Link<V>> extends AbstractGraph<V, E> {

	private MultiMap<V, E> verticesToEdges = new MultiMap<>();

	private MultiMap<V, E> verticesToConnectingEdges = new MultiMap<>();

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
		super(vertices, edges);
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
		super(Collections.emptySet(), Collections.emptySet());
	}

	/**
	 * Constructs a mapped graph consisting of the set of vertices and edges
	 * contained by the given graph.
	 * 
	 * @param graph
	 *            the graph containing the set of vertices and edges
	 */
	public MappedGraph(Graph<V, E> graph) {
		super(graph);
	}

	@Override
	public Set<V> getVertices() {
		return verticesToEdges.keySet();
	}

	@Override
	public void addVertex(V v) {
		verticesToEdges.addKey(v);
	}

	@Override
	public boolean removeVertex(V v) {
		if (!getVertices().contains(v))
			return false;
		verticesToConnectingEdges.get(v).forEach(this::removeEdge);
		verticesToEdges.remove(v);
		return true;
	}

	@Override
	public Set<E> getEdges() {
		return flatten(verticesToEdges.values());
	}

	@Override
	public Optional<E> edgeBetween(V u, V v) {
		return edgesOf(u).stream().filter(e -> e.asSet().equals(setOf(u, v)))
				.findAny();
	}

	@Override
	public Set<E> edgesOf(V v) {
		return Collections.unmodifiableSet(verticesToEdges.get(v));
	}

	@Override
	public Set<V> neighboursOf(V v) {
		return unmodifiableSetOf(edgesOf(v).stream().map(Link::asSet)
				.flatMap(Set::stream).filter(u -> !u.equals(v)));
	}

	@Override
	public void addEdge(E e) {
		e.tails().forEach(v -> verticesToEdges.addValue(v, e));
		e.asSet().forEach(v -> verticesToConnectingEdges.addValue(v, e));
	}

	@Override
	public boolean removeEdge(E e) {
		return e.tails().stream()
				.allMatch(v -> verticesToEdges.removeValue(v, e));
	}
}
