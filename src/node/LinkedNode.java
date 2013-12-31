package node;

import java.util.Set;

import edge.Edge;

public interface LinkedNode<T extends LinkedNode<T, S>, S extends Edge<T>>
		extends
			Node {

	public Set<S> getEdges();

	public Set<T> getNeighbors();

}
