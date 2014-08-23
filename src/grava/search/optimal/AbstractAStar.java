package grava.search.optimal;

import grava.edge.WeightedLink;
import grava.search.heuristic.Heuristic;
import grava.walk.Walk;

import java.util.Queue;

public abstract class AbstractAStar<V, E extends WeightedLink<V>> extends
		AbstractOptimal<V, E> {

	public AbstractAStar(Heuristic<V> h) {
		super(h);
	}

	protected void branchAndBound(Queue<Walk<V, E>> q) {
		q.stream().forEach(
				w1 -> {
					final double cost = totalCostOf(w1);
					final V endVertex = w1.endVertex();
					if (q.stream().anyMatch(
							w2 -> w2.contains(endVertex)
									&& cost >= totalCostOf(w2)
									&& !w1.equals(w2)))
						q.remove(w1);
				});
	}

	protected boolean isRedundant(Queue<Walk<V, E>> q, Walk<V, E> w) {
		final double costOfW = totalCostOf(w);
		final V end = w.endVertex();
		return q.stream().filter(w1 -> w1.contains(end))
				.anyMatch(w2 -> costOfW >= totalCostOf(w2));
	}

}
