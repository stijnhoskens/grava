package grava.graph;

public class DirectedEdge<V> extends Edge<V> implements Directed<V> {

	/**
	 * Creates an edge directed from its tail to its head.
	 */
	public DirectedEdge(V tail, V head) {
		super(tail, head);
	}
	
	public V getTail() {
		return tail;
	}
	
	public V getHead() {
		return head;
	}

}
