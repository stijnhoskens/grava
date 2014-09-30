package grava.edge;

import grava.exceptions.LoopException;

import java.util.Collections;
import java.util.Set;

/**
 * Represents a directed edge or arc between two vertices.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public class Arc<V> extends AbstractLink<V> {

	/**
	 * Constructs an arc accepting the first argument as its tail and the second
	 * as its head.
	 * 
	 * @param tail
	 *            the tail
	 * @param head
	 *            the head
	 * @throws LoopException
	 *             if the created arc forms a loop
	 */
	public Arc(V tail, V head) throws LoopException {
		super(tail, head);
	}

	public V getTail() {
		return pair.getFirst();
	}

	public V getHead() {
		return pair.getSecond();
	}

	@Override
	public Set<V> tails() {
		return Collections.unmodifiableSet(Collections.singleton(getTail()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pair == null) ? 0 : pair.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arc<?> other = (Arc<?>) obj;
		if (pair == null) {
			if (other.pair != null)
				return false;
		} else if (!pair.equals(other.pair))
			return false;
		return true;
	}

}
