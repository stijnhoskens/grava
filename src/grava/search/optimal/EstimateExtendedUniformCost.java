package grava.search.optimal;

import java.util.Optional;
import java.util.function.Predicate;

import grava.edge.WeightedLink;
import grava.search.Searchable;
import grava.search.heuristic.Heuristic;
import grava.walk.Walk;

public class EstimateExtendedUniformCost<V, E extends WeightedLink<V>> extends
		UniformCost<V, E> {

	public EstimateExtendedUniformCost(Heuristic<V> h) {
		super(h);
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		return findPath(graph, start, termination, fComparator());
	}

}
