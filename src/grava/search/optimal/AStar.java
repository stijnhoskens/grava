package grava.search.optimal;

import grava.edge.WeightedLink;
import grava.search.Searchable;
import grava.search.heuristic.Heuristic;
import grava.walk.Walk;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Predicate;

public class AStar<V, E extends WeightedLink<V>> extends AbstractOptimal<V, E> {

	public AStar(Heuristic<V> h) {
		super(h);
	}

	@Override
	public Optional<Walk<V, E>> findPath(Searchable<V, E> graph, V start,
			Predicate<V> termination) {
		Queue<Walk<V, E>> q = new PriorityQueue<>(costComparator());
		q.add(new Walk<V, E>(start));
		while (!q.isEmpty()) {
			Walk<V, E> walk = q.poll();
			if (termination.test(walk.endVertex()))
				return Optional.of(walk);
			getNewWalks(graph, walk).stream().filter(w -> isStillPath(walk, w))
					.forEach(q::add);
			branchAndBound(q);
		}
		return Optional.empty();
	}

	private void branchAndBound(Queue<Walk<V, E>> q) {
		q.forEach(w1 -> {
			final double cost = totalCostOf(w1);
			final V endVertex = w1.endVertex();
			if (q.stream().anyMatch(
					w2 -> w2.contains(endVertex) && cost >= totalCostOf(w2)
							&& !w1.equals(w2)))
				q.remove(w1);
		});
	}

}
