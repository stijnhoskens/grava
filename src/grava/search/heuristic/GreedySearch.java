package grava.search.heuristic;

import grava.edge.Link;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

public class GreedySearch<V, E extends Link<V>> extends AbstractHeuristic<V, E> {

	public GreedySearch(Heuristic<V> h) {
		super(h);
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		if (termination.test(start))
			return Optional.of(new Walk<V, E>(start));
		Queue<Walk<V, E>> q = new PriorityQueue<>(heuristicComparator());
		q.add(new Walk<V, E>(start));
		while (!q.isEmpty()) {
			Walk<V, E> walk = q.poll();
			Set<Walk<V, E>> newWalks = filteredNewWalksAsSet(graph, walk);
			for (Walk<V, E> w : newWalks) {
				if (termination.test(w.endVertex()))
					return Optional.of(w);
				q.add(w);
			}
		}
		return Optional.empty();
	}

}
