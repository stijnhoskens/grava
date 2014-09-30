package grava.edge;

import grava.exceptions.LoopException;
import grava.util.CollectionUtils;
import grava.util.Pair;

import java.util.Set;

public abstract class AbstractLink<V> implements Link<V> {

	protected final Set<V> vertices;
	protected final Pair<V, V> pair;

	public AbstractLink(V u, V v) throws LoopException {
		if (u.equals(v))
			throw new LoopException(u);
		vertices = CollectionUtils.unmodifiableSetOf(u, v);
		pair = new Pair<>(u, v);
	}

	@Override
	public Set<V> asSet() {
		return vertices;
	}

	@Override
	public Pair<V, V> asPair() {
		return pair;
	}

	@Override
	public boolean contains(V v) {
		return vertices.contains(v);
	}

}
