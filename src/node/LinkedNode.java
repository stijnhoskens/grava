package node;

import java.util.HashSet;
import java.util.Set;

import edge.Edge;

public abstract class LinkedNode<T extends LinkedNode<T, S>, S extends Edge<T>>
		implements
			Node {

	public abstract Set<S> getEdges();

	public Set<T> getNeighbors() {
		Set<T> neighbors = new HashSet<>();
		for (S edge : getEdges())
			neighbors.add(edge.getNode2());
		return neighbors;
	}

}
