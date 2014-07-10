package grava.edge;

/**
 * Provides an interface for weighted edges. These weighted edges have an
 * associated weight represented as a double.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public interface WeightedLink<V> extends Link<V> {

	/**
	 * Returns the weight.
	 * 
	 * @return the weight
	 */
	double getWeight();

}
