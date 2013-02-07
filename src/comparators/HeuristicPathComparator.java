package comparators;

import graph.Node;
import algorithm.Heuristic;
import algorithm.Path;

public class HeuristicPathComparator<T extends Node> implements PathComparator<T> {
	
	private final Heuristic<T> heuristic;
	private	final T goal;
	
	public HeuristicPathComparator(Heuristic<T> h, T goal) {
		this.heuristic = h;
		this.goal = goal;
	}

	@Override
	public int compare(Path<T> arg0, Path<T> arg1) {
		double h1 = h(arg0);
		double h2 = h(arg1);
		return h1<h2 ? -1 : (h1>h2 ? 1 : 0);
	}
	
	private double h(Path<T> path) {
		return heuristic.value(path.getEndpoint(), goal);
	}

}
