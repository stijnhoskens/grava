package grava.search.blind;

import grava.edge.Link;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class NonDeterministic<V, E extends Link<V>> extends AbstractBlind<V, E> {

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		if (termination.test(start))
			return Optional.of(new Walk<V, E>(start));
		Set<Walk<V, E>> walks = new HashSet<Walk<V, E>>();
		walks.add(new Walk<V, E>(start));
		while (!walks.isEmpty()) {
			Walk<V, E> walk = walks.stream().findAny().get();
			Set<Walk<V, E>> newWalks = getNewWalks(graph, walk);
			for (Walk<V, E> w : newWalks) {
				if (termination.test(w.endVertex()))
					return Optional.of(w);
				if (isStillPath(walk, w))
					walks.add(w);
			}
		}
		return Optional.empty();
	}

}
