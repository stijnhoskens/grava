package grava.graph;

import static grava.util.CollectionUtils.unmodifiableSetOf;
import grava.edge.Link;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

abstract class AbstractGraph<V, E extends Link<V>> implements Graph<V, E> {

	/**
	 * Constructs a graph consisting of the set of vertices and edges contained
	 * by the given graph.
	 * 
	 * @param graph
	 *            the graph containing the set of vertices and edges
	 */
	public AbstractGraph(Graph<V, E> graph) {
		this(graph.getVertices(), graph.getEdges());
	}

	/**
	 * Constructs a graph consisting of the given set of vertices and edges.
	 * 
	 * @param vertices
	 *            the initial set of vertices
	 * @param edges
	 *            the initial set of edges
	 */
	public AbstractGraph(Iterable<V> vertices, Iterable<E> edges) {
		initDataStructures();
		edges.forEach(this::addEdge);
		vertices.forEach(this::addVertex);
	}

	public AbstractGraph() {

	}

	@Override
	public boolean removeEdgeBetween(V u, V v) {
		Optional<E> optional = edgeBetween(u, v);
		optional.ifPresent(e -> removeEdge(e));
		return optional.isPresent();
	}

	@Override
	public boolean areNeighbours(V u, V v) {
		return edgeBetween(u, v).isPresent();
	}

	@Override
	public Set<V> neighboursOf(V v) {
		return unmodifiableSetOf(edgesOf(v).stream().map(Link::asSet)
				.flatMap(Set::stream).filter(u -> !u.equals(v)));
	}

	private Set<GraphListener<V, E>> listeners = new HashSet<>();

	public void addListener(GraphListener<V, E> l) {
		listeners.add(l);
	}

	public void removeListener(GraphListener<V, E> l) {
		listeners.remove(l);
	}

	protected void informListeners(Consumer<GraphListener<V, E>> action) {
		listeners.forEach(action);
	}

	/**
	 * Use this method to initialize the datastructures used to store edges and
	 * vertices, as it may not be implicitly initialized when using the various
	 * constructors.
	 */
	protected abstract void initDataStructures();

}
