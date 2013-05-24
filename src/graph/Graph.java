package graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph<T extends Node> {

	private final Set<T> nodes = new HashSet<>();
	private Set<Edge<T>> edges = new HashSet<>();
	private NodeFactory<T> nodeFactory;

	public Graph() {
		// basic constructor
	}

	/**
	 * Use this constructor if you want nodes be made dynamically.
	 * 
	 * @param factory
	 */
	public Graph(NodeFactory<T> factory) {
		this.nodeFactory = factory;
	}

	public Set<T> getNodes() {
		return new HashSet<T>(nodes);
	}

	public void addNode(T node) {
		nodes.add(node);
	}

	public void addNodes(Collection<T> nodes) {
		nodes.addAll(nodes);
	}

	public void addConnection(T node1, T node2, double cost1to2, double cost2to1) {
		if (nodes.contains(node1) && nodes.contains(node2)
				&& !edgesContainsEdgeBetween(node1, node2))
			edges.add(new Edge<T>(node1, node2, cost1to2, cost2to1));
	}

	public void addConnection(T node1, T node2, double cost) {
		addConnection(node1, node2, cost, cost);
	}

	public void addConnection(T node1, T node2) {
		addConnection(node1, node2, 0d);
	}

	/**
	 * @param node
	 *            The one node which has the other nodes as neighbours
	 * @param neighbours
	 *            Those neighbours
	 * @param costs
	 *            a neighbours.size()x2 matrix, so that costs[0][0] is the cost
	 *            from the node to the first neighbour, and costs[0][1] is the
	 *            cost of the first neighbour to the node.
	 */
	public void addConnections(T node, List<T> neighbours, double[][] costs) {
		for (int i = 0; i < neighbours.size(); i++)
			addConnection(node, neighbours.get(i), costs[i][0], costs[i][1]);
	}

	/**
	 * Returns a set of edges with the given node as first node every time.
	 */
	public Set<Edge<T>> getEdgesOf(T node) {
		Set<Edge<T>> nodeEdges = new HashSet<Edge<T>>();
		for (Edge<T> edge : edges) {
			if (node.equals(edge.getNode1()))
				nodeEdges.add(edge);
			else if (node.equals(edge.getNode2()) && !edge.isDirected())
				nodeEdges.add(edge.switchNodes());
		}
		return nodeEdges;
	}

	/**
	 * If you want to dynamically add new nodes everytime a certain node is
	 * expanded, use the nodeFactory.
	 * 
	 * @param node
	 * @return
	 */
	public Collection<T> getNeighboursOf(T node) {
		if (hasNodeFactory()) {
			
			List<T> neighbours = this.nodeFactory.createNeighbourNodes(node);
			this.addNodes(neighbours);
			double[][] costs = new double[neighbours.size()][2];
			for (int i = 0; i < neighbours.size(); i++) {
				T neighbour = neighbours.get(i);
				costs[i][0] = this.nodeFactory.getCostBetween(node, neighbour);
				costs[i][1] = this.nodeFactory.getCostBetween(neighbour, node);
			}
			this.addConnections(node, neighbours, costs);
			this.nodes.addAll(neighbours);
			return neighbours;
			
		} else {
			
			Collection<T> neighbours = new HashSet<T>();
			for (Edge<T> edge : getEdgesOf(node))
				neighbours.add(edge.getNode2());
			return neighbours;
			
		}
	}

	public boolean containsNode(T node) {
		return nodes.contains(node);
	}

	private boolean hasNodeFactory() {
		return nodeFactory != null;
	}

	private boolean edgesContainsEdgeBetween(T node1, T node2) {
		for (Edge<T> edge : edges) {
			if (node1.equals(edge.getNode1()) && node2.equals(edge.getNode1()))
				return true;
			else if (!edge.isDirected()
					&& (node2.equals(edge.getNode1()) && node1.equals(edge
							.getNode1())))
				return true;
		}
		return false;
	}

}
