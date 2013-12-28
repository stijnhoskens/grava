package algorithm;

import node.Node;

public interface Heuristic<T extends Node> {
	
	public double value(T node1, T node2);

}
