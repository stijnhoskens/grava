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

	public double getCost() {
		return cost1to2;
	}

}
