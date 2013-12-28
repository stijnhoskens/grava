package edge;

import node.Node;

/**
 * This class is used for the most basic edge, in only one direction. This can
 * be the case when a certain street is one direction. You can also use this if
 * you insist on making two edges for a two way edge. It can come in handy in a
 * LinkedGraph, where only the other neighbor is needed, so a bidirectional
 * relation is unnecessary. Use the BiDirectionalEdge if you need the edge to
 * contain both linking nodes.
 * 
 * @author Stijn
 * 
 * @param <T>
 *            The node which the edge connects.
 */
public class Edge<T extends Node> {

	protected final T node2;

	protected final double cost1to2;

	public Edge(T node, double cost) {
		node2 = node;
		cost1to2 = cost;
	}

	public T getNode2() {
		return node2;
	}

	public double getCost1to2() {
		return cost1to2;
	}

	public double getCost() {
		return getCost1to2();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost1to2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (Double.doubleToLongBits(cost1to2) != Double
				.doubleToLongBits(other.cost1to2))
			return false;
		if (node2 == null) {
			if (other.node2 != null)
				return false;
		} else if (!node2.equals(other.node2))
			return false;
		return true;
	}

}
