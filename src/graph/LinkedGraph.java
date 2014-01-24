package graph;

import java.util.Collections;
import java.util.Set;

import node.LinkedNode;
import edge.Edge;

/**
 * Serves as a mere facade to external users, this implementation contains no
 * data, since nodes have knowledge of their edges and neighbors.
 * 
 * @see LinkedNode
 * 
 * @author Stijn
 * 
 * @param <T>
 * @param <S>
 */
public class LinkedGraph<T extends LinkedNode<T, S>, S extends Edge<T>>
		implements
			Graph<T, S> {

	@Override
	public Set<T> getNeighborsOf(T node) {
		return Collections.unmodifiableSet(node.getNeighbors());
	}

	@Override
	public Set<S> getEdgesFrom(T node) {
		return Collections.unmodifiableSet(node.getEdges());
	}

}
