package grava.graph;

import grava.edge.WeightedLink;
import grava.util.DistanceMatrix;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MatrixGraph<V, E extends WeightedLink<V>> extends AbstractGraph<V, E> {

	public MatrixGraph(List<V> vertices, DistanceMatrix matrix) {
		
	}
	
	public MatrixGraph(Set<V> vertices, Set<E> edges) {
		super(vertices, edges);
	}

	public MatrixGraph(Graph<V, E> graph) {
		super(graph);
	}

	@Override
	public Set<V> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addVertex(V v) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeVertex(V v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<E> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEdge(E e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeEdge(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<E> edgeBetween(V u, V v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<E> edgesOf(V v) {
		// TODO Auto-generated method stub
		return null;
	}

}