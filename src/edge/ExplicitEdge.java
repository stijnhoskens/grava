package edge;

import node.Node;

/**
 * Use this class whenever your edges need to contain both nodes. Any type of
 * node can be used (specified by T), and any type of edge (S). Instances of
 * this class are not necessarily bidirectional. However node 2 must be
 * reachable from node 1, node 1 can be unreachable from node 2.
 * 
 * @see Edge
 * 
 * @author Stijn
 */
public class ExplicitEdge<T extends Node, S extends Edge<T>> {

	private final T node1;

	private final S edge1to2;

	private final S edge2to1;

	private final boolean isDirected;

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
	public ExplicitEdge(S edge1to2, S edge2to1) {
		this(edge2to1.getDestination(), edge1to2, edge2to1, false);
	}

	/**
	 * Combines a directed edge into a BiDirectional, since an edge from 2 to 1
	 * is not given, only 2 is reachable from 1, and not the other way around.
	 * 
	 * @param node1
	 *            The first node.
	 * @param edge1to2
	 *            The edge from node 1 to 2.
	 */
	public ExplicitEdge(T node1, S edge1to2) {
		this(node1, edge1to2, null, true);
	}

	private ExplicitEdge(T node1, S edge1to2, S edge2to1, boolean isDirected) {
		this.node1 = node1;
		this.edge1to2 = edge1to2;
		this.edge2to1 = edge2to1;
		this.isDirected = isDirected;
	}

	/**
	 * Returns null if the edge is directed, if node 1 can reach node 2 but not
	 * the other way around.
	 */
	public ExplicitEdge<T, S> switchNodes() {
		return isDirected() ? null : new ExplicitEdge<>(edge2to1, edge1to2);
	}

	public T getNode1() {
		return node1;
	}

	public S getEdge1to2() {
		return edge1to2;
	}

	public T getNode2() {
		return edge1to2.getDestination();
	}

	/**
	 * Returns null if the edge is directed, if node 1 can reach node 2 but not
	 * the other way around.
	 */
	public S getEdge2to1() {
		return edge2to1;
	}

	public boolean isDirected() {
		return isDirected;
	}

	@Override
	public String toString() {
		String inBetween = isDirected() ? "" : "<";
		return getNode1().toString() + inBetween + "->" + getNode2().toString();
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
		ExplicitEdge<?, ?> other = (ExplicitEdge<?, ?>) obj;
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

	public static <T extends Node> ExplicitEdge<T, Edge<T>> basic(T node1,
			T node2) {
		return new ExplicitEdge<T, Edge<T>>(new Edge<T>(node2), new Edge<T>(
				node1));
	}

	public static <T extends Node> ExplicitEdge<T, WeightedEdge<T>> weighted(
			T node1, T node2) {
		return new ExplicitEdge<T, WeightedEdge<T>>(new WeightedEdge<T>(node2),
				new WeightedEdge<T>(node1));
	}

	public static <T extends Node> ExplicitEdge<T, WeightedEdge<T>> weighted(
			T node1, T node2, double cost) {
		return new ExplicitEdge<T, WeightedEdge<T>>(new WeightedEdge<T>(node2,
				cost), new WeightedEdge<T>(node1, cost));
	}

	public static <T extends Node> ExplicitEdge<T, WeightedEdge<T>> weighted(
			T node1, T node2, double cost1to2, double cost2to1) {
		return new ExplicitEdge<T, WeightedEdge<T>>(new WeightedEdge<T>(node2,
				cost1to2), new WeightedEdge<T>(node1, cost2to1));
	}
}
