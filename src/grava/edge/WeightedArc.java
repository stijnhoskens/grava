package grava.edge;

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
	 */
	public WeightedArc(V tail, V head, double weight) {
		super(tail, head);
		this.weight = weight;
	}

	@Override
	public double getWeight() {
		return weight;
	}
}
