package grava.search;

import static grava.util.SetUtils.unmodifiableSetOf;
import grava.edge.Link;

import java.util.Set;

public abstract class AbstractSearchable<V, E extends Link<V>> implements
		Searchable<V, E> {

	@Override
	public Set<V> neighboursOf(V v) {
		return unmodifiableSetOf(edgesOf(v).stream().map(Link::asSet)
				.flatMap(Set::stream).filter(u -> !u.equals(v)));
	}

}
