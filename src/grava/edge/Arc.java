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

	private V tail;
	private V head;

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
		this.tail = tail;
		this.head = head;
	}

	public V getTail() {
		return tail;
	}

	public V getHead() {
		return head;
	}

	@Override
	public Set<V> tails() {
		return Collections.unmodifiableSet(Collections.singleton(tail));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((tail == null) ? 0 : tail.hashCode());
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
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (tail == null) {
			if (other.tail != null)
				return false;
		} else if (!tail.equals(other.tail))
			return false;
		return true;
	}

}
