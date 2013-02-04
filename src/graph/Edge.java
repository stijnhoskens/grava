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
		if(isDirected())
			throw new DirectedException(cost1to2, cost2to1);
		return getCost1to2();
	}
	
	public double getCost1to2() {
		return cost1to2;
	}

	public double getCost2to1() {
		return cost2to1;
	}

}
