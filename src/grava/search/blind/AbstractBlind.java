package grava.search.blind;

import grava.edge.Link;
import grava.search.AbstractSearch;
import grava.search.SearchStrategy;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractBlind<V, E extends Link<V>> extends
		AbstractSearch<V, E> implements SearchStrategy<V, E> {

	protected final Deque<Walk<V, E>> q = new ArrayDeque<>();

	protected Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination, Consumer<Walk<V, E>> addToQ) {
		if (termination.test(start))
			return Optional.of(new Walk<V, E>(start));
		q.clear();
		q.add(new Walk<V, E>(start));
		while (!q.isEmpty()) {
			Walk<V, E> walk = q.pollFirst();
			Set<Walk<V, E>> newWalks = filteredNewWalksAsSet(graph, walk);
			for (Walk<V, E> w : newWalks) {
				if (termination.test(w.endVertex()))
					return Optional.of(w);
				addToQ.accept(w);
			}
		}
		return Optional.empty();
	}
}
