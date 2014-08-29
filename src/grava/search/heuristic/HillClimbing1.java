package grava.search.heuristic;

import grava.edge.Link;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class HillClimbing1<V, E extends Link<V>> extends
		AbstractHeuristic<V, E> {

	public HillClimbing1(Heuristic<V> h) {
		super(h);
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		if (termination.test(start))
			return Optional.of(new Walk<V, E>(start));
		Deque<Walk<V, E>> q = new ArrayDeque<>();
		q.add(new Walk<V, E>(start));
		while (!q.isEmpty()) {
			Walk<V, E> walk = q.pollFirst();
			List<Walk<V, E>> newWalks = newWalksList(graph, walk);
			newWalks.sort(reversedHeuristicComparator());
			for (Walk<V, E> w : newWalks) {
				if (termination.test(w.endVertex()))
					return Optional.of(w);
				if (isStillPath(walk, w))
					q.addFirst(w);
			}
		}
		return Optional.empty();
	}

}
