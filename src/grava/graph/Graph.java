package grava.graph;

import java.util.Collection;
import java.util.Set;

public interface Graph<V, E extends Edge<V>> {
	
	Set<V> getVertices();
	
	boolean addVertex(V v);
	
	void addVertices(Collection<V> vertices);
	
	boolean removeVertex(V v);
	
	boolean containsVertex(V v);
	
	Set<E> getEdges();
	
	boolean addEdge(E e);
	
	void addEdges(Collection<E> edges);
	
	boolean removeEdge(E e);
	
	boolean removeEdgesBetween(V u, V v);

	boolean areNeighbours(V u, V v);
	
	Set<V> neighboursOf(V v);
	
}
