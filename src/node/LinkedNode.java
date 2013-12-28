package node;

import java.util.HashSet;
import java.util.Set;

import edge.OneWayEdge;

public abstract class LinkedNode<T extends LinkedNode<T>> implements Node {
	
	public abstract Set<OneWayEdge<T>> getEdges();
	
	public Set<T> getNeighbors() {
		Set<T> neighbors = new HashSet<>();
		for(OneWayEdge<T> edge : getEdges())
			neighbors.add(edge.getNode2());
		return neighbors;
	}

}
