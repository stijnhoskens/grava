package grava.graph;

import java.util.Set;

public interface WeightedGraph<V, E extends WeightedEdge<V>> extends Graph<V, E> {
	
	Set<E> edgesOf(V v);
	
	E edgeBetween(V u, V v);

}
