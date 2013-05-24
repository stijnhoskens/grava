package graph;

import java.util.List;

public interface NodeFactory<T extends Node> {
	
	public List<T> creatNeighbourNodes(T node);
	
	public double getCostBetween(T node1, T node2);

}
