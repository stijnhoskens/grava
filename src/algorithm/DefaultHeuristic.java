package algorithm;

import graph.Node;

public class DefaultHeuristic<T extends Node> implements Heuristic<T> {

	@Override
	public double value(T node1, T node2) {
		return 0;
	}

}
