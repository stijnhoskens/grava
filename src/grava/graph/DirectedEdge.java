package grava.graph;

public class DirectedEdge<T> extends Edge<T> {

	public DirectedEdge(T tail, T head) {
		super(tail, head);
	}

	public DirectedEdge(T loopedVertex) {
		super(loopedVertex);
	}
	
	public T getTail() {
		return tail;
	}
	
	public T getHead() {
		return head;
	}

}
