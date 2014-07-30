package grava.search.blind;

import grava.edge.Link;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class NonDeterministic<V, E extends Link<V>> extends AbstractBlind<V, E> {

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start, V end) {
		Set<Walk<V, E>> walks = new HashSet<Walk<V, E>>();
		walks.add(new Walk<V, E>(start));
		while (!walks.isEmpty()) {
			Walk<V, E> walk = walks.stream().findAny().get();
			Set<Walk<V, E>> newWalks = getNewWalks(graph, walk);
			for (Walk<V, E> w : newWalks) {
				if (w.endVertex().equals(end))
					return Optional.of(w);
				if (w.isPath())
					walks.add(w);
			}
		}
		return Optional.empty();
	}

}
