package grava.graph;

public class WeightedEdge<V> extends Edge<V> {

	protected final double weight;

	/**
	 * Creates an edge between the given vertices, having the given weight.
	 */
	public WeightedEdge(V u, V v, double weight) {
		super(u, v);
		this.weight = weight;
	}

	/**
	 * Returns the weight.
	 */
	public double getWeight() {
		return weight;
	}
}
