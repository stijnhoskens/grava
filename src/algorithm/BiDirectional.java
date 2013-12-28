package algorithm;

import java.util.Set;

import node.Node;

import exceptions.InvalidGraphException;
import exceptions.InvalidNodeException;
import graph.Graph;

public class BiDirectional<T extends Node> extends BlindSearch<T> {

	public BiDirectional(Graph<T> graph, T start, T goal)
			throws InvalidGraphException, InvalidNodeException {
		super(graph, start, goal);
	}

	@Override
	public void performSearch() {
		Queue<T> q2 = new Queue<T>();
		initialiseQueue();
		q2.offerFirst(new Path<T>(goal));
		while(!q.isEmpty() && !q2.isEmpty()) {
			Path<T> firstPath1 = q.pollFirst();
			Path<T> firstPath2 = q2.pollFirst();
			Set<Path<T>> childrenPaths1 = generateChildrenPaths(firstPath1);
			Set<Path<T>> childrenPaths2 = generateChildrenPaths(firstPath2);
			q.addAllLast(childrenPaths1);
			q2.addAllLast(childrenPaths2);
			Path<T> sharedPath = sharedState(q, q2);
			if(!sharedPath.isEmpty()) {
				foundPath = sharedPath;
				break;
			}
		}
	}
	
	private Path<T> sharedState(Queue<T> q1, Queue<T> q2) {
		for(Path<T> path : q1) 
			for(Path<T> path2 : q2) {
				Path<T> cPath = combinedPath(path, path2);
				if(!cPath.isEmpty())
					return cPath;
			}
		return new Path<T>();
	}
	
	private Path<T> combinedPath(Path<T> p1, Path<T> p2) {
		Path<T> path = new Path<T>();
		for(int i = 0; i < p1.size(); i++)
			for(int j = 0; j < p2.size(); j++)
				if(p1.get(i).equals(p2.get(j))) {
					path.addAll(p1.subPath(0, i));
					path.addAll(p2.subPath(j-1, 0));
				}
		return path;
	}

}
