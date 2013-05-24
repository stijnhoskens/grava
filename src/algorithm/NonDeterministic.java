package algorithm;

import java.util.Random;
import java.util.Set;

import exceptions.InvalidGraphException;
import exceptions.InvalidNodeException;
import graph.Graph;
import graph.Node;

public class NonDeterministic<T extends Node> extends BlindSearch<T> {

	public NonDeterministic(Graph<T> graph, T start, T goal)
			throws InvalidGraphException, InvalidNodeException {
		super(graph, start, goal);
	}

	@Override
	public void performSearch() {
		initialiseQueue();
		while(!q.isEmpty() && !hasReachedGoal()) {
			Path<T> firstPath = q.pollFirst();
			Set<Path<T>> childrenPaths = generateChildrenPaths(firstPath);
			Random r = new Random();
			for(Path<T> childrenPath : childrenPaths) {
				if(r.nextBoolean()) q.addFirst(childrenPath);
				else q.addLast(childrenPath);
			}
		}
	}

}
