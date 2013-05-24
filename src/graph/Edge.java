package graph;

import exceptions.DirectedException;

public class Edge<T extends Node> {

	private final T node1, node2;

	private final double cost1to2, cost2to1;

	public Edge(T node1, T node2, double cost1to2, double cost2to1) {
		this.node1 = node1;
		this.node2 = node2;
		this.cost1to2 = cost1to2;
		this.cost2to1 = cost2to1;
	}

	public Edge(T node1, T node2, double cost) {
		this(node1, node2, cost, cost);
	}

	public Edge<T> switchNodes() {
		return new Edge<T>(node2, node1, cost2to1, cost1to2);
	}

	public T getNode1() {
		return node1;
	}

	public T getNode2() {
		return node2;
	}

	public boolean isDirected() {
		return cost1to2 != cost2to1;
	}

	public double getCost() throws DirectedException {
		if (isDirected())
			throw new DirectedException(cost1to2, cost2to1);
		return getCost1to2();
	}

	public double getCost1to2() {
		return cost1to2;
	}

	public double getCost2to1() {
		return cost2to1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node1 == null) ? 0 : node1.hashCode());
		result = prime * result + ((node2 == null) ? 0 : node2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<?> other = (Edge<?>) obj;
		if (node1 == null) {
			if (other.node1 != null)
				return false;
		} else if (!node1.equals(other.node1))
			return false;
		if (node2 == null) {
			if (other.node2 != null)
				return false;
		} else if (!node2.equals(other.node2))
			return false;
		return true;
	}

}
