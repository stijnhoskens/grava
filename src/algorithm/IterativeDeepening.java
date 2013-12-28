package algorithm;

import java.util.Set;

import node.Node;

import exceptions.InvalidGraphException;
import exceptions.InvalidNodeException;
import graph.Graph;

public class IterativeDeepening<T extends Node> extends BlindSearch<T> {

	public IterativeDeepening(Graph<T> graph, T start, T goal)
			throws InvalidGraphException, InvalidNodeException {
		super(graph, start, goal);
	}

	@Override
	public void performSearch() {
		outerLoop: for(int depth = 1; true; depth++) {
			initialiseQueue();
			while(!q.isEmpty()) {
				Path<T> firstPath = q.pollFirst();
				if(firstPath.size() < depth) {
					Set<Path<T>> childrenPaths = generateChildrenPaths(firstPath);
					q.addAllFirst(childrenPaths);
				}
				if(hasReachedGoal())
					break outerLoop;
			}
		}
	}

}
