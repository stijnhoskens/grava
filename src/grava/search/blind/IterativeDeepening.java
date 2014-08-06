package grava.search.blind;

import grava.edge.Link;
import grava.search.Searchable;
import grava.walk.Walk;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @note Beware as this method of search does not necessarily terminate when no
 *       path can be found. Termination can be controlled by specifying a
 *       maximum depth.
 */
public class IterativeDeepening<V, E extends Link<V>> extends
		AbstractBlind<V, E> {

	private final int maxDepth;

	public IterativeDeepening(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public IterativeDeepening() {
		maxDepth = Integer.MAX_VALUE;
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		for (int depth = 1; depth < maxDepth; depth++) {
			q.add(new Walk<V, E>(start));
			while (!q.isEmpty()) {
				Walk<V, E> walk = q.pollFirst();
				if (walk.length() >= depth)
					continue;
				Set<Walk<V, E>> newWalks = getNewWalks(graph, walk);
				for (Walk<V, E> w : newWalks) {
					if (termination.test(w.endVertex()))
						return Optional.of(w);
					if (isStillPath(walk, w))
						q.addFirst(w);
				}
			}
		}
		return Optional.empty();
	}
}
