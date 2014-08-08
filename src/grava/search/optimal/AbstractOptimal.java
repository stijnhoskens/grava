package grava.search.optimal;

import grava.edge.WeightedLink;
import grava.search.heuristic.AbstractHeuristic;
import grava.search.heuristic.Heuristic;
import grava.walk.Walk;

import java.util.Comparator;

public abstract class AbstractOptimal<V, E extends WeightedLink<V>> extends
		AbstractHeuristic<V, E> {

	public AbstractOptimal(Heuristic<V> h) {
		super(h);
	}

	protected Comparator<Walk<V, E>> costComparator() {
		return Comparator.comparingDouble(AbstractOptimal::totalCostOf);
	}

	protected Comparator<Walk<V, E>> fComparator() {
		return Comparator.comparingDouble(w -> totalCostOf(w)
				+ h.applyAsDouble(w.endVertex()));
	}

	protected static <E extends WeightedLink<?>> double totalCostOf(Walk<?, E> w) {
		return w.getEdges().stream().mapToDouble(WeightedLink::getWeight).sum();
	}
}
