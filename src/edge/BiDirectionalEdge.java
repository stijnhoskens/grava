package edge;

import node.Node;
import exceptions.DirectedException;

/**
 * Use this class whenever your edges need to contain both nodes.
 * 
 * @see Edge
 * 
 * @author Stijn
 * 
 * @param <T>
 */
public class BiDirectionalEdge<T extends Node> extends Edge<T> {

	private final T node1;

	private final double cost2to1;

	public BiDirectionalEdge(T node1, T node2, double cost1to2, double cost2to1) {
		super(node2, cost1to2);
		this.node1 = node1;
		this.cost2to1 = cost2to1;
	}

	public BiDirectionalEdge(T node1, T node2, double cost) {
		this(node1, node2, cost, cost);
	}

	public BiDirectionalEdge<T> switchNodes() {
		return new BiDirectionalEdge<T>(node2, node1, cost2to1, cost1to2);
	}

	public T getNode1() {
		return node1;
	}

	public boolean isDirected() {
		return cost1to2 != cost2to1;
	}

	@Override
	public double getCost() throws DirectedException {
		if (isDirected())
			throw new DirectedException(cost1to2, cost2to1);
		return getCost1to2();
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
		BiDirectionalEdge<?> other = (BiDirectionalEdge<?>) obj;
		if (node1 == null)
			if (other.node1 != null)
				return false;
		if (node2 == null)
			if (other.node2 != null)
				return false;
		if (node1.equals(other.node1) && node2.equals(other.node2))
			return true;
		BiDirectionalEdge<?> switched = other.switchNodes();
		if (node1.equals(switched.node1) && node2.equals(switched.node2))
			return true;
		return false;
	}

}
