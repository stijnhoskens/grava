package grava.graph;

public class WeightedDirectedEdge<V> extends WeightedEdge<V> implements
		Directed<V> {

	/**
	 * Creates a directed edge between the given vertices, having the given
	 * weight.
	 */
	public WeightedDirectedEdge(V tail, V head, double weight) {
		super(tail, head, weight);
	}

	@Override
	public V getTail() {
		return tail;
	}

	@Override
	public V getHead() {
		return head;
	}

}
