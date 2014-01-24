package edge;

import node.Node;

public class WeightedEdge<T extends Node> extends Edge<T> {

	private final double cost;

	public final static double UNIT_COST = 1d;

	public WeightedEdge(T destination, double cost) {
		super(destination);
		this.cost = cost;
	}

	/**
	 * Creates a edge with cost UNIT_COST. Used in optimal searches which need a
	 * weighted edge to calculate the total path cost.
	 */
	public WeightedEdge(T destination) {
		super(destination);
		cost = UNIT_COST;
	}
	public double getCost() {
		return cost;
	}

}
