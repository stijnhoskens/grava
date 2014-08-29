package grava.search.optimal;

import grava.edge.WeightedLink;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.HashSet;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

public class AStar<V, E extends WeightedLink<V>> extends AbstractEEOptimal<V, E> {

	private final Set<V> alreadyEvaluated = new HashSet<>();
	private final Queue<Walk<V, E>> q = new PriorityQueue<>(fComparator());
	private final Predicate<Walk<V, E>> consistencyFilter;

	public AStar(AStarHeuristic<V> h) {
		super(h);
		consistencyFilter = h.isConsistent() ? w -> !alreadyEvaluated
				.contains(w.endVertex()) : w -> true;
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		if (termination.test(start))
			return Optional.of(new Walk<V, E>(start));
		q.add(new Walk<V, E>(start));
		while (!q.isEmpty()) {
			Walk<V, E> walk = q.poll();
			if (termination.test(walk.endVertex()))
				return Optional.of(walk);
			newWalks(graph, walk).stream().filter(w -> isStillPath(walk, w))
					.filter(consistencyFilter).forEach(q::add);
		}
		return Optional.empty();
	}
}
