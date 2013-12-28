package algorithm;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import node.Node;

public class Queue<T extends Node> extends ArrayDeque<Path<T>> {

	private static final long serialVersionUID = 1L;

	public void addAllFirst(Collection<Path<T>> paths) {
		for(Path<T> path : paths)
			this.addFirst(path);
	}
	
	/**
	 * This method makes sure the given list is still in the given order in the queue.
	 * @param paths
	 */
	public void addAllFirst(List<Path<T>> paths) {
		Collections.reverse(paths);
		for(Path<T> path : paths)
			this.addFirst(path);
	}
	
	public void addAllLast(Collection<Path<T>> paths) {
		for(Path<T> path : paths)
			this.addLast(path);
	}
	
	public void sort(Comparator<Path<T>> comp) {
		List<Path<T>> toSort = new ArrayList<Path<T>>();
		while(!this.isEmpty()) 
			toSort.add(this.pollFirst());
		Collections.sort(toSort, comp);
		this.addAllFirst(toSort);
	}
}
