package algorithm;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import node.Node;

public class Path<T extends Node> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a path with a single node. Used to initialize the queue.
	 */
	public Path(T node) {
		super();
		add(node);
	}

	public Path(List<T> path) {
		super(path);
	}

	public Path() {
		super();
	}

	public T getEndpoint() {
		return get(size() - 1);
	}

	/**
	 * Prints out the path (using the nodes toString method), separated by a ->
	 */
	public void print() {
		String print = "";
		for (T node : this)
			print += node.toString() + " -> ";
		print = print.substring(0, print.length() - 4);
		System.out.println(print);
	}

	/**
	 * Reverses this path.
	 * 
	 * @see subPath
	 */
	protected void reverse() {
		Collections.reverse(this);
	}

	/**
	 * Returns a subpath specified by its start and end index, both inclusive.
	 * When end > start, it returns a subPath starting at end, going to start
	 * (in reverse).
	 * 
	 * @param start
	 *            The index to start the subpath from
	 * @param end
	 *            The index to end.
	 * @note unlike List.subList, the node at this index is also included.
	 * @throws IndexOutOfBoundsException
	 *             When one or both indices are out of bounds.
	 */
	public Path<T> subPath(int start, int end) throws IndexOutOfBoundsException {
		if (start < 0 || start >= size() || end < 0 || end >= size())
			throw new IndexOutOfBoundsException();
		boolean shouldReverse = false;
		if (end < start) {
			int temp = start;
			start = end;
			end = temp;
			shouldReverse = true;
		}
		Path<T> subPath = new Path<>(this.subList(start, end + 1));
		if (shouldReverse)
			subPath.reverse();
		return subPath;
	}
}
