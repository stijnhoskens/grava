package grava.graph;

import static grava.util.SetUtils.unmodifiableSetOf;

import java.util.Optional;
import java.util.Set;

import grava.edge.Link;

public abstract class AbstractGraph<V, E extends Link<V>> implements
		Graph<V, E> {

	@Override
	public boolean removeEdgeBetween(V u, V v) {
		Optional<E> optional = edgeBetween(u, v);
		optional.ifPresent(e -> removeEdge(e));
		return optional.isPresent();
	}

	@Override
	public boolean areNeighbours(V u, V v) {
		return edgeBetween(u, v).isPresent();
	}

	@Override
	public Set<V> neighboursOf(V v) {
		return unmodifiableSetOf(edgesOf(v).stream().map(Link::asSet)
				.flatMap(Set::stream).filter(u -> !u.equals(v)));
	}

}
