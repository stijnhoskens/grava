package algorithm;

import java.util.Set;

import exceptions.InvalidGraphException;
import exceptions.InvalidNodeException;
import graph.Graph;
import graph.Node;

public class DepthFirst<T extends Node> extends BlindSearch<T> {
	
	public DepthFirst(Graph<T> graph, T start, T goal) 
			throws InvalidGraphException, InvalidNodeException {
		super(graph, start, goal);
	}
	
	@Override
	public void performSearch() {
		initialiseQueue();
		while(!q.isEmpty() && !hasReachedGoal()) {
			Path<T> firstPath = q.pollFirst();
			Set<Path<T>> childrenPaths = generateChildrenPaths(firstPath);
			q.addAllFirst(childrenPaths);
		}
	}
}
