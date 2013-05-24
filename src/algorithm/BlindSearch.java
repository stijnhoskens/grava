package algorithm;

import exceptions.InvalidGraphException;
import exceptions.InvalidNodeException;
import graph.Graph;
import graph.Node;

public abstract class BlindSearch<T extends Node> extends Search<T> {

	public BlindSearch(Graph<T> graph, T start, T goal)
			throws InvalidGraphException, InvalidNodeException {
		super(graph, start, goal);
	}
}
