package algorithm;

import graph.Node;

import java.util.ArrayList;
import java.util.Collections;


public class Path<T extends Node> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a path with a single node.
	 * Used to initialize the queue.
	 */
	public Path(T node) {
		super();
		this.add(node);
	}
	
	public Path(Path<T> path) {
		super(path);
	}
	
	public Path() {
		super();
	}
	
	public T getEndpoint() {
		return this.get(size()-1);
	}
	
	public void print() {
		String print = "";
		for(T node : this) 
			print+=node.getId()+" - ";
		print = print.substring(0, print.length()-3);
		System.out.println(print);
	}
	
	public void reverse() {
		Collections.reverse(this);
	}
	
	public Path<T> subPath(int start, int end) throws IndexOutOfBoundsException {
		if(start < 0 || start >= size() || end < 0 || end >= size())
			throw new IndexOutOfBoundsException();
		Path<T> subPath = new Path<T>();
		if(start==end) return subPath;
		boolean shouldReverse = false;
		if(end < start) {
			int temp = start;
			start = end;
			end = temp;
			shouldReverse = true;
		}
		for(int i = start; i <= end; i++)
			subPath.add(this.get(i));
		if(shouldReverse)
			subPath.reverse();
		return subPath;
	}
}
