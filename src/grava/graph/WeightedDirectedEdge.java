package grava.graph;

public class WeightedDirectedEdge<V> extends WeightedEdge<V> implements Directed<V> {

	public WeightedDirectedEdge(V tail, V head, double weight) {
		super(tail, head, weight);
	}

	public double getWeight() {
		return weight;
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
