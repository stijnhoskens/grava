package grava.edge;

import java.util.Collections;
import java.util.Set;

/**
 * Represents a directed edge or arc between two vertices.
 *
 * @param <V>
 *            The type of vertices which it connects.
 */
public class Arc<V> extends Edge<V> implements Link<V> {

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
	 */
	public Arc(V tail, V head) {
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

}
