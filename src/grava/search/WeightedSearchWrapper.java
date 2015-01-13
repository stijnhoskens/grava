package grava.search;

import grava.edge.Link;
import grava.edge.WeightedLink;
import grava.util.CollectionUtils;

import java.util.Set;
import java.util.function.Function;

public class WeightedSearchWrapper<V, E extends Link<V>, W extends WeightedLink<V>>
		implements Searchable<V, W> {

	private final Searchable<V, E> toWrap;
	private final Function<E, W> mapper;

	public WeightedSearchWrapper(Searchable<V, E> toWrap, Function<E, W> mapper) {
		this.toWrap = toWrap;
		this.mapper = mapper;
	}

	@Override
	public Set<W> edgesOf(V v) {
		return CollectionUtils.setOf(toWrap.edgesOf(v).stream().map(mapper));
	}

}
