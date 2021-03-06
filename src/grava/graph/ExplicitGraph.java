package grava.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import grava.edge.Link;
import static grava.util.CollectionUtils.*;

/**
 * This implementation of a graph is explicit in the sense that it explicitly
 * holds a set of vertices and a set of edges. This implementation is preferred
 * if regular access to the set of vertices and the set of edges is required.
 */
public class ExplicitGraph<V, E extends Link<V>> extends AbstractGraph<V, E> {

	private Set<V> vertices;
	private Set<E> edges;

	/**
	 * Constructs an explicit graph consisting of the given set of vertices and
	 * edges.
	 * 
	 * @param vertices
	 *            the initial set of vertices
	 * @param edges
	 *            the initial set of edges
	 */
	public ExplicitGraph(Iterable<V> vertices, Iterable<E> edges) {
		super(vertices, edges);
	}

	/**
	 * Constructs an explicit graph consisting of the given set of edges. The
	 * resulting graph contains all edges of the given set together with all
	 * vertices contained in these edges.
	 * 
	 * @param edges
	 *            the initial set of edges
	 */
	public ExplicitGraph(Iterable<E> edges) {
		this(Collections.emptySet(), edges);
	}

	/**
	 * Creates an empty explicit graph containing no vertices nor edges.
	 */
	public ExplicitGraph() {
		super(Collections.emptySet(), Collections.emptySet());
	}

	/**
	 * Constructs an explicit graph consisting of the set of vertices and edges
	 * contained by the given graph.
	 * 
	 * @param graph
	 *            the graph containing the set of vertices and edges
	 */
	public ExplicitGraph(Graph<V, E> graph) {
		super(graph);
	}

	@Override
	protected void initDataStructures() {
		vertices = new HashSet<>();
		edges = new HashSet<>();
	}

	@Override
	public Set<V> getVertices() {
		return Collections.unmodifiableSet(vertices);
	}

	@Override
	public void addVertex(V v) {
		if (vertices.add(v))
			informGraphListeners(l -> l.vertexAdded(v));
	}

	@Override
	public boolean removeVertex(V v) {
		if (!getVertices().contains(v))
			return false;
		copyOf(getEdges()).stream().filter(e -> e.contains(v))
				.forEach(e -> removeEdge(e));
		vertices.remove(v);
		informGraphListeners(l -> l.vertexRemoved(v));
		return true;
	}

	@Override
	public Set<E> getEdges() {
		return Collections.unmodifiableSet(edges);
	}

	@Override
	public void addEdge(E e) {
		if (edges.add(e))
			informGraphListeners(l -> l.edgeAdded(e));
		e.asSet().forEach(v -> addVertex(v));
	}

	@Override
	public boolean removeEdge(E e) {
		boolean isRemoved = edges.remove(e);
		if (isRemoved)
			informGraphListeners(l -> l.edgeRemoved(e));
		return isRemoved;
	}

	@Override
	public Set<E> edgesOf(V v) {
		return unmodifiableSetOf(edges.stream().filter(
				e -> e.tails().contains(v)));
	}

	@Override
	public Optional<E> edgeBetween(V u, V v) {
		return edges.stream().filter(e -> e.tails().contains(u))
				.filter(e -> e.asSet().equals(setOf(u, v))).findAny();
	}
}