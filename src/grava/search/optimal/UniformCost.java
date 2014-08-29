package grava.search.optimal;

import grava.edge.WeightedLink;
import grava.search.Searchable;
import grava.search.heuristic.Heuristic;
import grava.search.heuristic.NoHeuristic;
import grava.walk.Walk;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Predicate;

public class UniformCost<V, E extends WeightedLink<V>> extends
		AbstractOptimal<V, E> {

	protected UniformCost(Heuristic<V> h) {
		super(h);
	}

	public UniformCost() {
		this(new NoHeuristic<>());
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		return findPath(graph, start, termination, costComparator());
	}

	protected Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination, Comparator<Walk<V, E>> comp) {
		if (termination.test(start))
			return Optional.of(new Walk<V, E>(start));
		Queue<Walk<V, E>> q = new PriorityQueue<>(costComparator());
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
