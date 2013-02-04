package algorithm;

import graph.Node;

public interface Heuristic<T extends Node> {
	
	public double value(T node1, T node2);

}
