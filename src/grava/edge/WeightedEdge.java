package grava.edge;

import grava.exceptions.LoopException;

/**
 * Represents a weighted edge between two vertices.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public class WeightedEdge<V> extends Edge<V> implements WeightedLink<V> {

	private double weight;

	/**
	 * Constructs an edge between the given vertices with the given weight.
	 * 
	 * @param u
	 *            the first vertex
	 * @param v
	 *            the second vertex
	 * @param weight
	 *            the weight associated to the link between the two
	 * @throws LoopException
	 *             if the created edge forms a loop
	 */
	public WeightedEdge(V u, V v, double weight) throws LoopException {
		super(u, v);
		this.weight = weight;
	}

	@Override
	public double getWeight() {
		return weight;
	}

}
