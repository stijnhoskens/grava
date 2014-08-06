package grava.search.heuristic;

import grava.edge.Link;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BeamSearch<V, E extends Link<V>> extends AbstractHeuristic<V, E> {

	private final int bw;

	public BeamSearch(Heuristic<V> h, int beamWidth) {
		super(h);
		bw = beamWidth;
	}

	/**
	 * Creates a beam search with a default width of 5.
	 */
	public BeamSearch(Heuristic<V> h) {
		this(h, 5);
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		List<Walk<V, E>> q = new ArrayList<>();
		q.add(new Walk<V, E>(start));
		while (!q.isEmpty()) {
			List<Walk<V, E>> newQ = q.stream()
					.flatMap(w -> getNewWalks(graph, w).stream())
					.collect(Collectors.toList());
			q.clear();
			for (int i = 0; i < bw && !newQ.isEmpty(); i++) {
				Walk<V, E> w = Collections.min(newQ, heuristicComparator());
				newQ.remove(w);
				if (termination.test(w.endVertex()))
					return Optional.of(w);
				q.add(w);
			}
		}
		return Optional.empty();
	}

}
