package grava.edge;

import grava.exceptions.LoopException;

/**
 * Represents a weighted directed edge or weighted arc between two vertices.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public class WeightedArc<V> extends Arc<V> implements WeightedLink<V> {

	private double weight;

	/**
	 * Constructs a weighted arc accepting the first argument as its tail and
	 * the second as its head, also accepting its associated weight.
	 * 
	 * @param tail
	 *            the tail
	 * @param head
	 *            the head
	 * @param weight
	 *            the weight
	 * @throws LoopException
	 *             if the created arc forms a loop
	 */
	public WeightedArc(V tail, V head, double weight) throws LoopException {
		super(tail, head);
		this.weight = weight;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	public static <V> WeightedArc<V> unitWeightedArc(V u, V v) {
		return new WeightedArc<V>(u, v, 1d);
	}
}
