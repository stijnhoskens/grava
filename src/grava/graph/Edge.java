package grava.graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Edge<T> {

	protected T tail;
	protected T head;
	private Set<T> vertices;

	private Edge(Set<T> vertices) {
		this.vertices = Collections.unmodifiableSet(vertices);
	}

	public Edge(T tail, T head) {
		this(new HashSet<>(Arrays.asList(tail, head)));
		this.tail = tail;
		this.head = head;
	}

	public Edge(T loopedVertex) {
		this(loopedVertex, loopedVertex);
	}

	public Set<T> asSet() {
		return vertices;
	}

	public boolean isLoop() {
		return vertices.size() == 1;
	}

	public boolean isAdjacentTo(Edge<T> other) {
		Set<T> intersection = new HashSet<>(vertices);
		intersection.retainAll(other.asSet());
		return !intersection.isEmpty();
	}

	public boolean contains(T vertex) {
		return vertices.contains(vertex);
	}

	public boolean isParallelTo(Edge<T> other) {
		return vertices.equals(other.asSet());
	}

}
