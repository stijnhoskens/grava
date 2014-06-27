package grava.graph;

public class WeightedEdge<V> extends Edge<V> {

	protected final double weight;

	public WeightedEdge(V tail, V head, double weight) {
		super(tail, head);
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}
}
