package grava.graph;

import java.util.Set;

public interface Graph<T> {
	
	Set<T> getVertices();
	
	Set<Edge<T>> getEdges();

	boolean areNeighbours(T u, T v);
	
	boolean isIsolated(T u);
	
	boolean isComplete();
	
	int degreeOf(T u);
	
}
