package edge;

import node.Node;

/**
 * Use this class whenever your edges need to contain both nodes. Any type of
 * node can be used (specified by T), and any type of edge (S).
 * 
 * @see Edge
 * 
 * @author Stijn
 */
public class BiDirectionalEdge<T extends Node, S extends Edge<T>> {

	private final S edge1to2;

	private final S edge2to1;

	/**
	 * Combines two single sided edges to a bidirectional one.
	 * 
	 * @param edge1to2
	 *            The edge from node 1 to 2.
	 * @param edge2to1
	 *            The edge from node 2 to 1.
	 * @note Because edges are constructed by specifying the destination node in
	 *       the instructor, a completely new BiDirectionalEdge is constructed
	 *       as follows; new BiDirectionalEdge(new Edge(node2), new
	 *       Edge(node1)). Use the static methods basic and weighted whenever a
	 *       simple bidirectional edge is needed to avoid mistakes.
	 * @see Edge
	 */
	public BiDirectionalEdge(S edge1to2, S edge2to1) {
		this.edge1to2 = edge1to2;
		this.edge2to1 = edge2to1;
	}

	public BiDirectionalEdge<T, S> switchNodes() {
		return new BiDirectionalEdge<>(edge2to1, edge1to2);
	}

	public T getNode1() {
		return edge2to1.getDestination();
	}

	public S getEdge1to2() {
		return this.edge1to2;
	}

	public T getNode2() {
		return edge1to2.getDestination();
	}

	public S getEdge2to1() {
		return this.edge2to1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((edge1to2 == null) ? 0 : edge1to2.hashCode());
		result = prime * result
				+ ((edge2to1 == null) ? 0 : edge2to1.hashCode());
		return result;
	}

	/**
	 * Equals is re-implemented assuming there exist no two edges between two
	 * nodes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BiDirectionalEdge<?, ?> other = (BiDirectionalEdge<?, ?>) obj;
		if (edge1to2 == null) {
			if (other.edge1to2 != null)
				return false;
		} else if (!edge1to2.equals(other.edge1to2))
			return false;
		if (edge2to1 == null) {
			if (other.edge2to1 != null)
				return false;
		} else if (!edge2to1.equals(other.edge2to1))
			return false;
		return true;
	}

	public static <T extends Node> BiDirectionalEdge<T, Edge<T>> basic(T node1,
			T node2) {
		return new BiDirectionalEdge<T, Edge<T>>(new Edge<T>(node2),
				new Edge<T>(node1));
	}

	public static <T extends Node> BiDirectionalEdge<T, WeightedEdge<T>> weighted(
			T node1, T node2) {
		return new BiDirectionalEdge<T, WeightedEdge<T>>(new WeightedEdge<T>(
				node2), new WeightedEdge<T>(node1));
	}

	public static <T extends Node> BiDirectionalEdge<T, WeightedEdge<T>> weighted(
			T node1, T node2, double cost) {
		return new BiDirectionalEdge<T, WeightedEdge<T>>(new WeightedEdge<T>(
				node2, cost), new WeightedEdge<T>(node1, cost));
	}

	/**
	 * @note setting a cost to Double.INFINITY means it is not accessible from
	 *       that side.
	 */
	public static <T extends Node> BiDirectionalEdge<T, WeightedEdge<T>> weighted(
			T node1, T node2, double cost1to2, double cost2to1) {
		return new BiDirectionalEdge<T, WeightedEdge<T>>(new WeightedEdge<T>(
				node2, cost1to2), new WeightedEdge<T>(node1, cost2to1));
	}
}
