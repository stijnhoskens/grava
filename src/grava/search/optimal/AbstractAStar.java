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
