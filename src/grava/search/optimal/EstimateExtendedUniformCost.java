package grava.search.optimal;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Predicate;

import grava.edge.WeightedLink;
import grava.search.Searchable;
import grava.search.heuristic.Heuristic;
import grava.walk.Walk;

public class EstimateExtendedUniformCost<V, E extends WeightedLink<V>> extends
		AbstractEEOptimal<V, E> {

	public EstimateExtendedUniformCost(Heuristic<V> h) {
		super(h);
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		if (termination.test(start))
			return Optional.of(new Walk<V, E>(start));
		Queue<Walk<V, E>> q = new PriorityQueue<>(fComparator());
		q.add(new Walk<V, E>(start));
		while (!q.isEmpty()) {
			Walk<V, E> walk = q.poll();
			if (termination.test(walk.endVertex()))
				return Optional.of(walk);
			newWalks(graph, walk).stream().filter(w -> isStillPath(walk, w))
					.forEach(q::add);
		}
		return Optional.empty();
	}

}
