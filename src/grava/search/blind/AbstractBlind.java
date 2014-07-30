package grava.search.blind;

import static grava.util.SetUtils.setOf;
import grava.edge.Link;
import grava.search.SearchStrategy;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractBlind<V, E extends Link<V>> implements
		SearchStrategy<V, E> {

	protected final Deque<Walk<V, E>> q = new ArrayDeque<>();

	protected Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			V end, Consumer<Walk<V, E>> consumer) {
		q.add(new Walk<V, E>(start));
		while (!q.isEmpty()) {
			Walk<V, E> walk = q.pollFirst();
			Set<Walk<V, E>> newWalks = getNewWalks(graph, walk);
			for (Walk<V, E> w : newWalks) {
				if (w.endVertex().equals(end))
					return Optional.of(w);
				if (w.isPath())
					consumer.accept(w);
			}
		}
		return Optional.empty();
	}

	protected static <V, E extends Link<V>> Set<Walk<V, E>> getNewWalks(
			Searchable<V, E> graph, Walk<V, E> walk) {
		return setOf(graph.edgesOf(walk.endVertex()).stream()
				.map(walk::getExtended));
	}

}
