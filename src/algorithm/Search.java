package algorithm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import comparators.*;

import exceptions.InvalidGraphException;
import exceptions.InvalidNodeException;
import exceptions.NoPathFoundException;

import graph.Graph;
import graph.Node;

/**
 * To use this class, first setup a Search instance, then perform the desired search algorithm.
 * Once the search is concluded, getPath() will return the found path.
 * @author Stijn Hoskens
 * @version 0.5
 * @param <T> The node subclass you want to work with.
 */
public class Search<T extends Node> {
	
	private final Graph<T> graph;
	private T start, goal;
	private final Queue<T> q = new Queue<T>();
	private Path<T> foundPath;
	private Heuristic<T> h;
	private SearchMethod method; //The method last used.
	private int beamWidth = 1;
	
	public Search(Graph<T> graph, T start, T goal) 
			throws InvalidGraphException, InvalidNodeException {
		this(graph, start, goal, null);
	}
	
	public Search(Graph<T> graph, T start, T goal, Heuristic<T> h) 
			throws InvalidGraphException, InvalidNodeException {
		if(graph==null)
			throw new InvalidGraphException();
		this.graph = graph;
		setStart(start); 
		setGoal(goal);
		setHeuristic(h);
	}
	
	/**
	 * @note non deterministic search adds the new paths randomly to the first place in queue or the last place. 
	 * In this sense it is not purely non deterministic, but there is a good balance between
	 * depth and breadth first.
	 * @note If the given method is a heuristic search, and no heuristic is given, 
	 * it will just perform as if it was a blind search.
	 * @note When beamsearch is used and no beamwidth is set, it will use a beamwidth of 1,
	 * and will do hillclimbing2.
	 * @note beam and hill climbing 2 search both ignore leafs that are not goal nodes.
	 */
	public void performSearch(SearchMethod method) {
		this.foundPath = null;
		this.method = method;
		if(method.isHeuristic() && h==null)
			h = getDefaultHeuristic();
		try {
			Method classMethod = this.getClass().getMethod(method.name(), new Class<?>[] { });
			classMethod.invoke(this, new Object[] { });
		} catch (Exception e) {
			e.printStackTrace();
			//do nothing, something went inexplicably wrong.
		}
	}
	
	public void setStart(T start) throws InvalidNodeException {
		if(start==null || !graph.containsNode(start))
			throw new InvalidNodeException();
		this.start = start;
	}
	
	public void setGoal(T goal) throws InvalidNodeException {
		if(goal==null || !graph.containsNode(goal))
			throw new InvalidNodeException();
		this.goal = goal;
	}
	
	public void setHeuristic(Heuristic<T> h) {
		this.h = h;
	}
	
	public void setBeamWidth(int beamWidth) {
		this.beamWidth = beamWidth;
	}
	
	public Path<T> getPath() throws NoPathFoundException {
		if(foundPath==null)
			throw new NoPathFoundException("The goal node seems to be unreachable.");
		return this.foundPath;
	}

	private void initialiseQueue() {
		q.clear();
		q.offerFirst(new Path<T>(start));
	}
	
	public void depthFirst() {
		initialiseQueue();
		while(!q.isEmpty() && !hasReachedGoal()) {
			Path<T> firstPath = q.pollFirst();
			Set<Path<T>> childrenPaths = generateChildrenPaths(firstPath);
			q.addAllFirst(childrenPaths);
		}
	}
	
	public void breadthFirst() {
		initialiseQueue();
		while(!q.isEmpty() && !hasReachedGoal()) {
			Path<T> firstPath = q.pollFirst();
			Set<Path<T>> childrenPaths = generateChildrenPaths(firstPath);
			q.addAllLast(childrenPaths);
		}
	}
	
	public void nonDeterministic() {
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
	
	public void iterativeDeepening() {
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
	
	public void biDirectional() {
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
	
	public void hillClimbing1() {
		initialiseQueue();
		while(!q.isEmpty() && !hasReachedGoal()) {
			Path<T> firstPath = q.pollFirst();
			Set<Path<T>> childrenPaths = generateChildrenPaths(firstPath);
			List<Path<T>> sorted = getSortedList(childrenPaths);
			q.addAllFirst(sorted);
		}
	}
	
	public void beam() {
		beam(this.beamWidth);
	}
	
	private void beam(int beamWidth) {
		initialiseQueue();
		while(!q.isEmpty() && !hasReachedGoal()) {
			Set<Path<T>> allNewPaths = new HashSet<Path<T>>();
			while(!q.isEmpty()) {
				Path<T> path = q.pollFirst();
				allNewPaths.addAll(generateChildrenPaths(path));
			}
			List<Path<T>> sorted = getSortedList(allNewPaths);
			int toExtract = beamWidth;
			for(int i = 0; i < toExtract && i < sorted.size(); i++) {
				Path<T> path = sorted.get(i);
				if(!hasDeadEndButNoGoal(path))
					q.addLast(path);
				else toExtract++;
			}
		}
	}
	
	public void hillClimbing2() {
		beam(1);
	}
	
	public void greedy() {
		initialiseQueue();
		PathComparator<T> comp = getPathComparator();
		while(!q.isEmpty() && !hasReachedGoal()) {
			Path<T> firstPath = q.pollFirst();
			Set<Path<T>> childrenPaths = generateChildrenPaths(firstPath);
			q.addAllFirst(childrenPaths);
			q.sort(comp);
		}
	}

	/**
	 * This method also eliminates loops.
	 */
	private Set<Path<T>> generateChildrenPaths(Path<T> path) {
		Set<Path<T>> childrenPaths = new HashSet<Path<T>>();
		T lastNode = path.getEndpoint();
		Set<T> neighbours = this.graph.getNeighboursOf(lastNode);
		for(T neighbour : neighbours) {
			Path<T> childrenPath = new Path<T>(path);
			childrenPath.add(neighbour);
			childrenPaths.add(childrenPath);
		}
		// Eliminate loops in those childrenPaths
		eliminateLoops(childrenPaths);
		return childrenPaths;
	}

	/**
	 * If there is a path ending in the goal node, this method puts it in the foundPath field. 
	 * @return whether or not a path in the queue reaches the goal node
	 * @effect path == foundPath
	 */
	private boolean hasReachedGoal() {
		for(Path<T> path : q) 
			if(path.getEndpoint().equals(goal)) {
				foundPath = path;
				return true;
			}
		return false;
	}
	
	private void eliminateLoops(Set<Path<T>> paths) {
		Set<Path<T>> newPaths = new HashSet<Path<T>>(paths);
		for(Path<T> path : paths)
			if(path.subPath(0, path.size()-2).contains(path.getEndpoint()))
				newPaths.remove(path);
		paths.clear();
		paths.addAll(newPaths);
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
	
	private List<Path<T>> getSortedList(Collection<Path<T>> set) {
		List<Path<T>> list = new ArrayList<Path<T>>(set);
		PathComparator<T> comp = getPathComparator();
		Collections.sort(list, comp);
		return list;
	}
	
	private PathComparator<T> getPathComparator() {
		switch(method.getSearchType()) {
		case Blind:
			return new BlindPathComparator<T>();
		case Cost:
			return new CostPathComparator<T>();
		case Heuristic:
			return new HeuristicPathComparator<T>(h, goal);
		case Optimal:
			return new OptimalPathComparator<T>();
		default:
			return new BlindPathComparator<T>();
		}
	}
	
	private Heuristic<T> getDefaultHeuristic() {
		return new Heuristic<T>() {
			@Override
			public double value(T node1, T node2) {
				return 0;
			}
		};
	}
	
	/**
	 * Returns true when the path has a dead end, however, it returns false when 
	 * the endpoint is the goal node.
	 */
	private boolean hasDeadEndButNoGoal(Path<T> path) {
		if(path.getEndpoint().equals(goal))
			return false;
		else return hasDeadEnd(path);
	}
	
	private boolean hasDeadEnd(Path<T> path) {
		return generateChildrenPaths(path).isEmpty() || path.size()==0;
	}
}