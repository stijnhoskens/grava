package grava.search.optimal;

import grava.edge.WeightedLink;
import grava.search.Searchable;
import grava.search.heuristic.Heuristic;
import grava.walk.Walk;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Predicate;

public class IDAStar<V, E extends WeightedLink<V>> extends
		AbstractEEOptimal<V, E> {

	public IDAStar(Heuristic<V> h) {
		super(h);
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		if (termination.test(start))
			return Optional.of(new Walk<V, E>(start));
		double fBound = h.applyAsDouble(start);
		while (true) {
			Deque<Walk<V, E>> q = new ArrayDeque<>();
			q.add(new Walk<V, E>(start));
			double fNew = Double.POSITIVE_INFINITY;
			while (!q.isEmpty()) {
				Walk<V, E> walk = q.poll();
				if (termination.test(walk.endVertex()))
					return Optional.of(walk);
				for (Walk<V, E> w : filteredNewWalksAsSet(graph, walk)) {
					double fScore = fScoreOf(w);
					if (fScore > fBound)
						fNew = Math.min(fScore, fNew);
					else
						q.addFirst(w);
				}
			}
			if (fNew <= fBound)
				return Optional.empty();
			fBound = fNew;
		}
	}

}
