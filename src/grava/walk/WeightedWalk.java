package grava.walk;

import grava.edge.WeightedLink;

public class WeightedWalk<V, E extends WeightedLink<V>> extends Walk<V, E> {

	public WeightedWalk(V start) {
		super(start);
	}

	public double totalCost() {
		return edges.stream().mapToDouble(WeightedLink::getWeight).sum();
	}

}
