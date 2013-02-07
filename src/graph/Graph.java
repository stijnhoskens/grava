package graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Graph<T extends Node> {
	
	private final Set<T> nodes = new HashSet<T>();
	private Set<Edge<T>> edges  = new HashSet<Edge<T>>();
	
	public Set<T> getNodes() {
		return new HashSet<T>(nodes);
	}
	
	public void addNode(T node) {
		nodes.add(node);
	}
	
	public void addNodes(Collection<T> nodes){
		nodes.addAll(nodes);
	}
	
	public void addConnection(T node1, T node2, double cost1to2, double cost2to1) {
		if(nodes.contains(node1) && nodes.contains(node2)
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
	 * Returns a set of edges with the given node as first node every time.
	 */
	public Set<Edge<T>> getEdgesOf(T node) {
		Set<Edge<T>> nodeEdges = new HashSet<Edge<T>>();
		for(Edge<T> edge : edges) {
			if(node.equals(edge.getNode1()))
				nodeEdges.add(edge);
			else if(node.equals(edge.getNode2()) && !edge.isDirected())
				nodeEdges.add(edge.switchNodes());
		}
		return nodeEdges;
	}
	
	/**
	 * If you want to dynamically add new nodes everytime a certain node is expanded,
	 * override this method and add nodes and vertices everytime this method is called.
	 * @param node
	 * @return
	 */
	public Set<T> getNeighboursOf(T node) {
		Set<T> neighbours = new HashSet<T>();
		for(Edge<T> edge : getEdgesOf(node))
			neighbours.add(edge.getNode2());
		return neighbours;
	}
	
	public boolean containsNode(T node) {
		return nodes.contains(node);
	}
	
	private boolean edgesContainsEdgeBetween(T node1, T node2) {
		for(Edge<T> edge : edges) {
			if(node1.equals(edge.getNode1()) && node2.equals(edge.getNode1()))
				return true;
			else if(!edge.isDirected() && (node2.equals(edge.getNode1()) && node1.equals(edge.getNode1())))
				return true;
		}
		return false;
	}

}
