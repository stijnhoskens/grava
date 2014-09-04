package grava.edge;

import grava.exceptions.LoopException;
import grava.util.CollectionUtils;
import grava.util.Pair;

import java.util.Set;

public abstract class AbstractLink<V> implements Link<V> {

	protected final Set<V> vertices;
	private final Pair<V, V> pair;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((vertices == null) ? 0 : vertices.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractLink<?> other = (AbstractLink<?>) obj;
		if (vertices == null) {
			if (other.vertices != null)
				return false;
		} else if (!vertices.equals(other.vertices))
			return false;
		return true;
	}

}
