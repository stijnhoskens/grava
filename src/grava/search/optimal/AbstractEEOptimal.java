package grava.search.optimal;

import java.util.Comparator;

import grava.edge.WeightedLink;
import grava.search.heuristic.Heuristic;
import grava.walk.Walk;

public abstract class AbstractEEOptimal<V, E extends WeightedLink<V>> extends
		AbstractOptimal<V, E> {

	public AbstractEEOptimal(Heuristic<V> h) {
		super(h);
	}

	protected Comparator<Walk<V, E>> fComparator() {
		return Comparator.comparingDouble(this::fScoreOf);
	}

	protected double fScoreOf(Walk<V, E> w) {
		return w.getEdges().stream().mapToDouble(WeightedLink::getWeight).sum()
				+ h.applyAsDouble(w.endVertex());
	}

}
