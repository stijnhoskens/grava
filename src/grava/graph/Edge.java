package grava.graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Edge<V> {

	protected V tail;
	protected V head;
	private Set<V> vertices;

	private Edge(Set<V> vertices) {
		this.vertices = Collections.unmodifiableSet(vertices);
	}

	public Edge(V tail, V head) {
		this(new HashSet<>(Arrays.asList(tail, head)));
		this.tail = tail;
		this.head = head;
	}

	public Set<V> asSet() {
		return vertices;
	}

	public boolean isAdjacentTo(Edge<V> other) {
		return vertices.stream().anyMatch(u -> other.asSet().contains(u));
	}

	public boolean contains(V vertex) {
		return vertices.contains(vertex);
	}
}
